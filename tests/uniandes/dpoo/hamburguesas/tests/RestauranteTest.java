package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Restaurante;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;

public class RestauranteTest {
	private Restaurante restaurante;
	private File ingredientes;
	private File menu;
	private File combos;
	
	@BeforeEach
	void setUp() throws Exception {
		restaurante = new Restaurante();
		ingredientes = new File("./data/test/ingredientes.txt");
		menu = new File("./data/test/menu.txt");
		combos = new File("./data/test/combos.txt");
		
	}
	@AfterEach
	void tearDown() throws Exception
	{
	}
	
	@Test
	void testCrearRestauranteVacio() {
		assertEquals(0, restaurante.getPedidos().size(), "La lista de pedidos debe estar vacía");
		assertEquals(0, restaurante.getIngredientes().size(), "La lista de ingredientes debe estar vacía");
		assertEquals(0, restaurante.getMenuBase().size(), "La lista de menus debe estar vacía");
		assertEquals(0, restaurante.getMenuCombos().size(), "La lista de combos debe estar vacía");
		assertNull(restaurante.getPedidoEnCurso(), "No debe haber pedidos en curso");
	}
	@Test
	void testCrearRestauranteConInformacion() throws Exception{
		restaurante.cargarInformacionRestaurante(ingredientes, menu, combos);
		assertEquals(0, restaurante.getPedidos().size(),"La lista de pedidos debe estar vacía");
		assertEquals("queso", restaurante.getIngredientes().get(0).getNombre(),"El nombre del ingrediente no es el esperado");
		assertEquals(4, restaurante.getIngredientes().size(),"El numero de ingredientes no coincide con el que se espera");
		assertEquals(3, restaurante.getMenuBase().size(),"El numero de menus no coincide con el que se espera");
		assertEquals(20000, restaurante.getMenuBase().get(0).getPrecio(),"El precio del menu no coincide con el que se espera");
		assertEquals(2,restaurante.getMenuCombos().size(),"El numero de combos no coincide con el que se espera");
		assertEquals("combo tiernita y mas", restaurante.getMenuCombos().get(0).getNombre(),"El nombre del combo no coincide conel esperado");
		assertEquals(13575, restaurante.getMenuCombos().get(1).getPrecio(),"El precio no coincide con el esperado");
		assertNull(restaurante.getPedidoEnCurso(), "No debe haber pedidos en curso");
	}
	@Test
	void testIniciarPedido() throws Exception{
		restaurante.iniciarPedido("Miguel", "Calle 119a #21-2");
		assertNotNull(restaurante.getPedidoEnCurso(),"Deberia haber un pedido en curso");
		assertEquals("Miguel",restaurante.getPedidoEnCurso().getNombreCliente(),"El nombre del cliente no coincide con el nombre del cliente en el pedido");
	}
	@Test
	void testIniciarPedidoConPedidoEnCurso() throws Exception{
		restaurante.iniciarPedido("Miguel", "Calle 119a #21-2");
		assertThrows(YaHayUnPedidoEnCursoException.class, ()->{
			restaurante.iniciarPedido("Lina", "Calle 92 #1-3");
		});
	}
	@Test 
	void testCerrarSinPedido() {
		assertThrows(NoHayPedidoEnCursoException.class, ()->{
			restaurante.cerrarYGuardarPedido();
		});
	}
	@Test
	void testCerrarYGuardarPedido() throws Exception{
		restaurante.iniciarPedido("Miguel", "Calle 119a #22-2");
		int id = restaurante.getPedidoEnCurso().getIdPedido();
		restaurante.cerrarYGuardarPedido();
		File facturaPrueba = new File("./facturas/factura_"+ id+ ".txt");
		assertTrue(facturaPrueba.exists(), "La factura deberia guardarse en la carpeta facturas");
		assertNull(restaurante.getPedidoEnCurso(),"Al cerrar y guardar no debería haber ningun pedido en curso");
		facturaPrueba.delete();
	}
	@Test 
	void testPedidoHistorial() throws Exception{
		restaurante.iniciarPedido("Miguel", "Calle 119a #22-2");
		int id = restaurante.getPedidoEnCurso().getIdPedido();
		restaurante.cerrarYGuardarPedido();
		File facturaPrueba = new File("./facturas/factura_"+ id+ ".txt");
		assertEquals(1,restaurante.getPedidos().size(),"La cantidad de pedidos no es la esperada");
		assertEquals("Miguel",restaurante.getPedidos().get(0).getNombreCliente(),"El nombre del cliente no es el esperado");
		facturaPrueba.delete();
	}
	
	@Test
	void testIngredienteRepetido(){
		File repetido = new File("./data/test/ingredientes_repetido.txt");
		assertThrows(IngredienteRepetidoException.class, () ->{
			restaurante.cargarInformacionRestaurante(repetido, menu, combos);
		});
	}
	
	@Test
	void testMenuRepetido(){
		File repetido = new File("./data/test/menu_repetido.txt");
		assertThrows(ProductoRepetidoException.class, () ->{
			restaurante.cargarInformacionRestaurante(ingredientes, repetido, combos);
		});
	}
	
	@Test
	void testComboRepetido(){
		File repetido = new File("./data/test/combos_repetido.txt");
		assertThrows(ProductoRepetidoException.class, () ->{
			restaurante.cargarInformacionRestaurante(ingredientes, menu, repetido);
		});
	}
	
	@Test
	void testProductoFaltante(){
		File faltante = new File("./data/test/combos_faltante.txt");
		assertThrows(ProductoFaltanteException.class, () ->{
			restaurante.cargarInformacionRestaurante(ingredientes, menu, faltante);
		});
	}

}
	