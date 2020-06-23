s.prototype.createPath = function (config) {
  var node;
  if (this.mode === 'svg') {
    node = this.createSvgNode('path');
    node.setAttribute('d', config.path);

    if (this.params.borderColor !== null) {
      node.setAttribute('stroke', this.params.borderColor);
    }
    if (this.params.borderWidth > 0) {
      node.setAttribute('stroke-width', this.params.borderWidth);
      node.setAttribute('stroke-linecap', 'round');
      node.setAttribute('stroke-linejoin', 'round');
    }
    if (this.params.borderOpacity > 0) {
      node.setAttribute('stroke-opacity', this.params.borderOpacity);
    }

    node.setFill = function (color) {
      this.setAttribute('fill', color);
      if (t