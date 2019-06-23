package aplicacion.controladores.beans;

import aplicacion.modelo.dominio.Carrito;
import aplicacion.hibernate.dao.ICarritoDAO;
import aplicacion.hibernate.dao.ICompraDAO;
import aplicacion.hibernate.dao.imp.CarritoDAOImp;
import aplicacion.hibernate.dao.imp.CompraDAOImp;
import aplicacion.modelo.dominio.Compra;
import aplicacion.modelo.dominio.Helado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.ChartSeries;

/**
 * bean que contiene el codigo sobre los graficos estadisticos
 *
 * @author Falcao
 */
@ManagedBean
@ViewScoped
public class GraficoBean implements Serializable {

    private BarChartModel model;
    private BarChartModel barras;
    private BarChartModel barrasEntreFechas;
    private ICarritoDAO carritoDao;
    private ICompraDAO compraDao;

    /**
     * Creates a new instance of GraficoBean
     */
    public GraficoBean() {
        carritoDao = new CarritoDAOImp();
        compraDao = new CompraDAOImp();
    }

    /**
     * esto es para la grafica estadistica general, primero consultamos cuantos
     * pendientes hay en la tabla carrito, luego contamos cuantos vendidos y
     * cancelas desde la tabla compra
     */
    public void listar() {
        List<Carrito> listaGrafico = new ArrayList<>();
        int contVendido = 0, contCancelado = 0, contPendiente = 0;
        boolean repetido = false;
        listaGrafico = carritoDao.getAll(Carrito.class);
        List<Integer> usuarios = new ArrayList<>(); // para almacenar cada usuario que realizo un pedido

        for (Carrito carrito : listaGrafico) {       //para las compras pendientes
            repetido = false;
            if (usuarios != null) {
                for (int i = 0; i < usuarios.size(); i++) {
                    if (carrito.getCodigoUsuario() == usuarios.get(i)) {
                        repetido = true;
                    }
                }
            }
            if (repetido == false) {
                contPendiente++;
                usuarios.add(carrito.getCodigoUsuario());
            }
        }
        List<Compra> listaCompra = new ArrayList<Compra>();
        listaCompra = compraDao.getAll(Compra.class);
        for (Compra compra : listaCompra) {   //ahora para las compras vendidas o canceladas
            System.out.println(compra.getEstado());
            if (compra.getEstado() == 0) {
                contVendido++;
            } else {
                contCancelado++;
            }
        }
        graficar(listaGrafico, contVendido, contPendiente, contCancelado); // y llamamos al metodo graficar

    }

    /**
     * metodo que se encargara de dibujar las barras estadisticas
     *
     * @param listaL lista de compras
     * @param cV contador vendidos
     * @param cP contador pendientes
     * @param cC contador cancelados
     */
    public void graficar(List<Carrito> listaL, int cV, int cP, int cC) {
        model = new BarChartModel(); //instanciamos el objeto 

        ChartSeries seriesVendido = new BarChartSeries();
        ChartSeries seriesPendiente = new BarChartSeries();
        ChartSeries seriesCancelado = new BarChartSeries();

        seriesVendido.setLabel("vendido");
        seriesVendido.set("estadisticas", cV);
        seriesPendiente.setLabel("pendiete");
        seriesPendiente.set("estadisticas", cP);
        seriesCancelado.setLabel("cancelado");
        seriesCancelado.set("estadisticas", cC);

        model.addSeries(seriesVendido);
        model.addSeries(seriesPendiente);
        model.addSeries(seriesCancelado);
        model.setTitle("GRAFICOS ESTADISTICOS GENERAL");
        model.setLegendPosition("ne");
        model.setAnimate(true);
    }

