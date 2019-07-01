/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHelCarDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HelCarDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.ComHelId;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.HelCar;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import javax.mail.MessagingException;
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
    private IHelCarDAO helCarDAO;
    private Integer cantidad;

    private Helado helado;

    /**
     * Creates a new instance of CarritoBean
     */
    public CarritoBean() {
        carritoDAO = new CarritoDAOImp();
        heladoDAO = new HeladoDAOImp();
        compraDAO = new CompraDAOImp();
        comHelDAO = new ComHelDAOImp();
        helCarDAO = new HelCarDAOImp();
        cantidad = 1;
    }

    public void agregarCompra() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        List<Helado> listaHelado = obtenerHeladoDeCarritoSegunUsuario();
        if (listaHelado.isEmpty()) {
            FacesMessage msg = new FacesMessage("Mensaje", "No puede confirmar la compra si el carrito esta vacio");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            // 0 es el estado del carrito ("a retirar")
            Compra compra = new Compra(1, usuario, new HashSet<>(listaHelado), 0, new Date(), obtenerTotaldelCarrito());

            compraDAO.create(compra);
            // se utiliza obtenerUltimaCompra para que guarda con el ultima id generado de la compra
            compra = compraDAO.obtenerUltimaCompra(usuario);
            comHelDAO.transferirCantidadHelCarAComHel(compra, usuario);
            //actualiza la cantidad de Helado de tabla Helados
            heladoDAO.actualizarLaCantidadDeHelados();
            enviarEmail(compra);

            Carrito c = carritoDAO.obtenerCarritoSegunUsuario(usuario);
            Carrito carrito = new Carrito(c.getCarCodigo(), c.getUsuario());
            carritoDAO.eliminarCarrito(carrito);
            FacesMessage msg = new FacesMessage("Mensaje", "Compra Finalizada exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        PrimeFaces.current().executeScript("PF('dlgPregunta').hide();");
    }

    /**
     * muestra la cantidad de helados de la tabla HelCar segun el helado ingresado
     * 
     * @param helado
     * @return cantidad de helado
     */
    public Integer obtenerCantidadHeladoDeHelCar(Helado helado) {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        return helCar.getCantHelado();
    }
    
    /**
     * devuelve y calcula el total segund el helado ingresado
     * @param helado
     * @return total
     */
    public double obtenerPrecioTotalPorHelado(Helado helado) {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        if (helado.getPrecioOferta() > 0) {
            return helCar.getCantHelado() * helado.getPrecioOferta();
        } else {
            return helCar.getCantHelado() * helado.getPrecio();
        }
    }
    
    /**
     * devuelve el total del carrito actual
     * @return total
     */
    public double obtenerTotaldelCarrito() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (carritoDAO.obtenerCarritoSegunUsuario(usuario) == null) {
            return 0;
        }
        return carritoDAO.obtenerCarritoSegunUsuario(usuario).getCarTotal();
    }

    /**
     * elimina helados del carrito
     */
    public void eliminarCarrito() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        helCarDAO.eliminarCarrito(helCar); //eliminacion fisica
        carritoDAO.calcularTotalListaHeladoCarrito(carritoDAO.obtenerCarritoSegunUsuario(usuario));

        Carrito carrito = carritoDAO.obtenerCarritoSegunUsuario(usuario);
        if (carrito.getHelCars().isEmpty()) {
            Carrito c = new Carrito(carrito.getCarCodigo(), carrito.getUsuario());
            carritoDAO.eliminarCarrito(c);
            FacesMessage msg = new FacesMessage("Exito", "Se elimino el carrito completamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage("Exito", "Eliminacion Helado del carrito exitosa");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

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
                    + "en nuestra sucursal más cercana "
                    + "deberá ir con su código de compra y DNI al momento de retirar el pedido.\n"
                    + "CODIGO DE COMPRA: " + compraDAO.obtenerUltimoCodigodeCompra(compra.getUsuarioCompra()) + "\n."
                    + "\n"
                    + "MUCHAS GRACIAS POR ELEGIRNOS !!!!!!!!!!!");

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
            
        }
        System.out.println("Correo enviado");
    }

    /**
     * guarda los datos del helado ingresado 
     * @param heladoSeleccion
     */
    public void leer(Helado heladoSeleccion) {
        helado = heladoSeleccion; // copia la referencia 
    }

    /**
     * modifica el carrito
     */
    public void modificarCarrito() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        HelCar helCar = helCarDAO.obtenerHelCar(carritoDAO.obtenerCarritoSegunUsuario(usuario), helado);
        helCar.setCantHelado(cantidad);
        helCarDAO.update(helCar);
        carritoDAO.calcularTotalListaHeladoCarrito(carritoDAO.obtenerCarritoSegunUsuario(usuario));
        
        FacesMessage msg = new FacesMessage("Exito", "Modificacion del carrito exitosa");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().executeScript("PF('dlgModificar').hide();");
    }

    public List<Carrito> obtenerCarrito() {
        return carritoDAO.getAll(Carrito.class);
    }

    public List<Helado> obtenerHeladoDeCarritoSegunUsuario() {
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (carritoDAO.obtenerCarritoSegunUsuario(usuario) == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(carritoDAO.obtenerCarritoSegunUsuario(usuario).getHelCars());
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

    public Helado getHelado() {
        return helado;
    }

    public void setHelado(Helado helado) {
        this.helado = helado;
    }

    public IHelCarDAO getHelCarDAO() {
        return helCarDAO;
    }

    public void setHelCarDAO(IHelCarDAO helCarDAO) {
        this.helCarDAO = helCarDAO;
    }

}
