package cadastrarbicicletas;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CadastrarBicicletas {
    static public void main(String[] argv) {
        try {
            DocumentBuilderFactory b = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = b.newDocumentBuilder();
            Document myDoc = builder.parse("bicicletas.xml");
            int opcao;
            System.out.println("Digite 1 para Adicionar uma Bicicleta:");
            System.out.println("Digite 2 para Alterar uma Bicicleta:");
            System.out.println("Digite 3 para Excluir uma Bicicleta:");
            System.out.println("Digite 4 para mostrar as Bicicletas disponíveis:");
            Scanner leitura = new Scanner(System.in);
            opcao = leitura.nextInt();

            switch (opcao) {
                case 1:
                    adiciona(myDoc);
                    break;
                case 2:
                    modifica(myDoc);
                    break;
                case 3:
                    remove(myDoc);
                    break;
                case 4:
                    listar(myDoc);
                    break;

                default:
                    System.out.println("Opção não existe, saindo do Programa.");
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }   

    private static void adiciona(Document doc) throws IOException, TransformerException {
        Scanner leitura = new Scanner(System.in);
        Element bicicletas, bicicleta, descricao, marca, modelo, categoria, cor, aro, marchas, condicao, status, preco;

        bicicletas = (Element) doc.getElementsByTagName("bicicletas").item(0);
        bicicleta = doc.createElement("bicicleta");

        System.out.print("Informe o numero da bicicleta: ");
        bicicleta.setAttribute("numero", leitura.nextLine());
        
        descricao = doc.createElement("descricao");
        marca = doc.createElement("marca");
        System.out.print("Informe a marca: ");
        marca.appendChild(doc.createTextNode(leitura.nextLine()));
        modelo = doc.createElement("modelo");
        System.out.print("Informe o modelo: ");
        modelo.appendChild(doc.createTextNode(leitura.nextLine()));
        categoria = doc.createElement("categoria");
        System.out.print("Informe a categoria: ");
        categoria.appendChild(doc.createTextNode(leitura.nextLine()));
        cor = doc.createElement("cor");
        System.out.print("Informe a cor: ");
        categoria.appendChild(doc.createTextNode(leitura.nextLine()));
        aro = doc.createElement("aro");
        System.out.print("Informe o tamanho do aro: ");
        categoria.appendChild(doc.createTextNode(leitura.nextLine()));
        marchas = doc.createElement("marchas");
        System.out.print("Informe a quantidade de marchas: ");
        categoria.appendChild(doc.createTextNode(leitura.nextLine()));
        descricao.appendChild(marca);
        descricao.appendChild(modelo);
        descricao.appendChild(categoria);
        descricao.appendChild(cor);
        descricao.appendChild(aro);
        descricao.appendChild(marchas);
        bicicleta.appendChild(descricao);
        
        condicao = doc.createElement("condição");
        status = doc.createElement("status");
        System.out.print("Informe o status: ");
        status.appendChild(doc.createTextNode(leitura.nextLine()));
        preco = doc.createElement("preco");
        System.out.print("Informe o preço: ");
        preco.appendChild(doc.createTextNode(leitura.nextLine()));
        condicao.appendChild(status);
        condicao.appendChild(preco);
        bicicleta.appendChild(condicao);
        
        bicicletas.appendChild(doc.createComment("Arquivo de Cadastro de bicicletas"));

        XMLSerializer serializer = new XMLSerializer(
            new FileOutputStream("bicicletas.xml"), new OutputFormat(doc, "iso-8859-1", true));
        serializer.serialize(doc);
    }
    
    private static void modifica(Document doc) throws TransformerException, IOException{
        Scanner leitura = new Scanner(System.in);
        int pos = -1;

        System.out.print("Informe o numero da bicicleta que deseja modificar: ");
        String numero = leitura.nextLine();

        NodeList nl = doc.getElementsByTagName("bicicleta");

        for(int i=0; i<nl.getLength(); i++){
            if (nl.item(i).getAttributes().item(0).getNodeValue().equals(numero))
                pos=i;
        }
        
        if (pos == -1){
            System.out.println("Bicicleta nao localizada!");
            return;
        }
        
        System.out.print("Informe a marca: ");
        doc.getElementsByTagName("marca").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe o modelo: ");
        doc.getElementsByTagName("modelo").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe a categoria: ");
        doc.getElementsByTagName("categoria").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe a cor: ");
        doc.getElementsByTagName("cor").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe o tamanho do aro: ");
        doc.getElementsByTagName("aro").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe a quantidade de marchas: ");
        doc.getElementsByTagName("marchas").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe o status: ");
        doc.getElementsByTagName("status").item(pos).setTextContent(leitura.nextLine());
        System.out.print("Informe o preço: ");
        doc.getElementsByTagName("preco").item(pos).setTextContent(leitura.nextLine());
        
        XMLSerializer serializer = new XMLSerializer(
            new FileOutputStream("bicicletas.xml"), new OutputFormat(doc, "iso-8859-1", true));
        serializer.serialize(doc);
    }
    
    private static void remove(Document doc) throws FileNotFoundException, IOException{
        Scanner leitura = new Scanner(System.in);
        int pos = -1;

        System.out.print("Informe o numero da bicicleta que deseja remover: ");
        String numero = leitura.nextLine();

        NodeList nl = doc.getElementsByTagName("bicicleta");

        for(int i=0; i<nl.getLength(); i++){
            if (nl.item(i).getAttributes().item(0).getNodeValue().equals(numero))
                pos = i;
        }

        if (pos == -1){
            System.out.println("Bicicleta nao localizado!");
            return;
        }

        Element excluir = (Element) doc.getElementsByTagName("bicicleta").item(pos);

        excluir.getParentNode().removeChild(excluir);
        
        XMLSerializer serializer = new XMLSerializer(
            new FileOutputStream("bicicletas.xml"), new OutputFormat(doc, "iso-8859-1", true));
        serializer.serialize(doc);
        }
    
    public static void listar(Document doc) {
        try {
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            System.out.println("Aqui esta o xml:\n\n" + xmlString);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
    
    
    
    

