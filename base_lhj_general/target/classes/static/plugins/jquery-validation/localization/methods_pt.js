/*!
 * JQVMap: jQuery Vector Map Library
 * @author JQVMap <me@peterschmalfeldt.com>
 * @version 1.5.1
 * @link http://jqvmap.com
 * @license https://github.com/manifestinteractive/jqvmap/blob/master/LICENSE
 * @builddate 2016/06/02
 */

var VectorCanvas=function(a,b,c){if(this.mode=window.SVGAngle?"svg":"vml",this.params=c,"svg"===this.mode)this.createSvgNode=function(a){return document.createElementNS(this.svgns,a)};else{try{document.namespaces.rvml||document.namespaces.add("rvml","urn:schemas-microsoft-com:vml"),this.createVmlNode=function(a){re