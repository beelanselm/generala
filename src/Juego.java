import java.awt.HeadlessException;
import java.io.ObjectInputStream.GetField;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Juego  {

	private ArrayList<Jugador> listaJugadores;
	private int inputPrincipal;
	int vueltaXJugador;
	private ArrayList<Jugada> jugadas;
	
	public Juego()
	{
		setInputPrincipal(0);
		setListaJugadores(new ArrayList<>());
		setJugadas(new ArrayList<Jugada>());
		setVueltaXJugador(1);
		
	}
	
	
	public void cargarCantidadJugadores() throws exceptionCantidadPlayers
	{
		int cantidad=Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de jugadores:" + "2,3,4 jugadores"));
		        
		if(cantidad>=2 || cantidad<=4)
		{
			for(int i=0;i<cantidad;i++)
			{
				String nombre= JOptionPane.showInputDialog("Ingrese el nombre");
				getListaJugadores().add(new Jugador(nombre));
			}
		}
		else
		{
			throw new exceptionCantidadPlayers("Ingresar cantidad correcta de jugadores");
		}
	}
	
	public boolean tacharJugada(Jugador j) throws ExceptionjugadaAnotada
	{
		String input= JOptionPane.showInputDialog("ESCRIBA EL NOMBRE DE LA JUGADA QUE DESEA TACHAR: ");
		if(j.anotarResultado(input, Jugador.getPuntostachar()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
    public void menuTachar(Jugador j) throws ExceptionjugadaAnotada 
    {
			if(tacharJugada(j))
			{
				JOptionPane.showMessageDialog(null, "JUGADA TACHADA");
				setVueltaXJugador(4);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "VOLVIENDO AL MENU PRINCIPAL");
				menuPrincipal();
				seleccionarMenu(j);
			}
    }
	
	public void menuSeparar(Jugador j)
	{
		if(j.getListaDados().size()==0)
		{
			JOptionPane.showMessageDialog(null, "SE HAN SEPARADO TODOS LOS DADOS, REINCORPORE DADOS O SALGA DEL MENU PRINCIPAL PARA CONTINUAR");
		}
		else
		{
		int input= Integer.parseInt(JOptionPane.showInputDialog("OPCIONES - ELIJA UN VALOR A SEPARAR:"+ "\n" + j.menuSepararDados()+ "\n" + "salir= 0"));
		if(input==0)
		{
			JOptionPane.showMessageDialog(null,  "SALIENDO AL MENU PRINCIPAL");
		}
		else
		{
			if(j.separarDados(j.getListaDados(),input))
			{
				j.agregarSeparadoPrevio(input);
				menuSeparar(j);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Valor inexistente , vuelva a intentar o elija salir para continuar");
				menuSeparar(j);
			}	
		}
	    }
	}
	
	public void menuRecuperar(Jugador j)
	{
		if(j.getSeparadosPrevio().size()==0)
		{
			JOptionPane.showMessageDialog(null, "SE HAN RECUPERADO TODOS LOS DADOS");
		}
		else
		{
		int input= Integer.parseInt(JOptionPane.showInputDialog("OPCIONES - ELIJA UN VALOR A REINCORPORAR:"+ "\n" + j.menuRecuperarDados()+ "\n" + "salir= 0"));
		if(input==0)
		{
			JOptionPane.showMessageDialog(null,  "SALIENDO AL MENU PRINCIPAL");
		}
		else
		{
			if(j.recuperarDados(j.getSeparadosPrevio(), input))
			{
				j.agregarDado(input);
				menuRecuperar(j);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Valor inexistente , vuelva a intentar o elija salir para continuar");
				menuRecuperar(j);
			}	
		}
	    }
	}
	
	public void menuPrincipal()
	{
		int input= Integer.parseInt(JOptionPane.showInputDialog("1-SEPARAR:"
                +"\n"+ //reverse?
                "2-REINCORPORAR"+"\n"+
                "3-SUMAR"+"\n"+
                "4-TACHAR"+"\n"+
                "5-SAVE"+"\n"+
                "6-LOAD"+"\n"+
                "0-SALIR O CONTINUAR"));
		setInputPrincipal(input);
	}
	
	
	public void seleccionarMenu(Jugador j) throws ExceptionjugadaAnotada
	{
		switch(getInputPrincipal()) {
  	  case 1: //separar
  	    menuSeparar(j);
  	    menuPrincipal();
  	    //testear si encuentra jugada
  	    break;
  	  case 2:
  	    menuRecuperar(j);
  	    menuPrincipal();
  	    break;
  	  case 3:
    	
    	break;
      case 4:
    	menuTachar(j);
    	break;
      case 0:
    	j.getSeparados().addAll(j.getSeparadosPrevio());
    	j.borrarListaSeparadosPrevio();
    	break;	
  	  default:
  		  JOptionPane.showMessageDialog(null, "Valor inexistente, vuelva a intentarlo");
  		  menuPrincipal();
  		  seleccionarMenu(j);
		}
	}
    public void Jugar() throws ExceptionjugadaAnotada
    {
    	try {
			cargarCantidadJugadores();
		} catch (exceptionCantidadPlayers e) {
			
			e.printStackTrace();
		}
    	
    	int vueltaPrincipal=1;
		while(vueltaPrincipal<=10)
		{
			for(Jugador jugador:getListaJugadores())
			{
				setVueltaXJugador(1);
				jugador.borrarListaseparados();
				while(getVueltaXJugador()<=3)
				    {
				    	jugador.TirarDados();
				    	//ver si encuentra una jugada sino
				    	menuPrincipal();
				    	seleccionarMenu(jugador);
				    	
				    	
				    	if(getVueltaXJugador()==3)
				    	{
				    		if(tacharJugada(jugador))
				    		{
				    			JOptionPane.showMessageDialog(null, "JUGADA TACHADA");
				    		}
				    		else
				    		{
				    			tacharJugada(jugador);
				    		}
				    	}
				    	setVueltaXJugador(getVueltaXJugador()+1);
				    }
			}			
         }
    }		
    
	public ArrayList<Jugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}


	public int getInputPrincipal() {
		return inputPrincipal;
	}


	public void setInputPrincipal(int inputPrincipal) {
		this.inputPrincipal = inputPrincipal;
	}


	public ArrayList<Jugada> getJugadas() {
		return jugadas;
	}


	public void setJugadas(ArrayList<Jugada> jugadas) {
		this.jugadas = jugadas;
	}


	public int getVueltaXJugador() {
		return vueltaXJugador;
	}


	public void setVueltaXJugador(int vueltaXJugador) {
		this.vueltaXJugador = vueltaXJugador;
	}
	
	
	

	
	
	
	
}
