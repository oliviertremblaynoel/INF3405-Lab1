- [ ] Programme : Nos objectifs
    - [x] exit -> OLI
    - [x] ls -> OLI
    - [x] mkdir -> OLI
        - [x] Ajouter correction si pas de deuxième mot-> NILS
        - [x] FIX : Client freezes after successful mkdir -> NILS
    
    - [x] L'interface client (Switch case) - started
    - [x] Server message header : remove / symbol
    - [x] Upload client-server -> NILS
    - [x] Download client-server -> NILS
    - [x] BONUS : prevent Traversal attack (prevent cd ..)
    - [x] cd -> GAB
      - [ ] Corriger front slash
    - [x] Remettre le verif IP (après tous les tests)
      - [ ] ip pas de 0 avant nombre -> 001.002.etc
    - [ ] Compiler le tout


---

- [x] Programme Consignes du cours
    - [x] Vérifier la validité de l’adresse IP saisie (uniquement le format) et le numéro de port (entre 5000 et 5050)
        - Si on entre le mauvais port, message d'erreur
        - Ressayer ou quitter
    - [x] Saisie des paramètres du serveur dans l’interface console du client et celle du serveur (adresse IP et port d’écoute entre 5000 et 5050)
    - [x] Téléverser et télécharger un fichier
    - [x] Pouvoir se déplacer dans la hiérarchie des répertoires du serveur de stockage à partir du client
    - [x] Pouvoir énumérer les répertoires et les fichiers au niveau du serveur de stockage à partir du client
    - [x] Pouvoir créer un répertoire à partir du client sur le serveur de stockage à partir du client
    - [x] Pouvoir se déconnecter adéquatement du serveur de stockage
    - [x] Afficher en temps réel les demandes à traiter (logs au niveau de la console serveur)

- [ ] Rapport : 3 pages max

- [ ] Soumission
    - [ ] Assurez-vous que les livrables compilent et s’exécutent adéquatement sur les ordinateurs du laboratoire !
    - Le livrable est une archive (ZIP ou RAR) ayant le format suivant:
        INF3405_TP1_matriculeX_matriculeY où matriculeX > matriculeY
    - Votre archive contiendra les fichiers suivants :
        - [ ]Les projets Eclipse du client et du serveur incluant les fichiers sources (.java), autrement dit le dossier en entier contenant votre projet
        - [ ] Le rapport au format PDF
        - [ ] Les fichiers exécutables de votre client et votre serveur (.jar)
        testgab