package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;


public class ProductoAjustadoTest {
	private ProductoMenu base;
	private Ingrediente lechuga;
	private Ingrediente queso;
	private ProductoAjustado ajustado;
	
	@BeforeEach
	void setUp() throws Exception{
		base = new ProductoMenu("Tiernita", 20000);
		lechuga = new Ingrediente("lechuga", 1000);
		queso = new Ingrediente ("queso", 2000);
		ajustado = new ProductoAjustado(base);
	}
	@AfterEach
	void tearDown() throws Exception
	{
	}
	
	@Test
	void testGetNombre() {
		assertEquals("Tiernita", ajustado.getNombre(), "El nombre del menu no es el que se espera");
	}
	
	@Test
	void testGetPrecioSinAjustes(){
		assertEquals(20000, ajustado.getPrecio(),"El precio del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaSinAjustes(){
		String correcto = "Tiernita\n" + "            20000\n";
		assertEquals(correcto, ajustado.generarTextoFactura(),"El texto de la factura no es el que se espera");	
	}
	@Test
	void testGetPrecioConAgregados(){
		ajustado.agregarIngrediente(queso);
		ajustado.agregarIngrediente(lechuga);
		assertEquals(20000+2000+1000, ajustado.getPrecio(),"El precio del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaConAgregados(){
		ajustado.agregarIngrediente(queso);
		ajustado.agregarIngrediente(lechuga);
		String correcto = "Tiernita\n" + "    +queso" + "                2000\n"+"    +lechuga"+"                1000\n" + "            23000\n";
		assertEquals(correcto, ajustado.generarTextoFactura(),"El texto de la factura no es el que se espera");	
	}
	@Test
	void testGetPrecioConEliminados(){
		ajustado.eliminarIngrediente(queso);
		ajustado.eliminarIngrediente(lechuga);
		assertEquals(20000, ajustado.getPrecio(),"El precio del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaConEliminados(){
		ajustado.eliminarIngrediente(queso);
		ajustado.eliminarIngrediente(lechuga);
		String correcto = "Tiernita\n" + "    -queso\n" +"    -lechuga\n" + "            20000\n";
		assertEquals(correcto, ajustado.generarTextoFactura(),"El texto de la factura no es el que se espera");	
	}
	@Test
	void testGetPrecioConAmbos(){
		ajustado.agregarIngrediente(queso);
		ajustado.eliminarIngrediente(lechuga);
		assertEquals(20000+2000, ajustado.getPrecio(),"El precio del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaConAmbos(){
		ajustado.agregarIngrediente(queso);
		ajustado.eliminarIngrediente(lechuga);
		String correcto = "Tiernita\n" + "    +queso" + "                2000\n"+"    -lechuga\n" + "            22000\n";
		assertEquals(correcto, ajustado.generarTextoFactura(),"El texto de la factura no es el que se espera");	
	}
	@Test
	void testGetPrecioConRepetidos(){
		ajustado.agregarIngrediente(queso);
		ajustado.agregarIngrediente(queso);
		assertEquals(20000+2000+2000, ajustado.getPrecio(),"El precio del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFacturaConRepetidos(){
		ajustado.agregarIngrediente(queso);
		ajustado.agregarIngrediente(queso);
		String correcto = "Tiernita\n" + "    +queso" + "                2000\n"+"    +queso"+"                2000\n" + "            24000\n";
		assertEquals(correcto, ajustado.generarTextoFactura(),"El texto de la factura no es el que se espera");	
	}
	
	
	

}
