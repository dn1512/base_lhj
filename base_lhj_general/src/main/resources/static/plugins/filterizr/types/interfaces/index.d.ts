import { FilterizrState } from '../types';
import FilterizrOptions from '../FilterizrOptions';
import FilterItems from '../FilterItems';
import FilterizrElement from '../FilterizrElement';
import StyledFilterContainer from './StyledFilterContainer';
/**
 * Resembles the grid of items within Filterizr.
 */
export default class FilterContainer extends FilterizrElement {
    filterItems: FilterItems;
    protected styledNode: StyledFilterContainer;
    private _filterizrState;
    constructor(node: Element, options: FilterizrOptions);
    re