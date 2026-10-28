package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {
	private ProductoMenu productoMenu1;
	
	@BeforeEach
	void setUp() throws Exception
	{
		productoMenu1 = new ProductoMenu("Tiernita",20000);
	}
	
	@AfterEach
	void tearDown() throws Exception
	{
	}
	
	@Test
	void testGetNombre() {
		assertEquals("Tiernita", productoMenu1.getNombre(),"El nombre del menu no es el que se espera");
	}
	@Test
	void testGetPrecio() {
		assertEquals(20000, productoMenu1.getPrecio(),"El costo del menu no es el que se espera");
	}
	@Test
	void testGenerarTextoFactura() {
		String correcto = "Tiernita\n" + "            20000\n";
		assertEquals(correcto, productoMenu1.generarTextoFactura(),"El texto de la factura no es el que se espera");
	}
	
}
