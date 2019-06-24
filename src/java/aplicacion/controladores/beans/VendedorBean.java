/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion.controladores.beans;

import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.IComHelDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.ComHelDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import aplicacion.modelo.dominio.Usuario;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class VendedorBean {
    ICompraDAO compraDAO;
    List<Compra> compras ;
    Integer codigo;
    

    /**
     * Creates a new instance of VendedorBean
     */
    public VendedorBean() {
        compras = new ArrayList<>();
        codigo = 0;
        compraDAO = new CompraDAOImp();
    }

    public void obtenerCompraSegunId(){
        System.out.println(codigo);
        compras = compraDAO.obtenerCompra(codigo);
        
    }    
    
    public void confirmarCompra() throws JRException, IOException{
        Compra compra = compras.get(0);
        listarArrayHeladoPdf(compra);
        compra.setEstado(1);
        compraDAO.update(compra);
        
        codigo = 0;
    }
    
    public void listarArrayHeladoPdf(Compra compra)
            throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        //puedo pasar parametros al report, siempre que el diseño lo soporte
        //parametros.put("usuario", "pepito");
        IComHelDAO comHelDAO = new ComHelDAOImp();
        
        List<Helado> helados = new ArrayList<>();
        for (Helado h: compra.getHeladosCompra()){
            h.setCantidad(comHelDAO.obtenerCantidadComHel(compra.getCodigoCompra(), h.getCodigoHelado()));
            helados.add(h);
        }
        
        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/compraReport.jasper"));
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
    
    
}
