import { Position } from './../types/interfaces';
import StyledFilterizrElement from '../StyledFilterizrElement';
import FilterizrOptions from '../FilterizrOptions';
export default class StyledFilterItem extends StyledFilterizrElement {
    private _index;
    constructor(node: HTMLElement, index: number, options: FilterizrOption