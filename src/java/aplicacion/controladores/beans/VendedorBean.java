/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.IHeladoDAO;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.hibernate.dao.imp.HeladoDAOImp;
import aplicacion.modelo.dominio.ComHel;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author gabri
 */
@ManagedBean
@SessionScoped
public class VendedorBean implements Serializable{
    ICompraDAO compraDAO;
    List<Compra> compras ;
    Integer codigo;
    IComHelDAO comHelDao;
    

    /**
     * Creates a new instance of VendedorBean
     */
    public VendedorBean() {
        compras = new ArrayList<>();
        codigo = 0;
        compraDAO = new CompraDAOImp();
        comHelDao = new ComHelDAOImp();
    }
    /**
     * me permite cancelar una compra manualmente
     */
    public void cancelarCompra(){
        IHeladoDAO heladoDao = new HeladoDAOImp();
        Compra compra = compras.get(0);
        if(compra.getEstado() == 0){
        compra.setEstado(3);
        compraDAO.update(compra);
        Set<Helado> listaHeladosComprados = compra.getHeladosCompra();
       for(Helado helado: listaHeladosComprados){
           String nombreHelado = helado.getNombreHelado();
           System.out.println("heado " + nombreHelado);
           int cantidadHelado = 0;   
            for(ComHel compras : comHelDao.getAll(ComHel.class)){
             if(compras.getCompras().getCodigoCompra() == compra.getCodigoCompra() && compras.getHelados().getCodigoHelado() == helado.getCodigoHelado()){        
                cantidadHelado= compras.getCantHelado();
                }
            }
           System.out.println("cantida "+ cantidadHelado);
           List<Helado> listaHeladosDisponibles = heladoDao.getAll(Helado.class);
           for(Helado disponible: listaHeladosDisponibles){
               if(nombreHelado.equals(disponible.getNombreHelado())){
                   int cantidadActual= disponible.getCantidad();
                   disponible.setCantidad(cantidadActual + cantidadHelado);
                   System.out.println("la nueva es "+disponible.getCantidad());
                    heladoDao.update(disponible);
               }
           }
       }
         FacesContext facesContext = FacesContext.getCurrentInstance();
         facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra Cancelada con Exito", ""));
        }else{
            if(compra.getEstado() == 3){
               FacesContext facesContext = FacesContext.getCurrentInstance();
         facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La compra ya estaba cancelada", "")); 
            }else{
                FacesContext facesContext = FacesContext.getCurrentInstance();
         facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Las compras confirmadas no pueden cancelarse", ""));
  
            }       
        }       
    }
    /**
     * sirve para obtener la compra correspondiente a un codigo 
     */
    public void obtenerCompraSegunId(){
        System.out.println(codigo);
        compras = compraDAO.obtenerCompra(codigo);      
    }    
    /**
     * metodo que permite confirmar una compra solo en estado "a retirar"
     * @throws JRException
     * @throws IOException 
     */
    public void confirmarCompra() throws JRException, IOException{
        Compra compra = compras.get(0);
        if(compra.getEstado() == 3){
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede confirmar esta compra", "porque la compra esta cancelada"));
       }else{
            if(compra.getEstado() == 0){
                listarArrayHeladoPdf(compra);
                compra.setEstado(1);
                compraDAO.update(compra);
                 FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "compra confirmada"));

            }else{
                 FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "la compra ya fue confirmada"));

            }
        }    
        codigo = 0;
    }
    
    public void listarArrayHeladoPdf(Compra compra)
            throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        IComHelDAO comHelDAO = new ComHelDAOImp();
        
        List<Helado> helados = new ArrayList<>();
        for (Helado h: compra.getHeladosCompra()){
            h.setCantidad(comHelDAO.obtenerCantidadComHel(compra.getCodigoCompra(), h.getCodigoHelado()));
            helados.add(h);
        }
        
        parametros.put("total", compra.getTotal());
        parametros.put("nombre", usuario.getApellidoUsuario()+", "+usuario.getNombreUsuario());
        parametros.put("fecha", new Date());
        
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/helado.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(helados));
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=usuario-report.pdf");
        ServletOutputStream stream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        //exportamos a un archivo en disco
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "e:/reporte.pdf");
        //mostrar en visor jasper
        //JasperViewer.viewReport(jasperPrint,false);
        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
        System.out.println("termino pdf");
    }
    
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public ICompraDAO getCompraDAO() {
        return compraDAO;
    }

    public void setCompraDAO(ICompraDAO compraDAO) {
        this.compraDAO = compraDAO;
    }

    public IComHelDAO getComHelDao() {
        return comHelDao;
    }

    public void setComHelDao(IComHelDAO comHelDao) {
        this.comHelDao = comHelDao;
    }   
}