package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Combo;


public class PedidoTest {
	private Pedido pedido;
	private ProductoMenu hamburguesa;
	private Combo combo;
	private ProductoAjustado ajustado;
	
	@BeforeEach
	void setUp() throws Exception{
		pedido = new Pedido("Miguel", "Calle 119a #12-23");
		hamburguesa = new ProductoMenu("tiernita", 20000);
		ArrayList<ProductoMenu> elementos = new ArrayList<ProductoMenu>();
		elementos.add(hamburguesa);
		elementos.add(new ProductoMenu("papas", 10000));
		elementos.add(new ProductoMenu("gaseosa",5000));
		combo = new Combo("tiernita y mas", 0.1, elementos);
		ajustado = new ProductoAjustado(hamburguesa);
		ajustado.agregarIngrediente(new Ingrediente("queso",2000));
	}
	@AfterEach
	void tearDown() throws Exception
	{
	}
	
	@Test
	void testGetIdPedido() {
		int idPrimerCaso = pedido.getIdPedido();
		Pedido segundoCaso = new Pedido("Lina", "Calle 92 #1-2");
		assertEquals(idPrimerCaso +1, segundoCaso.getIdPedido(), "El id del nuevo pedido deberia ser el siguiente al anterior" );
	}
	@Test
	void testGetNombreCliente() {
		assertEquals("Miguel", pedido.getNombreCliente(), "El nombre del pedido no coincide con el que se espera");
	}
	@Test
	void testAgregarProducto() {
		pedido.agregarProducto(hamburguesa);
		assertEquals(23800, pedido.getPrecioTotalPedido(), "El producto no se agrego");
	}
	@Test
	void testGetPrecioTotalPedidoVacio() {
		assertEquals(0, pedido.getPrecioTotalPedido(), "El precio total no cumple con el que se espera");
	}
	@Test
	void testGetPrecioTotalPedidoProductos() {
		pedido.agregarProducto(hamburguesa);
		pedido.agregarProducto(combo);
		pedido.agregarProducto(ajustado);
		assertEquals(87465, pedido.getPrecioTotalPedido(), "El precio total no cumple con el que se espera");
	}
	@Test
	void testGenerarTextoFacturaVacia() {
		String correcto = "Cliente: Miguel\n" + "Dirección: Calle 119a #12-23\n"+ "----------------\n"+"----------------\n"+ "Precio Neto:  0\n"+"IVA:          0\n"+"Precio Total: 0\n";
		assertEquals(correcto, pedido.generarTextoFactura(),"El texto de la factura no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaVariosProductos() {
		pedido.agregarProducto(combo);
		pedido.agregarProducto(hamburguesa);
		pedido.agregarProducto(ajustado);
		String correcto = "Cliente: Miguel\n" + "Dirección: Calle 119a #12-23\n"+ "----------------\n"+ combo.generarTextoFactura()+ hamburguesa.generarTextoFactura()+ ajustado.generarTextoFactura()+"----------------\n"+ "Precio Neto:  73500\n"+"IVA:          13965\n"+"Precio Total: 87465\n";
		assertEquals(correcto, pedido.generarTextoFactura(),"El texto de la factura no es el que se espera");
	}
	@Test
	void testGuardarFactura() throws IOException {
		pedido.agregarProducto(combo);
		pedido.agregarProducto(hamburguesa);
		File archivo = File.createTempFile("factura_test", ".txt");
		pedido.guardarFactura(archivo);
		String contenido = Files.readString(archivo.toPath());
		assertEquals(pedido.generarTextoFactura(), contenido, "El contenido del archivo no coincide con el que se espera");
		archivo.delete();
		
	}
	@Test
	void testGuardarFacturaConRutaIncorrecta() {
		File archivo = new File("./carpeta_incorrecta/factura.txt");
		assertThrows(FileNotFoundException.class, ()-> {pedido.guardarFactura(archivo);
		});
	}
	
}
