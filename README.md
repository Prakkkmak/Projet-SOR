# **Examen de TP SOR**

Lévy MARQUES, Thibault

## Préface

Dans le cadre du projet on suppose que l'utilisateur est intelligent et maitrise son outil. Des tests unitaires n'ont pas étés réalisés ce qui ne couvre pas toutes les erreurs possible.

## Partie 1 - Enregistrement des images

##### WebContent/WEB-INF/imgForm.jsp

Le formulaire d'ajout d'image est une simple page JSP avec comme particularité le type de "file" qui permet d'ajouter un fichier au formulaire.

![image-20200406095544071](C:\Users\Prakkmak\AppData\Roaming\Typora\typora-user-images\image-20200406095544071.png)

Ce formulaire est accessible via le lien http://localhost:8080/sor/img.

##### sor/com.pinkprogramming.servlets.ImgServlet

La servlet récupère le contenu du formulaire pour ensuite le traiter et l'envoyer au serveur RMI. Le projet utilise [commons-io](http://commons.apache.org/proper/commons-io/) qui permet d'utiliser `IOUtils`, transformant un `InputStream` en `ByteArray`. 

```java
Part filePart = request.getPart("file");
InputStream is = filePart.getInputStream(); // Get input stream of the file
byte[] bytes = IOUtils.toByteArray(is);
img.setImg(bytes);
```

##### sor/com.pinkprogramming.utils.RmiConnector

Le `RmiConnector` est une classe qui sert de client Rmi. Elle permet d'envoyer une image et de recevoir une image. L'envoi d'image renvoie `true` ou `false` en fonction de la réussite. La servlet utilise le `RmiConnector` pour envoyer l'image sur le port 10001.

##### rmi/com.pingprogamming.interfaces.IServer

```java
public interface IServer extends Remote {
	public boolean saveImg(String name, byte[] bytes) throws RemoteException ;
	public byte[] loadImg(String name) throws RemoteException ;
}
```

`IServer` est l'interface utilisée par le `RmiConnector` ainsi que par l'implémentation du serveur rmi du coté du projet rmi. 

##### rmi/com.pingprogamming.database.ImagesDatabase

La classe `ImagesDatabase` gère toutes les connexions à la base de donnée. Les fonctions sont synchrones donc dépendent de la surcharge du réseau. Dans le cadre du projet SOR le nombre d'utilisateurs simultanés excède pas 3. La connexion utilise un serveur MariaDB.

```mysql
CREATE TABLE IF NOT EXISTS `image_img` (
  `img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_bytes` longblob,
  `img_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
```

##### rmi/com.pinkprogramming.implementations.ServerImpl

Les méthodes `saveImg` et `loadImg` y sont implémentés. Ces méthodes utilisent seulement des méthodes d'instance de la classe `ImagesDatabase` et y retournent le résultat.

##### Exécution

Il est difficile de montrer des traces d'exécutions car le nombre d'affichages console sont limités. Voici l'exemple d'une exécution.

![image-20200406101718512](C:\Users\Prakkmak\AppData\Roaming\Typora\typora-user-images\image-20200406101718512.png)



![image-20200406101901003](C:\Users\Prakkmak\AppData\Roaming\Typora\typora-user-images\image-20200406101901003.png)



Hooray ! Les images sont bien sauvegardés dans la base de donnée.

## Partie 2 - Affichage des images



La plupart des classes ont déjà étés expliquées lors de la partie 1. 

##### sor/WebContent/WEB-INF/imgPrinter.jsp

Le formulaire `imgPrinter.jsp` pour récupérer une image est le même sans la demande de fichier. 

Ce formulaire est accessible via le lien http://localhost:8080/sor/printer.

##### sor/com.pinkprogramming.servlets.ImgPrinterServlet

Après avoir demandé l'image, la servlet s'occupe de l'afficher à l'écran. Cela écrase toute page actuelle. La classe utilise le `RmiConnector` pour charger l'image sur le serveur rmi. Une erreur est affichée si le chargement de l'image n'a pas été possible.

##### rmi/com.pinkprogramming.database.ImagesDatabase

Il a été décidé que si une image à le même nom qu'une autre, seulement la première viendra.

```java
//Get only the first result
if(rs.next()) {
    Blob blob = rs.getBlob("img_bytes");
    bytes = blob.getBytes(1, (int) blob.length());
    blob.free();
    System.out.println("Bytes récupérés");
}
```

On récupère la première image et on l'envoie au client RMI.

##### Exécution

1 -

![image-20200406104002012](C:\Users\Prakkmak\AppData\Roaming\Typora\typora-user-images\image-20200406104002012.png)

2 -

![image-20200406104013028](C:\Users\Prakkmak\AppData\Roaming\Typora\typora-user-images\image-20200406104013028.png)



## Conclusion

Le projet a été effectué quelques heures par jour. Notre but personnel lors de la réalisation du projet était de comprendre l'utilisation des différentes technos et de comment les utiliser ensemble. Concrètement le code n'est pas compliqué. Le rendu du projet peut être amélioré mais d'après nous, entre hors du cadre pédagogique de ce TP. Le projet est une très bonne réalisation pour réviser l'UE SOR. L'utilisation d'une architecture N-Tiers dans un mini projet rend exercice stimulant. 

