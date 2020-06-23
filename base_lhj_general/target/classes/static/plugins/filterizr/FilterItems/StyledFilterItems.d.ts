import { Position } from '../types/interfaces';
import FilterizrOptions from '../FilterizrOptions';
import FilterizrElement from '../FilterizrElement';
import StyledFilterItem from './StyledFilterItem';
/**
 * Resembles an item in the grid of Filterizr.
 */
export default class FilterItem extends FilterizrElement {
    protected styledNode: StyledFilterItem;
    private filteredOut;
    private lastPosition;
    private sortData;
    constructor(node: Element,