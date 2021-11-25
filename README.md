# Audio_continu_fin
Application traitement audio en continu

Pour lancer l'application vous devez procédez dans l'ordre suivant:
1)Lancez le start de la classe hello_application. Une fenêtre apparait alors.
2)vous devez remplir toutes les combo boxs,(sélection de l'input,output,de la fréq_éch,du temps de frame et le mode de fonctionnement) appuyez ensuite sur lance le processeur.
3)Vous pouvez ensuite appuyer sur "initialisation affichage" pour voir les représentation du signal ou sur "lance capture du niveau" pour afficher en temps réel le niveau sonore capté.
4)Vous pouvez ensuite mettre en pause les affichages des représentation ou arrêter le processeur.

note:Cette application ne peut pas représenter le spectrogramme et l'affichage des signaux n'est pas parfait.(je n'ai pas réussi à modifier les axes en fonctions des paramètres choisis il faut donc pour l'instant le faire manuellement).
Par ailleurs les effets audio ont des paramètres fixes (fréquence de coupure pour les filtres à respectivement 1000 et 2000 hz pour le passe bas,passe haut et l'écho numérique est un écho à 500 samples de retard avec une diminution (appellé ampli) de 0.5 . Ils se trouvent dans la classe Audio-processor et doivent être modifiés manuellement.
