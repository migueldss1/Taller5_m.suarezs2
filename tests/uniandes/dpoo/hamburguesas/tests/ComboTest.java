package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;


public class ComboTest {
	private ProductoMenu hamburguesa;
	private ProductoMenu papas;
	private ProductoMenu jugo;
	private ArrayList<ProductoMenu> elementos;
	private Combo combo;
	
	@BeforeEach
	void setUp() throws Exception{
		hamburguesa = new ProductoMenu("tiernita", 20000);
		papas = new ProductoMenu("papas fritas", 10000);
		jugo = new ProductoMenu("jugo naranja", 5000);
		elementos = new ArrayList<ProductoMenu>();
		elementos.add(hamburguesa);
		elementos.add(papas);
		elementos.add(jugo);
		combo = new Combo ("tiernita y mas", 0.05, elementos);
	}
	
	@AfterEach
	void tearDown() throws Exception
	{
	}
	
	@Test
	void testGetNombre() {
		assertEquals("tiernita y mas", combo.getNombre(),"El nombre del combo no es el esperado");
	}
	@Test
	void testGetPrecio() {
		assertEquals(19000+9500+4750, combo.getPrecio(), "El precio del combo no es el esperado");
	}
	@Test
	void testGenerarTextoFactura() {
		String correcto = "Combo tiernita y mas\n" + " Descuento: 0.05\n" + "            33250\n"  ;
		assertEquals(correcto, combo.generarTextoFactura(),"El texto de la factura no es el que se espera");
	}

}
