package aplicacion.controladores.beans.forms;

import aplicacion.controladores.beans.GraficoBean;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.modelo.dominio.Carrito;
import aplicacion.modelo.dominio.Compra;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * managedbean que permite trabajar con los graficos estadisticos
 *
 * @author Falcao
 */
@ManagedBean
@ViewScoped
public class GraficosFormBean implements Serializable {

    @ManagedProperty(value = "#{graficoBean}")
    private GraficoBean graficoBean;
    private Date fechaMenor;
    private Date fechaMayor;

    /**
     * Creates a new instance of GraficosFormBean
     */
    public GraficosFormBean() {
        fechaMayor = new Date();
        fechaMenor = new Date();
    }

    /**
     * grafica las estadisticas generales de las compras
     */
    public void graficarEstadisticaGeneral() {

        if (graficoBean.getCompraDao().getAll(Compra.class).isEmpty() && graficoBean.getCarritoDao().getAll(Carrito.class).isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nada para mostrar", "no hay ventas"));
        } else {
            graficoBean.listar();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se graficó con exito", "A observar!"));
        }

    }

    /**
     * grafica las estadisticas de confirmado y cancelado por dia
     */
    public void graficarPorDia() {
        if (graficoBean.getCompraDao().getAll(Compra.class).isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nada para mostrar", "no hay ventas"));
        } else {
            graficoBean.graficarPorDia();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se graficó correctamente", "A observar!"));

        }

    }

    /**
     * permite graficar entre dos fechas ingresadas por el administrador
     */
    public void graficarEntreDosFechas() {
        List<Compra> listaCompra = graficoBean.getCompraDao().getAll(Compra.class);
        if (listaCompra.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nada para mostrar", "no hay ventas"));
        } else {
            if (fechaMenor.compareTo(listaCompra.get(0).getFechaCompra()) >= 0
                    && fechaMayor.compareTo(listaCompra.get(listaCompra.size()-1).getFechaCompra()) <= 0) {
                if (fechaMayor.compareTo(fechaMenor) >= 0) {
                    graficoBean.graficarEntreFechas(fechaMenor, fechaMayor);
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se graficó exitosamente", "A observar!"));
                } else {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "LA SEGUNDA FECHA DEBE SER MAYOR A LA PRIMERA"));
                }
            }else{
                FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "fechas fuera de rango"));
            }
        }

    }
//getters y setters 

    public GraficoBean getGraficoBean() {
        return graficoBean;
    }

    public void setGraficoBean(GraficoBean graficoBean) {
        this.graficoBean = graficoBean;
    }

    public Date getFechaMenor() {
        return fechaMenor;
    }

    public void setFechaMenor(Date fechaMenor) {
        this.fechaMenor = fechaMenor;
    }

    public Date getFechaMayor() {
        return fechaMayor;
    }

    public void setFechaMayor(Date fechaMayor) {
        this.fechaMayor = fechaMayor;
    }
}
