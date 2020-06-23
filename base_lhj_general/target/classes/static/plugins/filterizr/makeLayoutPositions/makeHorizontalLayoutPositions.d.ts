import { BaseOptions, RawOptions } from './../types/interfaces';
import ActiveFilter from '../ActiveFilter';
import { Filter } from '../types';
export interface Options extends BaseOptions {
    filter: ActiveFilter;
}
export default class FilterizrOptions {
    private options;
    construct