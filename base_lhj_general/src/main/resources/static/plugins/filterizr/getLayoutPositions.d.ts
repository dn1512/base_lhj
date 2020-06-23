import FilterizrOptions from './FilterizrOptions/FilterizrOptions';
import FilterContainer from './FilterContainer';
import FilterItem from './FilterItem';
import { Filter } from './ActiveFilter';
import { RawOptions } from './FilterizrOptions/defaultOptions';
export default class Filterizr {
    /**
     * Main Filterizr classes exported as static members
     */
    static FilterContainer: typeof FilterContainer;
    static Fil