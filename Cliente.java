package Unidad5;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class Cliente {

    HttpURLConnection conexion;

    private String obtenerResultado() {

        String xmlResultado = "";

        try {

            InputStreamReader in = new InputStreamReader(conexion.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String entrada = "";

            while ((entrada = br.readLine()) != null) {

                xmlResultado += entrada;
            }

            System.out.println(xmlResultado);
        } catch (IOException e1) {

            System.out.println("Error de conexion " + e1);
        }

        return xmlResultado;
    }

    private Document convertirXML(String cadenaXML) {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            InputSource is = new InputSource(new StringReader(cadenaXML));
            Document documento = docBuilder.parse(is);

            return documento;
        } catch (Exception e1) {

            System.out.println("Error de formato XML " + e1);
            return null;
        }
    }

    public boolean iniciarSesion(String usuario, String password) {

        try {

            String ligaWebService = "http://localhost/webservices/ws_sesion.php";
            String parametros = "usuario=" + usuario + "&password=" + password;

            URL url = new URL(ligaWebService);
            conexion = (HttpURLConnection) url.openConnection();

            conexion.setDoOutput(true); // SE ESPECIFICA QUE SE ENVIARAN DATOS
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("charset", "utf-8");

            /* SE ESCRIBEN LOS PARAMETROS */
            OutputStreamWriter escribir = new OutputStreamWriter(conexion.getOutputStream());
            escribir.write(parametros);
            escribir.flush();
            /* *************** */

            String xmlResultado = obtenerResultado();
            Document documento = convertirXML(xmlResultado);

            NodeList textoResultado = documento.getElementsByTagName("resultado");
            String valor = textoResultado.item(0).getTextContent();

            if (valor.equalsIgnoreCase("1")) {

                System.out.println("Sesión iniciada correctamente");
                return true;
            } else {

                System.out.println("Error al iniciar sesión " + valor);
                return false;
            }
        } catch (Exception e) {

            System.out.println("Error general " + e);
            return false;
        }
    }

    public boolean insertarRegistro(String matricula, String nombre, String apellido,
            String telefono, String correo, int anyoNacimiento, int carrera) {

        try {

            String ligawebservice = "http://localhost/webservices/ws_agregar.php";
            String parametros = "?matricula=" + matricula + "&nombre=" + nombre;
            parametros += "&apellido=" + apellido + "&telefono=" + telefono;
            parametros += "&correo=" + correo + "&anyo=" + anyoNacimiento + "&id_carrera=" + carrera;

            URL url = new URL(ligawebservice + parametros);
            conexion = (HttpURLConnection) url.openConnection();

            //conexion.setDoOutput(true);
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("charset", "utf-8");

            /*OutputStreamWriter escribir = new OutputStreamWriter(conexion.getOutputStream());
            escribir.write(parametros);
            escribir.flush();*/
            String xmlResultado = obtenerResultado();
            Document documento = convertirXML(xmlResultado);

            NodeList textoResultado = documento.getElementsByTagName("resultado");
            String valor = textoResultado.item(0).getTextContent();

            if (valor.equalsIgnoreCase("1")) {

                System.out.println("Registro insertado");
                return true;
            } else {

                System.out.println("Error al insertar: " + valor);
                return false;
            }
        } catch (Exception e) {

            System.out.println("Error general " + e);
            return false;
        }
    }

    public void consultarRegistro(String texto) {

        try {

            String ligawebservice = "http://localhost/webservices/ws_consultar.php";
            String parametros = "?texto_buscar=" + texto;

            URL url = new URL(ligawebservice + parametros);
            conexion = (HttpURLConnection) url.openConnection();

            //conexion.setDoOutput(true);
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("charset", "utf-8");

            /*OutputStreamWriter escribir = new OutputStreamWriter(conexion.getOutputStream());
            escribir.write(parametros);
            escribir.flush();*/
            String xmlResultado = obtenerResultado();
            Document documento = convertirXML(xmlResultado);

            NodeList lista = documento.getElementsByTagName("alumno");

            for (int i = 0; i < lista.getLength(); i++) {

                Element eElement = (Element) lista.item(i);

                String matricula = eElement.getElementsByTagName("matricula").item(0).getTextContent();
                String nombre = eElement.getElementsByTagName("nombre").item(0).getTextContent();
                String apellido = eElement.getElementsByTagName("apellido").item(0).getTextContent();
                String telefono = eElement.getElementsByTagName("telefono").item(0).getTextContent();
                String correo = eElement.getElementsByTagName("correo").item(0).getTextContent();
                String anyoNac = eElement.getElementsByTagName("anyo").item(0).getTextContent();
                String nombreCarrera = eElement.getElementsByTagName("nombre_carrera").item(0).getTextContent();

                System.out.println(
                        "Matricula: " + matricula
                        + "\nNombre: " + nombre
                        + "\nApellido: " + apellido
                        + "\nTelefono: " + telefono
                        + "\nCorreo: " + correo
                        + "\nEdad " + anyoNac
                        + "\nCarrera " + nombreCarrera);
            }
        } catch (Exception e) {

            System.out.println("Error general " + e);
        }
    }

    public static void main(String[] args) {

        Cliente n = new Cliente();

        if (n.iniciarSesion("admin", "admin")) {

            n.insertarRegistro("05020134", "HUGO", "TORRES", "4531170193", "isc.hugo@itsa.edu.mx", 0, 1);
            n.consultarRegistro("05020134");
        }
    }
}
