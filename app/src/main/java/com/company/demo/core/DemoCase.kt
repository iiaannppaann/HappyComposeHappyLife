package com.company.demo.core

enum class DemoCase(val title: String, val description: String) {
    BACKGROUND_XML(
        "Drawable Explosion (XML)", 
        "Traditional XML layout requiring multiple background shape files"
    ),
    BACKGROUND_COMPOSE(
        "Drawable Explosion (Compose)", 
        "Modern Jetpack Compose implementation using inline Modifiers"
    ),
    NESTED_XML(
        "Nested Layout Penalty (XML)", 
        "Deeply nested layouts causing double-measurement performance penalties"
    ),
    NESTED_COMPOSE(
        "Nested Layout Penalty (Compose)", 
        "Flat or deep layouts built in Compose without double-taxation penalty"
    ),
    FORM_XML(
        "Dynamic Form (XML)", 
        "Dynamic form requiring programmatic View generation and manual ID tracking"
    ),
    FORM_COMPOSE(
        "Dynamic Form (Compose)", 
        "Dynamic form using reactive states to map input data cleanly"
    ),
    MULTI_TYPE_XML(
        "Multi-Type List (XML)", 
        "Boilerplate-heavy RecyclerView Adapter with multiple ViewHolders"
    ),
    MULTI_TYPE_COMPOSE(
        "Multi-Type List (Compose)", 
        "Clean, single-file LazyColumn list mapping complex item layouts"
    ),
    STATEFUL_BUTTON_XML(
        "Stateful Button (XML)", 
        "Click to load button toggling FrameLayout visibilities programmatically"
    ),
    STATEFUL_BUTTON_COMPOSE(
        "Stateful Button (Compose)", 
        "Declarative loading button changing its visual composition dynamically"
    ),
    INTEROP_XML_IN_COMPOSE(
        "XML in Compose (Interop)", 
        "Embedding legacy Android Custom Views inside a Compose screen"
    ),
    INTEROP_COMPOSE_IN_XML(
        "Compose in XML (Interop)", 
        "Incrementally adding a ComposeView inside a traditional XML screen"
    ),
    CLASSIC_TABS(
        "Tabs & ViewPager (Compose)", 
        "The classic ViewPager + TabLayout pattern built effortlessly in Compose"
    )
}
