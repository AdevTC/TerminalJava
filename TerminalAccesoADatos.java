import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TerminalAccesoADatos {
    public class TerminalAcesoADatos {
        public Path rutabase;
        public TerminalAcesoADatos() {
            this.rutabase = Paths.get(System.getProperty("user.dir"));
        }
        public String help() {
            return "<html><body>" + "<p>●help-> Lista los comandos con una breve definición de lo que hacen.</p>" + "<p>●cd-> Muestra el directorio actual.</p>" + "<p>	▪[..] -> Accede al directorio padre.</p>" + "<p>	▪[<nombreDirectorio>]-> Accede a un directorio dentrodel directorio actual.</p>" + "<p>	▪[<rutaAbsoluta]-> Accede a la ruta absoluta del sistema.</p>" + "<p>●mkdir<nombre_directorio>-> Crea un directorio enla ruta actual.</p>" + "<p>●info <nombre>-> Muestra la información del elemento indicando FileSystem,Parent, Root, Nº of elements, FreeSpace, TotalSpace y UsableSpace.</p>" + "<p>●cat <nombreFichero>-> Muestra el contenido de un fichero.</p>" + "<p>●top <numeroLineas> <nombreFichero>-> Muestra las líneas especificadas de un fichero.</p>" + "<p>●mkfile <nombreFichero> <texto>-> Crea un fichero con ese nombre y el contenido de texto.</p>" + "<p>●write <nombreFichero> <texto>-> Añade 'texto' al final del fichero especificado.</p>" + "<p>●dir-> Lista los archivos o directorios de la rutaactual.</p>" + "<p>	▪[<nombreDirectorio>]-> Lista los archivos o directorios dentro de esedirectorio.</p>" + "<p>	▪[<rutaAbsoluta]-> Lista los archivos o directorios dentro de esa ruta.</p>" + "<p>●delete <nombre>-> Borra el fichero, si es un directorio borra todo su contenido y a si mismo.</p>" + "<p>●close-> Cierra el programa.</p>" + "<p>●Clear-> Vacía la vista.</p>" + "</body></html>";
        }
        public String cd(String[] valores) {
            String cd = "";
            if (valores.length == 2) {
                if (valores[1].equals("..")) {
                    this.rutabase = rutabase.getParent();
                    cd = this.rutabase.toString();
                } else {
                    Path nuevaRuta = Paths.get(rutabase.resolve(valores[1]).toString());
                    File rutaNueva = new File(nuevaRuta.toString());
                    if (rutaNueva.exists()) {
                        if (nuevaRuta.isAbsolute()) {
                            rutabase = nuevaRuta;
                            cd = rutabase.toString();
                        } else {
                            rutabase = rutabase.resolve(nuevaRuta);
                            cd = rutabase.toString();
                        }
                    } else {
                        cd = "No existe esa ruta. Comprueba que la has escrito bien.";
                    }
                }
            } else if (valores.length < 2) {
                cd = rutabase.toString();
            } else {
                cd = "Has introducido mal la ruta. Consulta 'help'.";
            }
            return cd;
        }
        public String mkdir(String[] valores) {
            String mkdir = "";
            File fichero = new File(rutabase.resolve(valores[1]).toString());
            if (!fichero.exists()) {
                fichero.mkdir();
                mkdir = "Directorio creado con éxito";
            } else {
                mkdir = "Ya existe ese directorio.";
            }
            return mkdir;
        }
        public String info(String[] valores) {
            String info = "";
            Path ruta = Paths.get(rutabase.resolve(valores[1]).toString());
            File r = new File(rutabase.resolve(valores[1]).toString());
            if (r.exists()) {
                info = "<html><body><br>File System: " + ruta.getFileSystem() + "</br>" + "<br>Parent: " + ruta.getParent() + "</br>" + "<br>Root: " + ruta.getRoot() + "</br>" + "<br>Nº of elements: " + ruta.getNameCount() + "</br>" + "<br>Free Space " + r.getFreeSpace() + "</br>" + "<br>Total Space: " + r.getTotalSpace() + "</br>" + "<br>Usable Space: " + r.getUsableSpace() + "</br>";
            } else {
                info = "Introduce un directorio existente";
            }
            return info;
        }
        public String cat(String[] valores) {
            String cat = "";
            try {
                File f = new File(rutabase.resolve(valores[1]).toString());
                FileReader fichero = new FileReader(f + ".txt");
                BufferedReader contenido = new BufferedReader(fichero);
                int i = 0;
                if (f.exists()) {
                    while ((i = contenido.read()) != -1) {
                        char c = (char) i;
                        cat += Character.toString(c);
                    }
                } else {
                    cat = "No existe ese fichero";
                }
                contenido.close();
            } catch (IOException e) {
                cat = "No existe ese fichero";
            }
            return cat;
        }
        public String mkfile(String[] valores) {
            String mkfile = "";
            if (valores.length > 2) {
                try {
                    File fichero = new File(rutabase.resolve(valores[1] + ".txt").toString());
                    if (fichero.exists()) {
                        mkfile = "Ya existe ese archivo";
                    } else {
                        FileWriter file = new FileWriter(fichero);
                        BufferedWriter buffer = new BufferedWriter(file);
                        for (int i = 2; i < valores.length; i++) {
                            buffer.write(valores[i] + " ");
                        }
                        buffer.flush();
                        buffer.close();
                        mkfile = "Fichero generado con éxito";
                    }
                } catch (IOException e) {
                    mkfile = "Introduce texto para meterlo en el archivo";
                }
            } else {
                mkfile = "Introduce texto para meterlo en el archivo";
            }
            return mkfile;
        }
        public String top(String[] valores) {
            String top = "<html><html/>";
            try {
                File f = new File(rutabase.resolve(valores[2]).toString());
                FileReader fichero = new FileReader(f);
                BufferedReader Texto = new BufferedReader(fichero);
                for (int i = 0; i < Integer.parseInt(valores[1]); i++) {
                    String contenido = "<p>" + Texto.readLine() + "</p>";
                    if (contenido != null) {
                        top += contenido;
                    }
                }
            } catch (FileNotFoundException noEncontrado) {
                top = "El archivo especificado no ha sido encontrado.";
            } catch (IOException ex) {
                top = "Error de lectura del fichero.";
            }
            return top;
        }
        public String dir(String[] valores) {
            String dir = "";
            if (valores.length == 2) {
                Path ruta = Paths.get(valores[1]);
                File f = new File(ruta.toString());
                if (f.isAbsolute()) {
                    File absoluta = new File(valores[1]);
                    File[] lista = absoluta.listFiles();
                    for (int i = 0; i < lista.length; i++) {
                        File file_lista = lista[i];
                        if (file_lista.isDirectory()) {
                            dir += "<html><body><br>Directorio: " + file_lista.getName() + "<br/><body/><html/>";
                        } else {
                            dir += "<html><body><br>Fichero: " + file_lista.getName() + "<br/><body/><html/>";
                        }
                    }
                } else {
                    File relativa = new File(rutabase.resolve(valores[1]).toString());
                    File[] lista = relativa.listFiles();
                    for (int i = 0; i < lista.length; i++) {
                        File file_lista = lista[i];
                        if (file_lista.isDirectory()) {
                            dir += "<html><body><br>Directorio: " + file_lista.getName() + "<br/><body/><html/>";
                        } else {
                            dir += "<html><body><br>Fichero: " + file_lista.getName() + "<br/><body/><html/>";
                        }
                    }
                }
            } else if (valores.length == 1) {
                File vacio = new File(rutabase.toString());
                File[] lista = vacio.listFiles();
                for (int i = 0; i < lista.length; i++) {
                    File file_lista = lista[i];
                    if (file_lista.isDirectory()) {
                        dir += "<html><body><br>Directorio: " + file_lista.getName() + "<br/><body/><html/>";
                    } else {
                        dir += "<html><body><br>Fichero: " + file_lista.getName() + "<br/><body/><html/>";
                    }
                }
            }
            return dir;
        }
        public boolean delete(String fichero) {
            boolean result = false;
            File pruebatxt = new File(this.rutabase.resolve(fichero).toString());
            borrarDirectorio(pruebatxt);
            if (pruebatxt.exists()) {
                pruebatxt.delete();
                result = true;
            } else {
            }
            return result;
        }
        public static void borrarDirectorio(File directorio) {
            File[] ficheros = directorio.listFiles();
            for (int i = 0; i < ficheros.length; i++) {
                if (ficheros[i].isDirectory()) {
                    borrarDirectorio(ficheros[i]);
                }
                ficheros[i].delete();
            }
        }
    }
    public void write(String[] valores) {
        if (valores.length == 3) {
            String nombre = valores[1];
            String salida = "";
            Path rutabase;
            rutabase = Paths.get(System.getProperty("user.dir"));
            try {
                File fichero = new File(rutabase.resolve(nombre + ".txt").toString());
                if (fichero.exists()) {
                    FileWriter file = new FileWriter(fichero);
                    BufferedWriter buffer = new BufferedWriter(file);
                    buffer.write(valores[2]);
                    buffer.flush();
                    buffer.close();
                    salida = "Fichero generado con éxito";
                } else {
                    salida = "No existe ese archivo";
                }
            } catch (IOException e) {
                salida = "Introduce texto para meterlo en el archivo";
            }
            File f = new File(rutabase.resolve(nombre).toString());
            if (!f.exists()) {
                System.out.println("Ese fichero no existe");
            } else {
                try {
                    String texto = valores[2];
                    FileReader fileLectura = new FileReader(f);
                    BufferedReader bufferLectura = new BufferedReader(fileLectura);
                    System.out.println("Leyendo fichero...");
                    int i = 0;
                    while ((i = bufferLectura.read()) != -1) {
                        char c = (char) i;
                        salida += c + "";
                    }
                    FileWriter file = new FileWriter(f);
                    BufferedWriter buffer = new BufferedWriter(file);
                    buffer.write(salida + " " + texto);
                    buffer.flush();
                    buffer.close();
                    System.out.println("Fichero escrito");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

