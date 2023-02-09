# 1. Introduction 

Dans le cadre de ce TP, nous avons réalisé une application client-serveur nous permettant de stocker des fichiers sur un serveur de stockage. Pour ce faire, nous avons implémenté une application console qui permet à l'utilisateur d'exécuter une variété de commandes pour interagir avec le serveur. Suite à la connexion au serveur, nous voulons avoir la possibilité de naviguer vers les répertoires enfant ou parent à partir du client, d'afficher la liste des fichiers dans le répertoire courant, de créer des dossiers vides, de téléverser des fichiers provenant du client vers le serveur, de télécharger des fichiers provenant du serveur vers notre répertoire local et enfin, lorsqu'on a fini, de se déconnecter du serveur de stockage. Enfin, nous voulons afficher en temps réel l'historique des commandes utilisées.

# 2. Présentation de vos travaux

Le projet a nécessité plusieurs bibliothèques pour l'implémentation de notre programme, soit :
- java.io : en combinant les DataInputStream/DataOutputStream aux Sockets de la librairie java.net on peut échanger des informations entre le client et le serveur. 
- java.net : pour la connexion, la communication et le partage de données entre le client et le serveur(Socket).
- java.time : pour afficher le temps de chaque requête sur le server.
- java.util : pour des manipulations de tableaux, pour l'utilisation de regex dans la vérification du format des adresses ip/numéros de port et enfin pour les Scanners qui nous permette de gérer l'interaction avec le client à travers la console.

Voici un scénario d'exécution du programme avec quelques détails techniques concernant l'implémentation des diverses fonctionnalités. 

Premièrement, le server est lancé. Il ouvre un socket sur son adresse ip du réseau local (LAN) et attend la connection d'un client sur le port 5000. Lorsqu'un client se connecte, un nouveau thread est créé et ce client est géré par la classe ClientHandler. Le thread principal du server attend ensuite la connection d'un autre client potentiel, ce qui permet de connecter plusieurs clients simultanément sur le même port.

Côté client, dès que l'application est lancée, on demande à l'utilisateur les informations pour se connecter au server. En utilisant des expressions régulières (regex), on vérifie les informations pour la connexion du côté client. Le  format de l'adresse IP doit contenir 4 nombres entre 0 et 255 séparés par des points et le port doit être un nombre entre 5000 et 5050. Par contre, la connection ne peut seulement être établie que si le port fourni est 5000, soit celui sur lequel le serveur écoute. La connexion et la communication entre le serveur et le client passe par les Sockets. Dans tout ce processus, notre interface permet au client plusieurs tentatives avant de quitter le programme. Pour ce faire, il utilise la classe Retry, qui est donc potentiellement réutilisable tout au long du processus.

Une fois connecté, le client a quelques options d'entrée. Si la commande est invalide, l'utilisateur reçoit un message l'en informant :

- aide : affiche les commandes possibles.

- cd <nom/du/dossier>: permet de changer le dossier courant chez le serveur. Pour des raisons de sécurité, il est impossible de reculer plus bas que le dossier initial (celui où le server est lancé). La position dans le répertoire de dossier est conservée dans la classe ClientHandler et est une information contextuelle utilisée dans les autres commandes. 

- ls : retourne les dossiers et les fichiers contenus dans le dossier courant (triés par dossier, puis fichier, alphabétiquement).

- mkdir <nomDuDossier> : permet de créer un nouveau dossier nommé d'après l'argument fourni avec la commande s'il n'existe pas déjà dans le dossier courant.

- upload <chemin/ver/le/fichier> et download <chemin/vers/le/fichier> : permettent respectivement de téléverser et télécharger un dossier depuis le dossier spécifié et le copier dans le dossier courant (selon le cas). Upload et download utilisent des classes commune pour envoyer des fichiers (SendFile.java) et recevoir des fichiers (ReceiveFile.java). Upload va envoyer des fichiers au serveur et download va recevoir des fichiers envoyé par le serveur. Nous avons fait des classes à part pour ces deux fonctionnalités pour pouvoir réutiliser le code autant chez le client que chez le server.

- exit : permet finalement au client de quitter le serveur et fermer la connexion.

# 3. Difficultés rencontrées lors de l’élaboration du TP et les éventuelles solutions apportées.

Nous avons rencontré quelques difficultés lors de la réalisation de cette application. Premièrement, puisque dans notre code nous avons fait des classes SendFile et ReceiveFile qui sont communes à l'implémentation du upload et du download nous avons eu des difficultés à garder le code réutilisable pour le client et le serveur. 

Le client et le serveur étant agnostiques de leur situation mutuelle, il a été complexe de rendre la communication efficace pour terminer l'échange de fichier et de commande au bon moment selon toutes les circonstances du code (fichier inexistant, commande invalide, etc).

Implémenter un cd fonctionnel vérifiant l'existence et la validité du chemin spécifié, tout en s'assurant de ne pas pouvoir dépasser la racine virtuelle du serveur s'est révélé comme une difficulté qu'il a fallu surmonter afin de mener à bien notre projet. En effet, l'entrelacement des différents tableaux, et variables a nécessité une certaine précaution dans la rédaction du code.

Finalement, lier la notion de dossier courant (current directory) au sein des autres méthodes a été un défi.

# 4. Critiques et améliorations

Le TP est assez simple et il permet de se familiariser avec les échanges entre client et server, ainsi que les contraintes que cela incombe. Il permet d'explorer des connaissances connexes comme le regex, la gestion d'erreurs et le UX. C'est également l'occasion de se familiariser avec les bibliothèques mentionnées ci-haut. 

Le TP est globalement bien balancé.

# 5. Conclusion 

Nos attentes ont été comblées lors du TP. Cette expérience a été formatrice et nous a permis de comprendre ce qui se cache "sous le capot" des applications client-serveur ainsi que de comprendre les commmandes de base pour la navigation dans les terminaux (ls, cd, mkdir, etc). Finalement, le TP nous a sensibilisé la la transmission de fichiers sur un socket, qui doit absolument se faire en divisant un fichier volumineux par petits paquets. 