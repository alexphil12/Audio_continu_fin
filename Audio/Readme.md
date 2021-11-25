  La partie audio comporte l'AudioProcessor, l'AudioIO et l'AudioSignal et le Framesize.
  Le Framesize est simplement une classe contenant la fréquence d'échantillonage ainsi que le temps de frame, 
elle calcul alors le nombre de sample dans chaque frame et sert à la réalisation de la classe audio_processor et audio Signal.
  AudioIO contient des fonctions static utiles pour le traitement(celles permettant notemment d'obtenir la target ainsi que la source dataline pour le traitement ainsi que
les fonctions appellées pour lancer l'application.
  AudioSignal est la classe utilisée pour le traitement audio, elle permet la conversion entre le buffer rempli/lu par la target/source data_line et le buffer de double
sur lequel on peut appliquer nos effets sonores(fonction record from et play to)
  AudioProcessor enfin fait comme son nom le suggère tout les calculs et le traitement audio. il contient les fonctions de traitement audio comme filtre_passe_bas.
  