    //en este apartado esta las graficas por dia
    /**
     * sumar dias a fechas
     */
    public Date sumarDiasAFecha(Date fecha, int dias) {
        if (dias == 0) {
            return fecha;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    /**
     * metodo que sirve para contar el estado de cada pedido
     *
     * @param fecha para que busque solo en esa fecha
     * @param estado comprueba el estado que tiene cada compra
     * @return un contador segun el caracter solicitado
     */
    public int listarPorDia(Date fecha, char estado) {
        int cV = 0, cP = 0, cC = 0, contGeneral = 0;
        for (Compra compra : compraDao.getAll(Compra.class)) {
            if (fecha.equals(compra.getFechaCompra())) {
                System.out.println("es " + fecha.equals(compra.getFechaCompra()));
                if (estado == 'v') {
                    if (compra.getEstado() == 0) {
                        cV++;
                    }
                    contGeneral = cV;
                }
                if (estado == 'c') {
                    if (compra.getEstado() == 3) {
                        cC++;
                    }
                    contGeneral = cC;
                }

            }
        }
        return contGeneral;
    }

    /**
     * este metodo se encarga de graficar por dia las estadisticas solo las
     * confirmadas y canceladas
     */
    public void graficarPorDia() {
        ChartSeries serieVendido = new BarChartSeries();
        ChartSeries serieCancelado = new BarChartSeries();
        serieVendido.setLabel("vendido");
        serieCancelado.setLabel("cancelado");
        barras = new BarChartModel();
        int cV = 0, cC = 0;
        List<Compra> listaPorDia = new ArrayList<>();
        listaPorDia = compraDao.getAll(Compra.class);
        Date fecha = listaPorDia.get(0).getFechaCompra();
        System.out.println(fecha);// primer fecha de compra
        for (Compra compra : listaPorDia) {
                if (fecha.compareTo(compra.getFechaCompra()) == -1) {//preguntar si la fecha es menor que la de la compra
                    fecha = compra.getFechaCompra(); //en caso de que lo sea, le asignamos esa fecha
                }
                if (fecha.equals(compra.getFechaCompra())) {
                    System.out.println(fecha.equals(compra.getFechaCompra())); //fecha mostrada
                    cV = listarPorDia(compra.getFechaCompra(), 'v');
                    cC = listarPorDia(compra.getFechaCompra(), 'c');
                    System.out.println(cV + " " + cC); //comprobar

                    serieVendido.set(compra.getFechaCompra(), cV);
                    serieCancelado.set(compra.getFechaCompra(), cC);

                    fecha = sumarDiasAFecha(fecha, 1);
                    System.out.println("sumada " + fecha);
                    System.out.println("-----------------");
                }
            }
           
        barras.addSeries(serieVendido);
        barras.addSeries(serieCancelado);
        barras.setTitle("GRAFICO ESTADISTICO SOBRE LAS VENTAS POR DIA, CONFIRMADAS Y CANCELADAS");
        barras.setLegendPosition("ne");
        barras.setAnimate(true);
    }
    /**
     * permite graficar entre dos fechas pasadas como parametro
     * @param fechaMenor fecha inicial
     * @param fechaMayor fecha final
     */
    public void graficarEntreFechas(Date fechaMenor, Date fechaMayor){
        ChartSeries serieVendido = new BarChartSeries();
        ChartSeries serieCancelado = new BarChartSeries();
        serieVendido.setLabel("vendido");serieCancelado.setLabel("cancelado");
        barrasEntreFechas = new BarChartModel();
        int cV = 0, cC = 0;
        List<Compra> listaPorDia = new ArrayList<>();
        listaPorDia = compraDao.getAll(Compra.class);
        Date fecha = listaPorDia.get(0).getFechaCompra();
        for (Compra compra : listaPorDia) { //recorrido de la tabla compra
            if(compra.getFechaCompra().compareTo(fechaMenor) >= 0 && compra.getFechaCompra().compareTo(fechaMayor) <= 0){
                if (fecha.compareTo(compra.getFechaCompra()) == -1) {//preguntar si la fecha es menor que la de la compra
                    fecha = compra.getFechaCompra(); //en caso de que lo sea, le asignamos esa fecha
                }
                if (fecha.equals(compra.getFechaCompra())) {
                    System.out.println(fecha.equals(compra.getFechaCompra())); //fecha mostrada
                    cV = listarPorDia(compra.getFechaCompra(), 'v');
                    cC = listarPorDia(compra.getFechaCompra(), 'c');
                    serieVendido.set(compra.getFechaCompra(), cV);
                    serieCancelado.set(compra.getFechaCompra(), cC);

                    fecha = sumarDiasAFecha(fecha, 1); //sumamos un dia a la fecha
                }
            }
        }
           
        barrasEntreFechas.addSeries(serieVendido);
        barrasEntreFechas.addSeries(serieCancelado);
        barrasEntreFechas.setTitle("GRAFICO ESTADISTICO SOBRE LAS VENTAS POR DIA ENTRE FECHAS, CONFIRMADAS Y CANCELADAS");
        barrasEntreFechas.setLegendPosition("ne");
        barrasEntreFechas.setAnimate(true);
    }
    
    //getters y setters
    public BarChartModel getModel() {
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }

    public BarChartModel getBarras() {
        return barras;
    }

    public void setBarras(BarChartModel barras) {
        this.barras = barras;
    }

    public ICarritoDAO getCarritoDao() {
        return carritoDao;
    }

    public void setCarritoDao(ICarritoDAO carritoDao) {
        this.carritoDao = carritoDao;
    }

    public ICompraDAO getCompraDao() {
        return compraDao;
    }

    public void setCompraDao(ICompraDAO compraDao) {
        this.compraDao = compraDao;
    }

    public BarChartModel getBarrasEntreFechas() {
        return barrasEntreFechas;
    }

    public void setBarrasEntreFechas(BarChartModel barrasEntreFechas) {
        this.barrasEntreFechas = barrasEntreFechas;
    }
    

}
