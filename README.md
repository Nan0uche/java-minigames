# java-minigames

## INTRODUCTION
Ce README vous guide à travers notre projet Java, qui comprend plusieurs mini-jeux. Vous y trouverez toutes les informations nécessaires pour commencer à utiliser et contribuer à notre application.

### POUR COMMENCER

1. Clonez ce dépôt Git en utilisant la commande suivante :
    git clone "lien du répo"
2. lancez le main.

### NOS JEUX

- BlackJack
- Démineur (encore en dev)
- FlappyBird
- Memory
- Snake
- Sudoku
- VraiOuFaux
- Pendu

### LES REGLES

### 1. BlackJack
   Le BlackJack est un jeu de cartes où l'objectif est d'atteindre une main dont la valeur est la plus proche possible de 21, sans la dépasser. Les cartes numérotées valent leur valeur nominale, les figures (roi, dame, valet) valent 10 points, et l'as peut valoir soit 1 point, soit 11 points selon ce qui est avantageux pour le joueur. Le croupier distribue deux cartes à chaque joueur et deux à lui-même, une face visible et une face cachée. Les joueurs peuvent demander des cartes supplémentaires ("hit") pour s'approcher de 21 ou rester avec leur main actuelle ("stand"). Le croupier révèle sa carte cachée et doit frapper jusqu'à ce que sa main atteigne au moins 17 points. Le joueur le plus proche de 21 sans le dépasser remporte la manche.

### 2. Démineur
   Le Démineur est un jeu de logique où le joueur doit découvrir les cases vides du plateau sans déclencher de mines cachées. Chaque case peut soit être vide, contenir un nombre indiquant le nombre de mines adjacentes, soit cacher une mine. En cliquant sur une case vide, le joueur révèle son contenu. Si une case vide n'a aucune mine adjacente, toutes les cases adjacentes vides sont également révélées. Si une case révélée contient un nombre, il indique combien de mines sont adjacentes. Le joueur doit utiliser ces informations pour déterminer l'emplacement des mines et marquer celles-ci avec un drapeau.

### 3. FlappyBird
   Dans Flappy Bird, le joueur contrôle un chat tartine qui doit voler à travers un ensemble d'obstacles en forme de tuyaux sans les toucher. En tapant sur l'écran ou en appuyant sur une touche, l'oiseau battra des ailes et montera. En relâchant, l'oiseau commencera à descendre en raison de la gravité. Le but est de naviguer à travers les espaces entre les tuyaux en volant le plus loin possible.

### 4. Memory
   Memory est un jeu de société où le joueur retourne deux cartes à la fois pour trouver des paires correspondantes. Les cartes sont placées face cachée et mélangées. À chaque tour, le joueur retourne deux cartes. Si elles correspondent, elles restent découvertes. Sinon, elles sont retournées face cachée et le joueur doit se rappeler de leur emplacement. Le jeu se poursuit jusqu'à ce que toutes les paires soient trouvées.

### 5. Snake
   Dans Snake, le joueur contrôle un serpent qui se déplace à travers un espace de jeu. Le serpent peut se déplacer vers le haut, le bas, la gauche ou la droite. En mangeant des aliments, le serpent grandit. Le but est de faire grandir le serpent le plus possible sans le faire heurter les murs ou se mordre lui-même.

### 6. Sudoku
   Le Sudoku est un jeu de logique où le joueur remplit une grille 9x9 avec des chiffres de 1 à 9, en veillant à ce qu'aucun chiffre ne se répète dans une ligne, une colonne ou un carré 3x3. La grille commence partiellement remplie, et le joueur doit remplir les cases vides en suivant les règles du jeu.

### 7. Vrai ou Faux
   Dans Vrai ou Faux, le joueur est confronté à une série d'affirmations et doit déterminer si elles sont vraies ou fausses. Le joueur sélectionne sa réponse et reçoit un retour immédiat sur sa véracité.

### 8. Pendu
   Le Pendu est un jeu de devinette où le joueur doit deviner un mot en proposant des lettres une à une. Le jeu commence avec un certain nombre de tirets représentant les lettres du mot à deviner. À chaque fois que le joueur propose une lettre, si elle est présente dans le mot, elle est révélée à toutes les positions correspondantes. Si la lettre n'est pas dans le mot, une partie du pendu est dessinée. Le joueur gagne s'il devine le mot avant que le dessin du pendu ne soit complet, sinon il perd.

### NOS AJOUTS

- statistiques des parties (via le menu stats)
- possibilité de configurer certains jeux ou d'ajouter ses propres mots pour le pendu ou des questions pour le VraiOuFaux

### LA BASE DE DONEES
Étape 1: Configuration de la base de données avec WampServer
Installation de WampServer:

Téléchargez et installez WampServer à partir du site officiel.
Lancement de WampServer:
Installation de MySQL: Si vous n'avez pas encore MySQL installé sur votre machine, vous pouvez le télécharger et l'installer à partir du site officiel MySQL

Après l'installation, lancez WampServer. Assurez-vous que les services Apache et MySQL sont démarrés.
Accès à phpMyAdmin:

Ouvrez un navigateur Web et accédez à phpMyAdmin en saisissant l'URL http://localhost/phpmyadmin.
Création de la base de données et des tables:

Connectez-vous à phpMyAdmin en utilisant le nom d'utilisateur et le mot de passe par défaut (généralement root et une chaîne vide pour le mot de passe).
Créez une nouvelle base de données nommée minigames.

Étape 2: Ajout de la librairie
Ajouter le .jar dans le dossier lib et le mettre dans la librairie du projet

Étape 3: Configuration du projet
Modifiez les constantes URL, USERNAME, et PASSWORD avec les détails de connexion à votre base de données MySQL (généralement jdbc:mysql://localhost:3306/minigames, root pour l'utilisateur, et une chaîne vide pour le mot de passe) dans les classes nécéssitant des appels à la base de donnée.

### Remerciements
Ce projet a été créé en collaboration par trois étudiants dans le but de rendre un devoir pour leurs curus à Bordeaux Ynov Campus.

- ROUSSEL Mathéo
- DERC Nathan
- CHIPPEY Theo


