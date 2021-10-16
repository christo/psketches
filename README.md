# Generative Art - Processing Sketches in Java

Graphics sketches using Processing as a library in a gradle project
for ease of version control and to let me use a decent IDE. 

This is intended to be the beginning of a mini framework for 
conveniently creating video or gif loops of a desired duration.  
It should be easy to make seamlessly repeating loops by parametising
by a trigonometric cycle of `2 * Pi` and supporting only ingegral
divisions of this. 

# TODO

* Make `theta` divisions explicitly integral - so that epicycles always
  complete neatly on the master loop for seamlessness.
* Have a warm-up loop length so lingering artifacts get a chance to 
  stabilise such that the looped clip is seamless (requires some 
  experimentation and testing.)
* Automate `.gif` and `.m4v` export. The Processing lib `VideoExport`
  may not be convenient to add as a gradle dep.
* Automate social network uploads 
  * Tiktok
  * Instagram
  * Twitter
* need to decide about music/sound for video items. Generative sound
  is a nice to have, but it must sound good enough. Libs?
* Configure target dimensions with dynamic scaling. Tiktok wants 720:1280, 
  Twitter probably wants something 16:9. Check out bees&bombs examples.
* Make parametisation interactive with a convolver or GA with artificial
  selection.


