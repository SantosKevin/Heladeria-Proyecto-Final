/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.ComHelId;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author samanta
 */
@ManagedBean
@SessionScoped
public class CarritoBean implements Serializable {

    private ICarritoDAO carritoDAO;
    private IHeladoDAO heladoDAO;
    private ICompraDAO compraDAO;
    private IComHelDAO comHelDAO;
    private Integer cantidad;
    private Carrito carrito;

    /**
     * Creates a new instance of CarritoBean
     */
    public CarritoBean() {
        carritoDAO = new CarritoDAOImp();
        heladoDAO = new HeladoDAOImp();
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
        carrito = new Carrito();
        cantidad = 1;
    }

    public void agregarCompra() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        List<Helado> listaHelado = carritoDAO.obtenerHeladoSegunIdUsuario(usuario.getCodigoUsuario());
        System.out.println(listaHelado.size());
        if (listaHelado.isEmpty()) {
            FacesMessage msg = new FacesMessage("Mensaje", "No puede confirmar la compra si el carrito esta vacio");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            Compra compra = new Compra();

            compra.setUsuarioCompra(usuario);
            compra.setHeladosCompra(new HashSet<>(listaHelado));
            //Esto seria para hora y fecha pero hibernate no lo realiza: compra.setFechaCompra(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
            compra.setTotal(carritoDAO.obtenerPrecioTotalCarrito(carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario())));
            compra.setFechaCompra(new Date());
            compra.setEstado(0);

            compraDAO.create(compra);
            comHelDAO.actualizarCantidadHeladoComHel(compra, usuario.getCodigoUsuario());
            enviarEmail(compra);
            carritoDAO.eliminarCarrito(usuario.getCodigoUsuario());
            FacesMessage msg = new FacesMessage("Mensaje", "Compra Finalizada exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().executeScript("PF('dlgPregunta').hide();");
    }

    
    public double calcularTotal() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return carritoDAO.obtenerPrecioTotalCarrito(carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario()));
    }
    
    public void eliminarCarrito() {
        carritoDAO.eliminarCarrito(carrito);
        FacesMessage msg = new FacesMessage("Exito", "Eliminacion del carrito exitosa");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('dlgEliminar').hide();");
    }
    
    public void enviarEmail(Compra compra) {
        //el correo email de envio
        // El correo gmail de envío
        String correoEnvia = "heladeriafiunju@gmail.com";
        String claveCorreo = "Heladeria123";

        // La configuración para enviar correo
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", correoEnvia);
        properties.put("mail.password", claveCorreo);

        // Obtener la sesion
        Session session = Session.getInstance(properties, null);

        try {
            // Crear el cuerpo del mensaje
            MimeMessage mimeMessage = new MimeMessage(session);

            // Agregar quien envía el correo
            mimeMessage.setFrom(new InternetAddress(correoEnvia, "Heladeria"));

            // Los destinatarios
            InternetAddress[] internetAddresses = {
                new InternetAddress(compra.getUsuarioCompra().getEmailUsuario())};

            // Agregar los destinatarios al mensaje
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    internetAddresses);

            // Agregar el asunto al correo
            mimeMessage.setSubject("Compra Realizado con Exito");

            // Creo la parte del mensaje
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setText("Su compra esta en espera para ser retirado "
                    + "en nuestra sucursal mas cercana "
                    + "debera ir con su codigo de compra y dni al momento de retirar el pedido.\n"
                    + "CODIGO DE COMPRA: " + compraDAO.obtenerUltimoCodigodeCompra(compra.getUsuarioCompra().getCodigoUsuario()) + "\n."
                    + "\n"
                    + "MUCHAS GRACIAS POR ELEJIRNOS !!!!!!!!!!!");

            // Crear el multipart para agregar la parte del mensaje anterior
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Agregar el multipart al cuerpo del mensaje
            mimeMessage.setContent(multipart);

            // Enviar el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(correoEnvia, claveCorreo);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Correo enviado");
    }

    public void leer(Carrito carritoSeleccion) {
        carrito = carritoSeleccion; // copia la referencia 
    }

    public void modificarCarrito() {
        Helado helado = heladoDAO.obtenerHeladoSegunIdHelado(carrito.getCodigoHelado());
        if (helado.getPrecioOferta().equals(0.0)){
            carrito.setTotal(carrito.getCantidad() * helado.getPrecio());
        }else{
            carrito.setTotal(carrito.getCantidad() * helado.getPrecioOferta());
        }
        carritoDAO.update(carrito);
        FacesMessage msg = new FacesMessage("Exito", "Modificacion del carrito exitosa");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('dlgModificar').hide();");
    }
    
    public List<Carrito> obtenerCarrito() {
        return carritoDAO.getAll(Carrito.class);
    }

    public List<Carrito> obtenerCarritoSegunIdUsuario() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return carritoDAO.obtenerCarritoSegunIdUsuario(usuario.getCodigoUsuario());
    }

    public Helado obtenerHelado(Integer idHelado) {
        return heladoDAO.obtenerHeladoSegunIdHelado(idHelado);
    }

    public ICarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public void setCarritoDAO(ICarritoDAO carritoDAO) {
        this.carritoDAO = carritoDAO;
    }

    public IHeladoDAO getHeladoDAO() {
        return heladoDAO;
    }

    public void setHeladoDAO(IHeladoDAO heladoDAO) {
        this.heladoDAO = heladoDAO;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }

    public IComHelDAO getComHelDAO() {
        return comHelDAO;
    }

    public void setComHelDAO(IComHelDAO comHelDAO) {
        this.comHelDAO = comHelDAO;
    }

}
