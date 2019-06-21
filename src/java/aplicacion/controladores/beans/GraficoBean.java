
package aplicacion.controladores.beans;

import aplicacion.modelo.dominio.Carrito;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;

/**
 * bean que contiene el codigo sobre los graficos estadisticos
 * @author Falcao
 */
@ManagedBean
@ViewScoped
public class GraficoBean implements Serializable{
    private BarChartModel model;
    private BarChartModel barras;
    private ICarritoDAO carritoDao;
    /**
     * Creates a new instance of GraficoBean
     */
    public GraficoBean() {
        carritoDao = new CarritoDAOImp();
    }

     /**
     * esto es para la grafica estadistica general
     */
//    public void listar() {
  //      List<Carrito> listaGrafico = new ArrayList<>();
//        int contVendido = 0, contCancelado = 0, contPendiente = 0;
//        listaGrafico = carritoDao.getAll(Carrito.class);
 //      for (Carrito carrito : listaGrafico) {         
//}
//            if (carrito== 1) {
//                contVendido++;
//            } else {
//                if (libro.getCantidadPaginas() == 2) {
//                    contPendiente++;
//                } else {
//                    if (libro.getCantidadPaginas() == 3) {
//                        contCancelado++;
//                    }
//                }
//            }
//        }
//        graficar(listaGrafico, contVendido, contPendiente, contCancelado);
//
//    }
//
//    public void graficar(List<Libro> listaL, int cV, int cP, int cC) {
//        model = new BarChartModel();
//
//        ChartSeries series = new BarChartSeries();
//        ChartSeries series2 = new BarChartSeries();
//        ChartSeries series3 = new BarChartSeries();
//
//        series.setLabel("vendidaao");
//        series.set("Vendido1", cV);
//
//        series.set("vendido2", cV);
//        model.addSeries(series);
//        series2.setLabel("pendieteee");
//        series2.set("pendiente", cP);
//        series2.set("penditn2", cP);
//        model.addSeries(series2);
//        series3.setLabel("cancelad");
//        series3.set("cancelado", cC);
//        model.addSeries(series3);
//
//        model.setTitle("PRUEBA GRAFICO");
//        model.setLegendPosition("ne");
//        model.setAnimate(true);

}
