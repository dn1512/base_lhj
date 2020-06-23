//
// Component: Main Header
//
 
.main-header {
  border-bottom: $main-header-bottom-border;
  z-index: $zindex-main-header;

  .nav-link {
    height: $nav-link-height;
    position: relative;
  }

  .text-sm &,
  &.text-sm {
    .nav-link {
      height: $nav-link-sm-height;
      padding: $nav-link-sm-padding-y $nav-link-padding-x;

      > .fa,
      > .fas,
      > .far,
      > .fab,
      > .glyphicon,
      > .ion {
        font-size: $font-size-sm;
      }
    }

  }

  .navbar-nav {
    .nav-item {
      margin: 0;
    }

    &[class*='-right'] {
      .dropdown-menu {
        left: auto;
        margin-top: -3px;
        right: 0;

        @media (max-width: breakpoin