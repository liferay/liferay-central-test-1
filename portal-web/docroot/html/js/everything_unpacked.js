(function(){
/*
 * jQuery 1.2.6 - New Wave Javascript
 *
 * Copyright (c) 2008 John Resig (jquery.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2008-05-24 14:22:17 -0400 (Sat, 24 May 2008) $
 * $Rev: 5685 $
 */

// Map over jQuery in case of overwrite
var _jQuery = window.jQuery,
// Map over the $ in case of overwrite
	_$ = window.$;

var jQuery = window.jQuery = window.$ = function( selector, context ) {
	// The jQuery object is actually just the init constructor 'enhanced'
	return new jQuery.fn.init( selector, context );
};

// A simple way to check for HTML strings or ID strings
// (both of which we optimize for)
var quickExpr = /^[^<]*(<(.|\s)+>)[^>]*$|^#(\w+)$/,

// Is it a simple selector
	isSimple = /^.[^:#\[\.]*$/,

// Will speed up references to undefined, and allows munging its name.
	undefined;

jQuery.fn = jQuery.prototype = {
	init: function( selector, context ) {
		// Make sure that a selection was provided
		selector = selector || document;

		// Handle $(DOMElement)
		if ( selector.nodeType ) {
			this[0] = selector;
			this.length = 1;
			return this;
		}
		// Handle HTML strings
		if ( typeof selector == "string" ) {
			// Are we dealing with HTML string or an ID?
			var match = quickExpr.exec( selector );

			// Verify a match, and that no context was specified for #id
			if ( match && (match[1] || !context) ) {

				// HANDLE: $(html) -> $(array)
				if ( match[1] )
					selector = jQuery.clean( [ match[1] ], context );

				// HANDLE: $("#id")
				else {
					var elem = document.getElementById( match[3] );

					// Make sure an element was located
					if ( elem ){
						// Handle the case where IE and Opera return items
						// by name instead of ID
						if ( elem.id != match[3] )
							return jQuery().find( selector );

						// Otherwise, we inject the element directly into the jQuery object
						return jQuery( elem );
					}
					selector = [];
				}

			// HANDLE: $(expr, [context])
			// (which is just equivalent to: $(content).find(expr)
			} else
				return jQuery( context ).find( selector );

		// HANDLE: $(function)
		// Shortcut for document ready
		} else if ( jQuery.isFunction( selector ) )
			return jQuery( document )[ jQuery.fn.ready ? "ready" : "load" ]( selector );

		return this.setArray(jQuery.makeArray(selector));
	},

	// The current version of jQuery being used
	jquery: "1.2.6",

	// The number of elements contained in the matched element set
	size: function() {
		return this.length;
	},

	// The number of elements contained in the matched element set
	length: 0,

	// Get the Nth element in the matched element set OR
	// Get the whole matched element set as a clean array
	get: function( num ) {
		return num == undefined ?

			// Return a 'clean' array
			jQuery.makeArray( this ) :

			// Return just the object
			this[ num ];
	},

	// Take an array of elements and push it onto the stack
	// (returning the new matched element set)
	pushStack: function( elems ) {
		// Build a new jQuery matched element set
		var ret = jQuery( elems );

		// Add the old object onto the stack (as a reference)
		ret.prevObject = this;

		// Return the newly-formed element set
		return ret;
	},

	// Force the current matched set of elements to become
	// the specified array of elements (destroying the stack in the process)
	// You should use pushStack() in order to do this, but maintain the stack
	setArray: function( elems ) {
		// Resetting the length to 0, then using the native Array push
		// is a super-fast way to populate an object with array-like properties
		this.length = 0;
		Array.prototype.push.apply( this, elems );

		return this;
	},

	// Execute a callback for every element in the matched set.
	// (You can seed the arguments with an array of args, but this is
	// only used internally.)
	each: function( callback, args ) {
		return jQuery.each( this, callback, args );
	},

	// Determine the position of an element within
	// the matched set of elements
	index: function( elem ) {
		var ret = -1;

		// Locate the position of the desired element
		return jQuery.inArray(
			// If it receives a jQuery object, the first element is used
			elem && elem.jquery ? elem[0] : elem
		, this );
	},

	attr: function( name, value, type ) {
		var options = name;

		// Look for the case where we're accessing a style value
		if ( name.constructor == String )
			if ( value === undefined )
				return this[0] && jQuery[ type || "attr" ]( this[0], name );

			else {
				options = {};
				options[ name ] = value;
			}

		// Check to see if we're setting style values
		return this.each(function(i){
			// Set all the styles
			for ( name in options )
				jQuery.attr(
					type ?
						this.style :
						this,
					name, jQuery.prop( this, options[ name ], type, i, name )
				);
		});
	},

	css: function( key, value ) {
		// ignore negative width and height values
		if ( (key == 'width' || key == 'height') && parseFloat(value) < 0 )
			value = undefined;
		return this.attr( key, value, "curCSS" );
	},

	text: function( text ) {
		if ( typeof text != "object" && text != null )
			return this.empty().append( (this[0] && this[0].ownerDocument || document).createTextNode( text ) );

		var ret = "";

		jQuery.each( text || this, function(){
			jQuery.each( this.childNodes, function(){
				if ( this.nodeType != 8 )
					ret += this.nodeType != 1 ?
						this.nodeValue :
						jQuery.fn.text( [ this ] );
			});
		});

		return ret;
	},

	wrapAll: function( html ) {
		if ( this[0] )
			// The elements to wrap the target around
			jQuery( html, this[0].ownerDocument )
				.clone()
				.insertBefore( this[0] )
				.map(function(){
					var elem = this;

					while ( elem.firstChild )
						elem = elem.firstChild;

					return elem;
				})
				.append(this);

		return this;
	},

	wrapInner: function( html ) {
		return this.each(function(){
			jQuery( this ).contents().wrapAll( html );
		});
	},

	wrap: function( html ) {
		return this.each(function(){
			jQuery( this ).wrapAll( html );
		});
	},

	append: function() {
		return this.domManip(arguments, true, false, function(elem){
			if (this.nodeType == 1)
				this.appendChild( elem );
		});
	},

	prepend: function() {
		return this.domManip(arguments, true, true, function(elem){
			if (this.nodeType == 1)
				this.insertBefore( elem, this.firstChild );
		});
	},

	before: function() {
		return this.domManip(arguments, false, false, function(elem){
			this.parentNode.insertBefore( elem, this );
		});
	},

	after: function() {
		return this.domManip(arguments, false, true, function(elem){
			this.parentNode.insertBefore( elem, this.nextSibling );
		});
	},

	end: function() {
		return this.prevObject || jQuery( [] );
	},

	find: function( selector ) {
		var elems = jQuery.map(this, function(elem){
			return jQuery.find( selector, elem );
		});

		return this.pushStack( /[^+>] [^+>]/.test( selector ) || selector.indexOf("..") > -1 ?
			jQuery.unique( elems ) :
			elems );
	},

	clone: function( events ) {
		// Do the clone
		var ret = this.map(function(){
			if ( jQuery.browser.msie && !jQuery.isXMLDoc(this) ) {
				// IE copies events bound via attachEvent when
				// using cloneNode. Calling detachEvent on the
				// clone will also remove the events from the orignal
				// In order to get around this, we use innerHTML.
				// Unfortunately, this means some modifications to
				// attributes in IE that are actually only stored
				// as properties will not be copied (such as the
				// the name attribute on an input).
				var clone = this.cloneNode(true),
					container = document.createElement("div");
				container.appendChild(clone);
				return jQuery.clean([container.innerHTML])[0];
			} else
				return this.cloneNode(true);
		});

		// Need to set the expando to null on the cloned set if it exists
		// removeData doesn't work here, IE removes it from the original as well
		// this is primarily for IE but the data expando shouldn't be copied over in any browser
		var clone = ret.find("*").andSelf().each(function(){
			if ( this[ expando ] != undefined )
				this[ expando ] = null;
		});

		// Copy the events from the original to the clone
		if ( events === true )
			this.find("*").andSelf().each(function(i){
				if (this.nodeType == 3)
					return;
				var events = jQuery.data( this, "events" );

				for ( var type in events )
					for ( var handler in events[ type ] )
						jQuery.event.add( clone[ i ], type, events[ type ][ handler ], events[ type ][ handler ].data );
			});

		// Return the cloned set
		return ret;
	},

	filter: function( selector ) {
		return this.pushStack(
			jQuery.isFunction( selector ) &&
			jQuery.grep(this, function(elem, i){
				return selector.call( elem, i );
			}) ||

			jQuery.multiFilter( selector, this ) );
	},

	not: function( selector ) {
		if ( selector.constructor == String )
			// test special case where just one selector is passed in
			if ( isSimple.test( selector ) )
				return this.pushStack( jQuery.multiFilter( selector, this, true ) );
			else
				selector = jQuery.multiFilter( selector, this );

		var isArrayLike = selector.length && selector[selector.length - 1] !== undefined && !selector.nodeType;
		return this.filter(function() {
			return isArrayLike ? jQuery.inArray( this, selector ) < 0 : this != selector;
		});
	},

	add: function( selector ) {
		return this.pushStack( jQuery.unique( jQuery.merge(
			this.get(),
			typeof selector == 'string' ?
				jQuery( selector ) :
				jQuery.makeArray( selector )
		)));
	},

	is: function( selector ) {
		return !!selector && jQuery.multiFilter( selector, this ).length > 0;
	},

	hasClass: function( selector ) {
		return this.is( "." + selector );
	},

	val: function( value ) {
		if ( value == undefined ) {

			if ( this.length ) {
				var elem = this[0];

				// We need to handle select boxes special
				if ( jQuery.nodeName( elem, "select" ) ) {
					var index = elem.selectedIndex,
						values = [],
						options = elem.options,
						one = elem.type == "select-one";

					// Nothing was selected
					if ( index < 0 )
						return null;

					// Loop through all the selected options
					for ( var i = one ? index : 0, max = one ? index + 1 : options.length; i < max; i++ ) {
						var option = options[ i ];

						if ( option.selected ) {
							// Get the specifc value for the option
							value = jQuery.browser.msie && !option.attributes.value.specified ? option.text : option.value;

							// We don't need an array for one selects
							if ( one )
								return value;

							// Multi-Selects return an array
							values.push( value );
						}
					}

					return values;

				// Everything else, we just grab the value
				} else
					return (this[0].value || "").replace(/\r/g, "");

			}

			return undefined;
		}

		if( value.constructor == Number )
			value += '';

		return this.each(function(){
			if ( this.nodeType != 1 )
				return;

			if ( value.constructor == Array && /radio|checkbox/.test( this.type ) )
				this.checked = (jQuery.inArray(this.value, value) >= 0 ||
					jQuery.inArray(this.name, value) >= 0);

			else if ( jQuery.nodeName( this, "select" ) ) {
				var values = jQuery.makeArray(value);

				jQuery( "option", this ).each(function(){
					this.selected = (jQuery.inArray( this.value, values ) >= 0 ||
						jQuery.inArray( this.text, values ) >= 0);
				});

				if ( !values.length )
					this.selectedIndex = -1;

			} else
				this.value = value;
		});
	},

	html: function( value ) {
		return value == undefined ?
			(this[0] ?
				this[0].innerHTML :
				null) :
			this.empty().append( value );
	},

	replaceWith: function( value ) {
		return this.after( value ).remove();
	},

	eq: function( i ) {
		return this.slice( i, i + 1 );
	},

	slice: function() {
		return this.pushStack( Array.prototype.slice.apply( this, arguments ) );
	},

	map: function( callback ) {
		return this.pushStack( jQuery.map(this, function(elem, i){
			return callback.call( elem, i, elem );
		}));
	},

	andSelf: function() {
		return this.add( this.prevObject );
	},

	data: function( key, value ){
		var parts = key.split(".");
		parts[1] = parts[1] ? "." + parts[1] : "";

		if ( value === undefined ) {
			var data = this.triggerHandler("getData" + parts[1] + "!", [parts[0]]);

			if ( data === undefined && this.length )
				data = jQuery.data( this[0], key );

			return data === undefined && parts[1] ?
				this.data( parts[0] ) :
				data;
		} else
			return this.trigger("setData" + parts[1] + "!", [parts[0], value]).each(function(){
				jQuery.data( this, key, value );
			});
	},

	removeData: function( key ){
		return this.each(function(){
			jQuery.removeData( this, key );
		});
	},

	domManip: function( args, table, reverse, callback ) {
		var clone = this.length > 1, elems;

		return this.each(function(){
			if ( !elems ) {
				elems = jQuery.clean( args, this.ownerDocument );

				if ( reverse )
					elems.reverse();
			}

			var obj = this;

			if ( table && jQuery.nodeName( this, "table" ) && jQuery.nodeName( elems[0], "tr" ) )
				obj = this.getElementsByTagName("tbody")[0] || this.appendChild( this.ownerDocument.createElement("tbody") );

			var scripts = jQuery( [] );

			jQuery.each(elems, function(){
				var elem = clone ?
					jQuery( this ).clone( true )[0] :
					this;

				// execute all scripts after the elements have been injected
				if ( jQuery.nodeName( elem, "script" ) )
					scripts = scripts.add( elem );
				else {
					// Remove any inner scripts for later evaluation
					if ( elem.nodeType == 1 )
						scripts = scripts.add( jQuery( "script", elem ).remove() );

					// Inject the elements into the document
					callback.call( obj, elem );
				}
			});

			scripts.each( evalScript );
		});
	}
};

// Give the init function the jQuery prototype for later instantiation
jQuery.fn.init.prototype = jQuery.fn;

function evalScript( i, elem ) {
	if ( elem.src )
		jQuery.ajax({
			url: elem.src,
			async: false,
			dataType: "script"
		});

	else
		jQuery.globalEval( elem.text || elem.textContent || elem.innerHTML || "" );

	if ( elem.parentNode )
		elem.parentNode.removeChild( elem );
}

function now(){
	return +new Date;
}

jQuery.extend = jQuery.fn.extend = function() {
	// copy reference to target object
	var target = arguments[0] || {}, i = 1, length = arguments.length, deep = false, options;

	// Handle a deep copy situation
	if ( target.constructor == Boolean ) {
		deep = target;
		target = arguments[1] || {};
		// skip the boolean and the target
		i = 2;
	}

	// Handle case when target is a string or something (possible in deep copy)
	if ( typeof target != "object" && typeof target != "function" )
		target = {};

	// extend jQuery itself if only one argument is passed
	if ( length == i ) {
		target = this;
		--i;
	}

	for ( ; i < length; i++ )
		// Only deal with non-null/undefined values
		if ( (options = arguments[ i ]) != null )
			// Extend the base object
			for ( var name in options ) {
				var src = target[ name ], copy = options[ name ];

				// Prevent never-ending loop
				if ( target === copy )
					continue;

				// Recurse if we're merging object values
				if ( deep && copy && typeof copy == "object" && !copy.nodeType )
					target[ name ] = jQuery.extend( deep, 
						// Never move original objects, clone them
						src || ( copy.length != null ? [ ] : { } )
					, copy );

				// Don't bring in undefined values
				else if ( copy !== undefined )
					target[ name ] = copy;

			}

	// Return the modified object
	return target;
};

var expando = "jQuery" + now(), uuid = 0, windowData = {},
	// exclude the following css properties to add px
	exclude = /z-?index|font-?weight|opacity|zoom|line-?height/i,
	// cache defaultView
	defaultView = document.defaultView || {};

jQuery.extend({
	noConflict: function( deep ) {
		window.$ = _$;

		if ( deep )
			window.jQuery = _jQuery;

		return jQuery;
	},

	// See test/unit/core.js for details concerning this function.
	isFunction: function( fn ) {
		return !!fn && typeof fn != "string" && !fn.nodeName &&
			fn.constructor != Array && /^[\s[]?function/.test( fn + "" );
	},

	// check if an element is in a (or is an) XML document
	isXMLDoc: function( elem ) {
		return elem.documentElement && !elem.body ||
			elem.tagName && elem.ownerDocument && !elem.ownerDocument.body;
	},

	// Evalulates a script in a global context
	globalEval: function( data ) {
		data = jQuery.trim( data );

		if ( data ) {
			// Inspired by code by Andrea Giammarchi
			// http://webreflection.blogspot.com/2007/08/global-scope-evaluation-and-dom.html
			var head = document.getElementsByTagName("head")[0] || document.documentElement,
				script = document.createElement("script");

			script.type = "text/javascript";
			if ( jQuery.browser.msie )
				script.text = data;
			else
				script.appendChild( document.createTextNode( data ) );

			// Use insertBefore instead of appendChild  to circumvent an IE6 bug.
			// This arises when a base node is used (#2709).
			head.insertBefore( script, head.firstChild );
			head.removeChild( script );
		}
	},

	nodeName: function( elem, name ) {
		return elem.nodeName && elem.nodeName.toUpperCase() == name.toUpperCase();
	},

	cache: {},

	data: function( elem, name, data ) {
		elem = elem == window ?
			windowData :
			elem;

		var id = elem[ expando ];

		// Compute a unique ID for the element
		if ( !id )
			id = elem[ expando ] = ++uuid;

		// Only generate the data cache if we're
		// trying to access or manipulate it
		if ( name && !jQuery.cache[ id ] )
			jQuery.cache[ id ] = {};

		// Prevent overriding the named cache with undefined values
		if ( data !== undefined )
			jQuery.cache[ id ][ name ] = data;

		// Return the named cache data, or the ID for the element
		return name ?
			jQuery.cache[ id ][ name ] :
			id;
	},

	removeData: function( elem, name ) {
		elem = elem == window ?
			windowData :
			elem;

		var id = elem[ expando ];

		// If we want to remove a specific section of the element's data
		if ( name ) {
			if ( jQuery.cache[ id ] ) {
				// Remove the section of cache data
				delete jQuery.cache[ id ][ name ];

				// If we've removed all the data, remove the element's cache
				name = "";

				for ( name in jQuery.cache[ id ] )
					break;

				if ( !name )
					jQuery.removeData( elem );
			}

		// Otherwise, we want to remove all of the element's data
		} else {
			// Clean up the element expando
			try {
				delete elem[ expando ];
			} catch(e){
				// IE has trouble directly removing the expando
				// but it's ok with using removeAttribute
				if ( elem.removeAttribute )
					elem.removeAttribute( expando );
			}

			// Completely remove the data cache
			delete jQuery.cache[ id ];
		}
	},

	// args is for internal usage only
	each: function( object, callback, args ) {
		var name, i = 0, length = object.length;

		if ( args ) {
			if ( length == undefined ) {
				for ( name in object )
					if ( callback.apply( object[ name ], args ) === false )
						break;
			} else
				for ( ; i < length; )
					if ( callback.apply( object[ i++ ], args ) === false )
						break;

		// A special, fast, case for the most common use of each
		} else {
			if ( length == undefined ) {
				for ( name in object )
					if ( callback.call( object[ name ], name, object[ name ] ) === false )
						break;
			} else
				for ( var value = object[0];
					i < length && callback.call( value, i, value ) !== false; value = object[++i] ){}
		}

		return object;
	},

	prop: function( elem, value, type, i, name ) {
		// Handle executable functions
		if ( jQuery.isFunction( value ) )
			value = value.call( elem, i );

		// Handle passing in a number to a CSS property
		return value && value.constructor == Number && type == "curCSS" && !exclude.test( name ) ?
			value + "px" :
			value;
	},

	className: {
		// internal only, use addClass("class")
		add: function( elem, classNames ) {
			jQuery.each((classNames || "").split(/\s+/), function(i, className){
				if ( elem.nodeType == 1 && !jQuery.className.has( elem.className, className ) )
					elem.className += (elem.className ? " " : "") + className;
			});
		},

		// internal only, use removeClass("class")
		remove: function( elem, classNames ) {
			if (elem.nodeType == 1)
				elem.className = classNames != undefined ?
					jQuery.grep(elem.className.split(/\s+/), function(className){
						return !jQuery.className.has( classNames, className );
					}).join(" ") :
					"";
		},

		// internal only, use hasClass("class")
		has: function( elem, className ) {
			return jQuery.inArray( className, (elem.className || elem).toString().split(/\s+/) ) > -1;
		}
	},

	// A method for quickly swapping in/out CSS properties to get correct calculations
	swap: function( elem, options, callback ) {
		var old = {};
		// Remember the old values, and insert the new ones
		for ( var name in options ) {
			old[ name ] = elem.style[ name ];
			elem.style[ name ] = options[ name ];
		}

		callback.call( elem );

		// Revert the old values
		for ( var name in options )
			elem.style[ name ] = old[ name ];
	},

	css: function( elem, name, force ) {
		if ( name == "width" || name == "height" ) {
			var val, props = { position: "absolute", visibility: "hidden", display:"block" }, which = name == "width" ? [ "Left", "Right" ] : [ "Top", "Bottom" ];

			function getWH() {
				val = name == "width" ? elem.offsetWidth : elem.offsetHeight;
				var padding = 0, border = 0;
				jQuery.each( which, function() {
					padding += parseFloat(jQuery.curCSS( elem, "padding" + this, true)) || 0;
					border += parseFloat(jQuery.curCSS( elem, "border" + this + "Width", true)) || 0;
				});
				val -= Math.round(padding + border);
			}

			if ( jQuery(elem).is(":visible") )
				getWH();
			else
				jQuery.swap( elem, props, getWH );

			return Math.max(0, val);
		}

		return jQuery.curCSS( elem, name, force );
	},

	curCSS: function( elem, name, force ) {
		var ret, style = elem.style;

		// A helper method for determining if an element's values are broken
		function color( elem ) {
			if ( !jQuery.browser.safari )
				return false;

			// defaultView is cached
			var ret = defaultView.getComputedStyle( elem, null );
			return !ret || ret.getPropertyValue("color") == "";
		}

		// We need to handle opacity special in IE
		if ( name == "opacity" && jQuery.browser.msie ) {
			ret = jQuery.attr( style, "opacity" );

			return ret == "" ?
				"1" :
				ret;
		}
		// Opera sometimes will give the wrong display answer, this fixes it, see #2037
		if ( jQuery.browser.opera && name == "display" ) {
			var save = style.outline;
			style.outline = "0 solid black";
			style.outline = save;
		}

		// Make sure we're using the right name for getting the float value
		if ( name.match( /float/i ) )
			name = styleFloat;

		if ( !force && style && style[ name ] )
			ret = style[ name ];

		else if ( defaultView.getComputedStyle ) {

			// Only "float" is needed here
			if ( name.match( /float/i ) )
				name = "float";

			name = name.replace( /([A-Z])/g, "-$1" ).toLowerCase();

			var computedStyle = defaultView.getComputedStyle( elem, null );

			if ( computedStyle && !color( elem ) )
				ret = computedStyle.getPropertyValue( name );

			// If the element isn't reporting its values properly in Safari
			// then some display: none elements are involved
			else {
				var swap = [], stack = [], a = elem, i = 0;

				// Locate all of the parent display: none elements
				for ( ; a && color(a); a = a.parentNode )
					stack.unshift(a);

				// Go through and make them visible, but in reverse
				// (It would be better if we knew the exact display type that they had)
				for ( ; i < stack.length; i++ )
					if ( color( stack[ i ] ) ) {
						swap[ i ] = stack[ i ].style.display;
						stack[ i ].style.display = "block";
					}

				// Since we flip the display style, we have to handle that
				// one special, otherwise get the value
				ret = name == "display" && swap[ stack.length - 1 ] != null ?
					"none" :
					( computedStyle && computedStyle.getPropertyValue( name ) ) || "";

				// Finally, revert the display styles back
				for ( i = 0; i < swap.length; i++ )
					if ( swap[ i ] != null )
						stack[ i ].style.display = swap[ i ];
			}

			// We should always get a number back from opacity
			if ( name == "opacity" && ret == "" )
				ret = "1";

		} else if ( elem.currentStyle ) {
			var camelCase = name.replace(/\-(\w)/g, function(all, letter){
				return letter.toUpperCase();
			});

			ret = elem.currentStyle[ name ] || elem.currentStyle[ camelCase ];

			// From the awesome hack by Dean Edwards
			// http://erik.eae.net/archives/2007/07/27/18.54.15/#comment-102291

			// If we're not dealing with a regular pixel number
			// but a number that has a weird ending, we need to convert it to pixels
			if ( !/^\d+(px)?$/i.test( ret ) && /^\d/.test( ret ) ) {
				// Remember the original values
				var left = style.left, rsLeft = elem.runtimeStyle.left;

				// Put in the new values to get a computed value out
				elem.runtimeStyle.left = elem.currentStyle.left;
				style.left = ret || 0;
				ret = style.pixelLeft + "px";

				// Revert the changed values
				style.left = left;
				elem.runtimeStyle.left = rsLeft;
			}
		}

		return ret;
	},

	clean: function( elems, context ) {
		var ret = [];
		context = context || document;
		// !context.createElement fails in IE with an error but returns typeof 'object'
		if (typeof context.createElement == 'undefined')
			context = context.ownerDocument || context[0] && context[0].ownerDocument || document;

		jQuery.each(elems, function(i, elem){
			if ( !elem )
				return;

			if ( elem.constructor == Number )
				elem += '';

			// Convert html string into DOM nodes
			if ( typeof elem == "string" ) {
				// Fix "XHTML"-style tags in all browsers
				elem = elem.replace(/(<(\w+)[^>]*?)\/>/g, function(all, front, tag){
					return tag.match(/^(abbr|br|col|img|input|link|meta|param|hr|area|embed)$/i) ?
						all :
						front + "></" + tag + ">";
				});

				// Trim whitespace, otherwise indexOf won't work as expected
				var tags = jQuery.trim( elem ).toLowerCase(), div = context.createElement("div");

				var wrap =
					// option or optgroup
					!tags.indexOf("<opt") &&
					[ 1, "<select multiple='multiple'>", "</select>" ] ||

					!tags.indexOf("<leg") &&
					[ 1, "<fieldset>", "</fieldset>" ] ||

					tags.match(/^<(thead|tbody|tfoot|colg|cap)/) &&
					[ 1, "<table>", "</table>" ] ||

					!tags.indexOf("<tr") &&
					[ 2, "<table><tbody>", "</tbody></table>" ] ||

				 	// <thead> matched above
					(!tags.indexOf("<td") || !tags.indexOf("<th")) &&
					[ 3, "<table><tbody><tr>", "</tr></tbody></table>" ] ||

					!tags.indexOf("<col") &&
					[ 2, "<table><tbody></tbody><colgroup>", "</colgroup></table>" ] ||

					// IE can't serialize <link> and <script> tags normally
					jQuery.browser.msie &&
					[ 1, "div<div>", "</div>" ] ||

					[ 0, "", "" ];

				// Go to html and back, then peel off extra wrappers
				div.innerHTML = wrap[1] + elem + wrap[2];

				// Move to the right depth
				while ( wrap[0]-- )
					div = div.lastChild;

				// Remove IE's autoinserted <tbody> from table fragments
				if ( jQuery.browser.msie ) {

					// String was a <table>, *may* have spurious <tbody>
					var tbody = !tags.indexOf("<table") && tags.indexOf("<tbody") < 0 ?
						div.firstChild && div.firstChild.childNodes :

						// String was a bare <thead> or <tfoot>
						wrap[1] == "<table>" && tags.indexOf("<tbody") < 0 ?
							div.childNodes :
							[];

					for ( var j = tbody.length - 1; j >= 0 ; --j )
						if ( jQuery.nodeName( tbody[ j ], "tbody" ) && !tbody[ j ].childNodes.length )
							tbody[ j ].parentNode.removeChild( tbody[ j ] );

					// IE completely kills leading whitespace when innerHTML is used
					if ( /^\s/.test( elem ) )
						div.insertBefore( context.createTextNode( elem.match(/^\s*/)[0] ), div.firstChild );

				}

				elem = jQuery.makeArray( div.childNodes );
			}

			if ( elem.length === 0 && (!jQuery.nodeName( elem, "form" ) && !jQuery.nodeName( elem, "select" )) )
				return;

			if ( elem[0] == undefined || jQuery.nodeName( elem, "form" ) || elem.options )
				ret.push( elem );

			else
				ret = jQuery.merge( ret, elem );

		});

		return ret;
	},

	attr: function( elem, name, value ) {
		// don't set attributes on text and comment nodes
		if (!elem || elem.nodeType == 3 || elem.nodeType == 8)
			return undefined;

		var notxml = !jQuery.isXMLDoc( elem ),
			// Whether we are setting (or getting)
			set = value !== undefined,
			msie = jQuery.browser.msie;

		// Try to normalize/fix the name
		name = notxml && jQuery.props[ name ] || name;

		// Only do all the following if this is a node (faster for style)
		// IE elem.getAttribute passes even for style
		if ( elem.tagName ) {

			// These attributes require special treatment
			var special = /href|src|style/.test( name );

			// Safari mis-reports the default selected property of a hidden option
			// Accessing the parent's selectedIndex property fixes it
			if ( name == "selected" && jQuery.browser.safari )
				elem.parentNode.selectedIndex;

			// If applicable, access the attribute via the DOM 0 way
			if ( name in elem && notxml && !special ) {
				if ( set ){
					// We can't allow the type property to be changed (since it causes problems in IE)
					if ( name == "type" && jQuery.nodeName( elem, "input" ) && elem.parentNode )
						throw "type property can't be changed";

					elem[ name ] = value;
				}

				// browsers index elements by id/name on forms, give priority to attributes.
				if( jQuery.nodeName( elem, "form" ) && elem.getAttributeNode(name) )
					return elem.getAttributeNode( name ).nodeValue;

				return elem[ name ];
			}

			if ( msie && notxml &&  name == "style" )
				return jQuery.attr( elem.style, "cssText", value );

			if ( set )
				// convert the value to a string (all browsers do this but IE) see #1070
				elem.setAttribute( name, "" + value );

			var attr = msie && notxml && special
					// Some attributes require a special call on IE
					? elem.getAttribute( name, 2 )
					: elem.getAttribute( name );

			// Non-existent attributes return null, we normalize to undefined
			return attr === null ? undefined : attr;
		}

		// elem is actually elem.style ... set the style

		// IE uses filters for opacity
		if ( msie && name == "opacity" ) {
			if ( set ) {
				// IE has trouble with opacity if it does not have layout
				// Force it by setting the zoom level
				elem.zoom = 1;

				// Set the alpha filter to set the opacity
				elem.filter = (elem.filter || "").replace( /alpha\([^)]*\)/, "" ) +
					(parseInt( value ) + '' == "NaN" ? "" : "alpha(opacity=" + value * 100 + ")");
			}

			return elem.filter && elem.filter.indexOf("opacity=") >= 0 ?
				(parseFloat( elem.filter.match(/opacity=([^)]*)/)[1] ) / 100) + '':
				"";
		}

		name = name.replace(/-([a-z])/ig, function(all, letter){
			return letter.toUpperCase();
		});

		if ( set )
			elem[ name ] = value;

		return elem[ name ];
	},

	trim: function( text ) {
		return (text || "").replace( /^\s+|\s+$/g, "" );
	},

	makeArray: function( array ) {
		var ret = [];

		if( array != null ){
			var i = array.length;
			//the window, strings and functions also have 'length'
			if( i == null || array.split || array.setInterval || array.call )
				ret[0] = array;
			else
				while( i )
					ret[--i] = array[i];
		}

		return ret;
	},

	inArray: function( elem, array ) {
		for ( var i = 0, length = array.length; i < length; i++ )
		// Use === because on IE, window == document
			if ( array[ i ] === elem )
				return i;

		return -1;
	},

	merge: function( first, second ) {
		// We have to loop this way because IE & Opera overwrite the length
		// expando of getElementsByTagName
		var i = 0, elem, pos = first.length;
		// Also, we need to make sure that the correct elements are being returned
		// (IE returns comment nodes in a '*' query)
		if ( jQuery.browser.msie ) {
			while ( elem = second[ i++ ] )
				if ( elem.nodeType != 8 )
					first[ pos++ ] = elem;

		} else
			while ( elem = second[ i++ ] )
				first[ pos++ ] = elem;

		return first;
	},

	unique: function( array ) {
		var ret = [], done = {};

		try {

			for ( var i = 0, length = array.length; i < length; i++ ) {
				var id = jQuery.data( array[ i ] );

				if ( !done[ id ] ) {
					done[ id ] = true;
					ret.push( array[ i ] );
				}
			}

		} catch( e ) {
			ret = array;
		}

		return ret;
	},

	grep: function( elems, callback, inv ) {
		var ret = [];

		// Go through the array, only saving the items
		// that pass the validator function
		for ( var i = 0, length = elems.length; i < length; i++ )
			if ( !inv != !callback( elems[ i ], i ) )
				ret.push( elems[ i ] );

		return ret;
	},

	map: function( elems, callback ) {
		var ret = [];

		// Go through the array, translating each of the items to their
		// new value (or values).
		for ( var i = 0, length = elems.length; i < length; i++ ) {
			var value = callback( elems[ i ], i );

			if ( value != null )
				ret[ ret.length ] = value;
		}

		return ret.concat.apply( [], ret );
	}
});

var userAgent = navigator.userAgent.toLowerCase();

// Figure out what browser is being used
jQuery.browser = {
	version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
	safari: /webkit/.test( userAgent ),
	opera: /opera/.test( userAgent ),
	msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
	mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )
};

var styleFloat = jQuery.browser.msie ?
	"styleFloat" :
	"cssFloat";

jQuery.extend({
	// Check to see if the W3C box model is being used
	boxModel: !jQuery.browser.msie || document.compatMode == "CSS1Compat",

	props: {
		"for": "htmlFor",
		"class": "className",
		"float": styleFloat,
		cssFloat: styleFloat,
		styleFloat: styleFloat,
		readonly: "readOnly",
		maxlength: "maxLength",
		cellspacing: "cellSpacing"
	}
});

jQuery.each({
	parent: function(elem){return elem.parentNode;},
	parents: function(elem){return jQuery.dir(elem,"parentNode");},
	next: function(elem){return jQuery.nth(elem,2,"nextSibling");},
	prev: function(elem){return jQuery.nth(elem,2,"previousSibling");},
	nextAll: function(elem){return jQuery.dir(elem,"nextSibling");},
	prevAll: function(elem){return jQuery.dir(elem,"previousSibling");},
	siblings: function(elem){return jQuery.sibling(elem.parentNode.firstChild,elem);},
	children: function(elem){return jQuery.sibling(elem.firstChild);},
	contents: function(elem){return jQuery.nodeName(elem,"iframe")?elem.contentDocument||elem.contentWindow.document:jQuery.makeArray(elem.childNodes);}
}, function(name, fn){
	jQuery.fn[ name ] = function( selector ) {
		var ret = jQuery.map( this, fn );

		if ( selector && typeof selector == "string" )
			ret = jQuery.multiFilter( selector, ret );

		return this.pushStack( jQuery.unique( ret ) );
	};
});

jQuery.each({
	appendTo: "append",
	prependTo: "prepend",
	insertBefore: "before",
	insertAfter: "after",
	replaceAll: "replaceWith"
}, function(name, original){
	jQuery.fn[ name ] = function() {
		var args = arguments;

		return this.each(function(){
			for ( var i = 0, length = args.length; i < length; i++ )
				jQuery( args[ i ] )[ original ]( this );
		});
	};
});

jQuery.each({
	removeAttr: function( name ) {
		jQuery.attr( this, name, "" );
		if (this.nodeType == 1)
			this.removeAttribute( name );
	},

	addClass: function( classNames ) {
		jQuery.className.add( this, classNames );
	},

	removeClass: function( classNames ) {
		jQuery.className.remove( this, classNames );
	},

	toggleClass: function( classNames ) {
		jQuery.className[ jQuery.className.has( this, classNames ) ? "remove" : "add" ]( this, classNames );
	},

	remove: function( selector ) {
		if ( !selector || jQuery.filter( selector, [ this ] ).r.length ) {
			// Prevent memory leaks
			jQuery( "*", this ).add(this).each(function(){
				jQuery.event.remove(this);
				jQuery.removeData(this);
			});
			if (this.parentNode)
				this.parentNode.removeChild( this );
		}
	},

	empty: function() {
		// Remove element nodes and prevent memory leaks
		jQuery( ">*", this ).remove();

		// Remove any remaining nodes
		while ( this.firstChild )
			this.removeChild( this.firstChild );
	}
}, function(name, fn){
	jQuery.fn[ name ] = function(){
		return this.each( fn, arguments );
	};
});

jQuery.each([ "Height", "Width" ], function(i, name){
	var type = name.toLowerCase();

	jQuery.fn[ type ] = function( size ) {
		// Get window width or height
		return this[0] == window ?
			// Opera reports document.body.client[Width/Height] properly in both quirks and standards
			jQuery.browser.opera && document.body[ "client" + name ] ||

			// Safari reports inner[Width/Height] just fine (Mozilla and Opera include scroll bar widths)
			jQuery.browser.safari && window[ "inner" + name ] ||

			// Everyone else use document.documentElement or document.body depending on Quirks vs Standards mode
			document.compatMode == "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ] :

			// Get document width or height
			this[0] == document ?
				// Either scroll[Width/Height] or offset[Width/Height], whichever is greater
				Math.max(
					Math.max(document.body["scroll" + name], document.documentElement["scroll" + name]),
					Math.max(document.body["offset" + name], document.documentElement["offset" + name])
				) :

				// Get or set width or height on the element
				size == undefined ?
					// Get width or height on the element
					(this.length ? jQuery.css( this[0], type ) : null) :

					// Set the width or height on the element (default to pixels if value is unitless)
					this.css( type, size.constructor == String ? size : size + "px" );
	};
});

// Helper function used by the dimensions and offset modules
function num(elem, prop) {
	return elem[0] && parseInt( jQuery.curCSS(elem[0], prop, true), 10 ) || 0;
}var chars = jQuery.browser.safari && parseInt(jQuery.browser.version) < 417 ?
		"(?:[\\w*_-]|\\\\.)" :
		"(?:[\\w\u0128-\uFFFF*_-]|\\\\.)",
	quickChild = new RegExp("^>\\s*(" + chars + "+)"),
	quickID = new RegExp("^(" + chars + "+)(#)(" + chars + "+)"),
	quickClass = new RegExp("^([#.]?)(" + chars + "*)");

jQuery.extend({
	expr: {
		"": function(a,i,m){return m[2]=="*"||jQuery.nodeName(a,m[2]);},
		"#": function(a,i,m){return a.getAttribute("id")==m[2];},
		":": {
			// Position Checks
			lt: function(a,i,m){return i<m[3]-0;},
			gt: function(a,i,m){return i>m[3]-0;},
			nth: function(a,i,m){return m[3]-0==i;},
			eq: function(a,i,m){return m[3]-0==i;},
			first: function(a,i){return i==0;},
			last: function(a,i,m,r){return i==r.length-1;},
			even: function(a,i){return i%2==0;},
			odd: function(a,i){return i%2;},

			// Child Checks
			"first-child": function(a){return a.parentNode.getElementsByTagName("*")[0]==a;},
			"last-child": function(a){return jQuery.nth(a.parentNode.lastChild,1,"previousSibling")==a;},
			"only-child": function(a){return !jQuery.nth(a.parentNode.lastChild,2,"previousSibling");},

			// Parent Checks
			parent: function(a){return a.firstChild;},
			empty: function(a){return !a.firstChild;},

			// Text Check
			contains: function(a,i,m){return (a.textContent||a.innerText||jQuery(a).text()||"").indexOf(m[3])>=0;},

			// Visibility
			visible: function(a){return "hidden"!=a.type&&jQuery.css(a,"display")!="none"&&jQuery.css(a,"visibility")!="hidden";},
			hidden: function(a){return "hidden"==a.type||jQuery.css(a,"display")=="none"||jQuery.css(a,"visibility")=="hidden";},

			// Form attributes
			enabled: function(a){return !a.disabled;},
			disabled: function(a){return a.disabled;},
			checked: function(a){return a.checked;},
			selected: function(a){return a.selected||jQuery.attr(a,"selected");},

			// Form elements
			text: function(a){return "text"==a.type;},
			radio: function(a){return "radio"==a.type;},
			checkbox: function(a){return "checkbox"==a.type;},
			file: function(a){return "file"==a.type;},
			password: function(a){return "password"==a.type;},
			submit: function(a){return "submit"==a.type;},
			image: function(a){return "image"==a.type;},
			reset: function(a){return "reset"==a.type;},
			button: function(a){return "button"==a.type||jQuery.nodeName(a,"button");},
			input: function(a){return /input|select|textarea|button/i.test(a.nodeName);},

			// :has()
			has: function(a,i,m){return jQuery.find(m[3],a).length;},

			// :header
			header: function(a){return /h\d/i.test(a.nodeName);},

			// :animated
			animated: function(a){return jQuery.grep(jQuery.timers,function(fn){return a==fn.elem;}).length;}
		}
	},

	// The regular expressions that power the parsing engine
	parse: [
		// Match: [@value='test'], [@foo]
		/^(\[) *@?([\w-]+) *([!*$^~=]*) *('?"?)(.*?)\4 *\]/,

		// Match: :contains('foo')
		/^(:)([\w-]+)\("?'?(.*?(\(.*?\))?[^(]*?)"?'?\)/,

		// Match: :even, :last-child, #id, .class
		new RegExp("^([:.#]*)(" + chars + "+)")
	],

	multiFilter: function( expr, elems, not ) {
		var old, cur = [];

		while ( expr && expr != old ) {
			old = expr;
			var f = jQuery.filter( expr, elems, not );
			expr = f.t.replace(/^\s*,\s*/, "" );
			cur = not ? elems = f.r : jQuery.merge( cur, f.r );
		}

		return cur;
	},

	find: function( t, context ) {
		// Quickly handle non-string expressions
		if ( typeof t != "string" )
			return [ t ];

		// check to make sure context is a DOM element or a document
		if ( context && context.nodeType != 1 && context.nodeType != 9)
			return [ ];

		// Set the correct context (if none is provided)
		context = context || document;

		// Initialize the search
		var ret = [context], done = [], last, nodeName;

		// Continue while a selector expression exists, and while
		// we're no longer looping upon ourselves
		while ( t && last != t ) {
			var r = [];
			last = t;

			t = jQuery.trim(t);

			var foundToken = false,

			// An attempt at speeding up child selectors that
			// point to a specific element tag
				re = quickChild,

				m = re.exec(t);

			if ( m ) {
				nodeName = m[1].toUpperCase();

				// Perform our own iteration and filter
				for ( var i = 0; ret[i]; i++ )
					for ( var c = ret[i].firstChild; c; c = c.nextSibling )
						if ( c.nodeType == 1 && (nodeName == "*" || c.nodeName.toUpperCase() == nodeName) )
							r.push( c );

				ret = r;
				t = t.replace( re, "" );
				if ( t.indexOf(" ") == 0 ) continue;
				foundToken = true;
			} else {
				re = /^([>+~])\s*(\w*)/i;

				if ( (m = re.exec(t)) != null ) {
					r = [];

					var merge = {};
					nodeName = m[2].toUpperCase();
					m = m[1];

					for ( var j = 0, rl = ret.length; j < rl; j++ ) {
						var n = m == "~" || m == "+" ? ret[j].nextSibling : ret[j].firstChild;
						for ( ; n; n = n.nextSibling )
							if ( n.nodeType == 1 ) {
								var id = jQuery.data(n);

								if ( m == "~" && merge[id] ) break;

								if (!nodeName || n.nodeName.toUpperCase() == nodeName ) {
									if ( m == "~" ) merge[id] = true;
									r.push( n );
								}

								if ( m == "+" ) break;
							}
					}

					ret = r;

					// And remove the token
					t = jQuery.trim( t.replace( re, "" ) );
					foundToken = true;
				}
			}

			// See if there's still an expression, and that we haven't already
			// matched a token
			if ( t && !foundToken ) {
				// Handle multiple expressions
				if ( !t.indexOf(",") ) {
					// Clean the result set
					if ( context == ret[0] ) ret.shift();

					// Merge the result sets
					done = jQuery.merge( done, ret );

					// Reset the context
					r = ret = [context];

					// Touch up the selector string
					t = " " + t.substr(1,t.length);

				} else {
					// Optimize for the case nodeName#idName
					var re2 = quickID;
					var m = re2.exec(t);

					// Re-organize the results, so that they're consistent
					if ( m ) {
						m = [ 0, m[2], m[3], m[1] ];

					} else {
						// Otherwise, do a traditional filter check for
						// ID, class, and element selectors
						re2 = quickClass;
						m = re2.exec(t);
					}

					m[2] = m[2].replace(/\\/g, "");

					var elem = ret[ret.length-1];

					// Try to do a global search by ID, where we can
					if ( m[1] == "#" && elem && elem.getElementById && !jQuery.isXMLDoc(elem) ) {
						// Optimization for HTML document case
						var oid = elem.getElementById(m[2]);

						// Do a quick check for the existence of the actual ID attribute
						// to avoid selecting by the name attribute in IE
						// also check to insure id is a string to avoid selecting an element with the name of 'id' inside a form
						if ( (jQuery.browser.msie||jQuery.browser.opera) && oid && typeof oid.id == "string" && oid.id != m[2] )
							oid = jQuery('[@id="'+m[2]+'"]', elem)[0];

						// Do a quick check for node name (where applicable) so
						// that div#foo searches will be really fast
						ret = r = oid && (!m[3] || jQuery.nodeName(oid, m[3])) ? [oid] : [];
					} else {
						// We need to find all descendant elements
						for ( var i = 0; ret[i]; i++ ) {
							// Grab the tag name being searched for
							var tag = m[1] == "#" && m[3] ? m[3] : m[1] != "" || m[0] == "" ? "*" : m[2];

							// Handle IE7 being really dumb about <object>s
							if ( tag == "*" && ret[i].nodeName.toLowerCase() == "object" )
								tag = "param";

							r = jQuery.merge( r, ret[i].getElementsByTagName( tag ));
						}

						// It's faster to filter by class and be done with it
						if ( m[1] == "." )
							r = jQuery.classFilter( r, m[2] );

						// Same with ID filtering
						if ( m[1] == "#" ) {
							var tmp = [];

							// Try to find the element with the ID
							for ( var i = 0; r[i]; i++ )
								if ( r[i].getAttribute("id") == m[2] ) {
									tmp = [ r[i] ];
									break;
								}

							r = tmp;
						}

						ret = r;
					}

					t = t.replace( re2, "" );
				}

			}

			// If a selector string still exists
			if ( t ) {
				// Attempt to filter it
				var val = jQuery.filter(t,r);
				ret = r = val.r;
				t = jQuery.trim(val.t);
			}
		}

		// An error occurred with the selector;
		// just return an empty set instead
		if ( t )
			ret = [];

		// Remove the root context
		if ( ret && context == ret[0] )
			ret.shift();

		// And combine the results
		done = jQuery.merge( done, ret );

		return done;
	},

	classFilter: function(r,m,not){
		m = " " + m + " ";
		var tmp = [];
		for ( var i = 0; r[i]; i++ ) {
			var pass = (" " + r[i].className + " ").indexOf( m ) >= 0;
			if ( !not && pass || not && !pass )
				tmp.push( r[i] );
		}
		return tmp;
	},

	filter: function(t,r,not) {
		var last;

		// Look for common filter expressions
		while ( t && t != last ) {
			last = t;

			var p = jQuery.parse, m;

			for ( var i = 0; p[i]; i++ ) {
				m = p[i].exec( t );

				if ( m ) {
					// Remove what we just matched
					t = t.substring( m[0].length );

					m[2] = m[2].replace(/\\/g, "");
					break;
				}
			}

			if ( !m )
				break;

			// :not() is a special case that can be optimized by
			// keeping it out of the expression list
			if ( m[1] == ":" && m[2] == "not" )
				// optimize if only one selector found (most common case)
				r = isSimple.test( m[3] ) ?
					jQuery.filter(m[3], r, true).r :
					jQuery( r ).not( m[3] );

			// We can get a big speed boost by filtering by class here
			else if ( m[1] == "." )
				r = jQuery.classFilter(r, m[2], not);

			else if ( m[1] == "[" ) {
				var tmp = [], type = m[3];

				for ( var i = 0, rl = r.length; i < rl; i++ ) {
					var a = r[i], z = a[ jQuery.props[m[2]] || m[2] ];

					if ( z == null || /href|src|selected/.test(m[2]) )
						z = jQuery.attr(a,m[2]) || '';

					if ( (type == "" && !!z ||
						 type == "=" && z == m[5] ||
						 type == "!=" && z != m[5] ||
						 type == "^=" && z && !z.indexOf(m[5]) ||
						 type == "$=" && z.substr(z.length - m[5].length) == m[5] ||
						 (type == "*=" || type == "~=") && z.indexOf(m[5]) >= 0) ^ not )
							tmp.push( a );
				}

				r = tmp;

			// We can get a speed boost by handling nth-child here
			} else if ( m[1] == ":" && m[2] == "nth-child" ) {
				var merge = {}, tmp = [],
					// parse equations like 'even', 'odd', '5', '2n', '3n+2', '4n-1', '-n+6'
					test = /(-?)(\d*)n((?:\+|-)?\d*)/.exec(
						m[3] == "even" && "2n" || m[3] == "odd" && "2n+1" ||
						!/\D/.test(m[3]) && "0n+" + m[3] || m[3]),
					// calculate the numbers (first)n+(last) including if they are negative
					first = (test[1] + (test[2] || 1)) - 0, last = test[3] - 0;

				// loop through all the elements left in the jQuery object
				for ( var i = 0, rl = r.length; i < rl; i++ ) {
					var node = r[i], parentNode = node.parentNode, id = jQuery.data(parentNode);

					if ( !merge[id] ) {
						var c = 1;

						for ( var n = parentNode.firstChild; n; n = n.nextSibling )
							if ( n.nodeType == 1 )
								n.nodeIndex = c++;

						merge[id] = true;
					}

					var add = false;

					if ( first == 0 ) {
						if ( node.nodeIndex == last )
							add = true;
					} else if ( (node.nodeIndex - last) % first == 0 && (node.nodeIndex - last) / first >= 0 )
						add = true;

					if ( add ^ not )
						tmp.push( node );
				}

				r = tmp;

			// Otherwise, find the expression to execute
			} else {
				var fn = jQuery.expr[ m[1] ];
				if ( typeof fn == "object" )
					fn = fn[ m[2] ];

				if ( typeof fn == "string" )
					fn = eval("false||function(a,i){return " + fn + ";}");

				// Execute it against the current filter
				r = jQuery.grep( r, function(elem, i){
					return fn(elem, i, m, r);
				}, not );
			}
		}

		// Return an array of filtered elements (r)
		// and the modified expression string (t)
		return { r: r, t: t };
	},

	dir: function( elem, dir ){
		var matched = [],
			cur = elem[dir];
		while ( cur && cur != document ) {
			if ( cur.nodeType == 1 )
				matched.push( cur );
			cur = cur[dir];
		}
		return matched;
	},

	nth: function(cur,result,dir,elem){
		result = result || 1;
		var num = 0;

		for ( ; cur; cur = cur[dir] )
			if ( cur.nodeType == 1 && ++num == result )
				break;

		return cur;
	},

	sibling: function( n, elem ) {
		var r = [];

		for ( ; n; n = n.nextSibling ) {
			if ( n.nodeType == 1 && n != elem )
				r.push( n );
		}

		return r;
	}
});
/*
 * A number of helper functions used for managing events.
 * Many of the ideas behind this code orignated from
 * Dean Edwards' addEvent library.
 */
jQuery.event = {

	// Bind an event to an element
	// Original by Dean Edwards
	add: function(elem, types, handler, data) {
		if ( elem.nodeType == 3 || elem.nodeType == 8 )
			return;

		// For whatever reason, IE has trouble passing the window object
		// around, causing it to be cloned in the process
		if ( jQuery.browser.msie && elem.setInterval )
			elem = window;

		// Make sure that the function being executed has a unique ID
		if ( !handler.guid )
			handler.guid = this.guid++;

		// if data is passed, bind to handler
		if( data != undefined ) {
			// Create temporary function pointer to original handler
			var fn = handler;

			// Create unique handler function, wrapped around original handler
			handler = this.proxy( fn, function() {
				// Pass arguments and context to original handler
				return fn.apply(this, arguments);
			});

			// Store data in unique handler
			handler.data = data;
		}

		// Init the element's event structure
		var events = jQuery.data(elem, "events") || jQuery.data(elem, "events", {}),
			handle = jQuery.data(elem, "handle") || jQuery.data(elem, "handle", function(){
				// Handle the second event of a trigger and when
				// an event is called after a page has unloaded
				if ( typeof jQuery != "undefined" && !jQuery.event.triggered )
					return jQuery.event.handle.apply(arguments.callee.elem, arguments);
			});
		// Add elem as a property of the handle function
		// This is to prevent a memory leak with non-native
		// event in IE.
		handle.elem = elem;

		// Handle multiple events separated by a space
		// jQuery(...).bind("mouseover mouseout", fn);
		jQuery.each(types.split(/\s+/), function(index, type) {
			// Namespaced event handlers
			var parts = type.split(".");
			type = parts[0];
			handler.type = parts[1];

			// Get the current list of functions bound to this event
			var handlers = events[type];

			// Init the event handler queue
			if (!handlers) {
				handlers = events[type] = {};

				// Check for a special event handler
				// Only use addEventListener/attachEvent if the special
				// events handler returns false
				if ( !jQuery.event.special[type] || jQuery.event.special[type].setup.call(elem) === false ) {
					// Bind the global event handler to the element
					if (elem.addEventListener)
						elem.addEventListener(type, handle, false);
					else if (elem.attachEvent)
						elem.attachEvent("on" + type, handle);
				}
			}

			// Add the function to the element's handler list
			handlers[handler.guid] = handler;

			// Keep track of which events have been used, for global triggering
			jQuery.event.global[type] = true;
		});

		// Nullify elem to prevent memory leaks in IE
		elem = null;
	},

	guid: 1,
	global: {},

	// Detach an event or set of events from an element
	remove: function(elem, types, handler) {
		// don't do events on text and comment nodes
		if ( elem.nodeType == 3 || elem.nodeType == 8 )
			return;

		var events = jQuery.data(elem, "events"), ret, index;

		if ( events ) {
			// Unbind all events for the element
			if ( types == undefined || (typeof types == "string" && types.charAt(0) == ".") )
				for ( var type in events )
					this.remove( elem, type + (types || "") );
			else {
				// types is actually an event object here
				if ( types.type ) {
					handler = types.handler;
					types = types.type;
				}

				// Handle multiple events seperated by a space
				// jQuery(...).unbind("mouseover mouseout", fn);
				jQuery.each(types.split(/\s+/), function(index, type){
					// Namespaced event handlers
					var parts = type.split(".");
					type = parts[0];

					if ( events[type] ) {
						// remove the given handler for the given type
						if ( handler )
							delete events[type][handler.guid];

						// remove all handlers for the given type
						else
							for ( handler in events[type] )
								// Handle the removal of namespaced events
								if ( !parts[1] || events[type][handler].type == parts[1] )
									delete events[type][handler];

						// remove generic event handler if no more handlers exist
						for ( ret in events[type] ) break;
						if ( !ret ) {
							if ( !jQuery.event.special[type] || jQuery.event.special[type].teardown.call(elem) === false ) {
								if (elem.removeEventListener)
									elem.removeEventListener(type, jQuery.data(elem, "handle"), false);
								else if (elem.detachEvent)
									elem.detachEvent("on" + type, jQuery.data(elem, "handle"));
							}
							ret = null;
							delete events[type];
						}
					}
				});
			}

			// Remove the expando if it's no longer used
			for ( ret in events ) break;
			if ( !ret ) {
				var handle = jQuery.data( elem, "handle" );
				if ( handle ) handle.elem = null;
				jQuery.removeData( elem, "events" );
				jQuery.removeData( elem, "handle" );
			}
		}
	},

	trigger: function(type, data, elem, donative, extra) {
		// Clone the incoming data, if any
		data = jQuery.makeArray(data);

		if ( type.indexOf("!") >= 0 ) {
			type = type.slice(0, -1);
			var exclusive = true;
		}

		// Handle a global trigger
		if ( !elem ) {
			// Only trigger if we've ever bound an event for it
			if ( this.global[type] )
				jQuery("*").add([window, document]).trigger(type, data);

		// Handle triggering a single element
		} else {
			// don't do events on text and comment nodes
			if ( elem.nodeType == 3 || elem.nodeType == 8 )
				return undefined;

			var val, ret, fn = jQuery.isFunction( elem[ type ] || null ),
				// Check to see if we need to provide a fake event, or not
				event = !data[0] || !data[0].preventDefault;

			// Pass along a fake event
			if ( event ) {
				data.unshift({
					type: type,
					target: elem,
					preventDefault: function(){},
					stopPropagation: function(){},
					timeStamp: now()
				});
				data[0][expando] = true; // no need to fix fake event
			}

			// Enforce the right trigger type
			data[0].type = type;
			if ( exclusive )
				data[0].exclusive = true;

			// Trigger the event, it is assumed that "handle" is a function
			var handle = jQuery.data(elem, "handle");
			if ( handle )
				val = handle.apply( elem, data );

			// Handle triggering native .onfoo handlers (and on links since we don't call .click() for links)
			if ( (!fn || (jQuery.nodeName(elem, 'a') && type == "click")) && elem["on"+type] && elem["on"+type].apply( elem, data ) === false )
				val = false;

			// Extra functions don't get the custom event object
			if ( event )
				data.shift();

			// Handle triggering of extra function
			if ( extra && jQuery.isFunction( extra ) ) {
				// call the extra function and tack the current return value on the end for possible inspection
				ret = extra.apply( elem, val == null ? data : data.concat( val ) );
				// if anything is returned, give it precedence and have it overwrite the previous value
				if (ret !== undefined)
					val = ret;
			}

			// Trigger the native events (except for clicks on links)
			if ( fn && donative !== false && val !== false && !(jQuery.nodeName(elem, 'a') && type == "click") ) {
				this.triggered = true;
				try {
					elem[ type ]();
				// prevent IE from throwing an error for some hidden elements
				} catch (e) {}
			}

			this.triggered = false;
		}

		return val;
	},

	handle: function(event) {
		// returned undefined or false
		var val, ret, namespace, all, handlers;

		event = arguments[0] = jQuery.event.fix( event || window.event );

		// Namespaced event handlers
		namespace = event.type.split(".");
		event.type = namespace[0];
		namespace = namespace[1];
		// Cache this now, all = true means, any handler
		all = !namespace && !event.exclusive;

		handlers = ( jQuery.data(this, "events") || {} )[event.type];

		for ( var j in handlers ) {
			var handler = handlers[j];

			// Filter the functions by class
			if ( all || handler.type == namespace ) {
				// Pass in a reference to the handler function itself
				// So that we can later remove it
				event.handler = handler;
				event.data = handler.data;

				ret = handler.apply( this, arguments );

				if ( val !== false )
					val = ret;

				if ( ret === false ) {
					event.preventDefault();
					event.stopPropagation();
				}
			}
		}

		return val;
	},

	fix: function(event) {
		if ( event[expando] == true )
			return event;

		// store a copy of the original event object
		// and "clone" to set read-only properties
		var originalEvent = event;
		event = { originalEvent: originalEvent };
		var props = "altKey attrChange attrName bubbles button cancelable charCode clientX clientY ctrlKey currentTarget data detail eventPhase fromElement handler keyCode metaKey newValue originalTarget pageX pageY prevValue relatedNode relatedTarget screenX screenY shiftKey srcElement target timeStamp toElement type view wheelDelta which".split(" ");
		for ( var i=props.length; i; i-- )
			event[ props[i] ] = originalEvent[ props[i] ];

		// Mark it as fixed
		event[expando] = true;

		// add preventDefault and stopPropagation since
		// they will not work on the clone
		event.preventDefault = function() {
			// if preventDefault exists run it on the original event
			if (originalEvent.preventDefault)
				originalEvent.preventDefault();
			// otherwise set the returnValue property of the original event to false (IE)
			originalEvent.returnValue = false;
		};
		event.stopPropagation = function() {
			// if stopPropagation exists run it on the original event
			if (originalEvent.stopPropagation)
				originalEvent.stopPropagation();
			// otherwise set the cancelBubble property of the original event to true (IE)
			originalEvent.cancelBubble = true;
		};

		// Fix timeStamp
		event.timeStamp = event.timeStamp || now();

		// Fix target property, if necessary
		if ( !event.target )
			event.target = event.srcElement || document; // Fixes #1925 where srcElement might not be defined either

		// check if target is a textnode (safari)
		if ( event.target.nodeType == 3 )
			event.target = event.target.parentNode;

		// Add relatedTarget, if necessary
		if ( !event.relatedTarget && event.fromElement )
			event.relatedTarget = event.fromElement == event.target ? event.toElement : event.fromElement;

		// Calculate pageX/Y if missing and clientX/Y available
		if ( event.pageX == null && event.clientX != null ) {
			var doc = document.documentElement, body = document.body;
			event.pageX = event.clientX + (doc && doc.scrollLeft || body && body.scrollLeft || 0) - (doc.clientLeft || 0);
			event.pageY = event.clientY + (doc && doc.scrollTop || body && body.scrollTop || 0) - (doc.clientTop || 0);
		}

		// Add which for key events
		if ( !event.which && ((event.charCode || event.charCode === 0) ? event.charCode : event.keyCode) )
			event.which = event.charCode || event.keyCode;

		// Add metaKey to non-Mac browsers (use ctrl for PC's and Meta for Macs)
		if ( !event.metaKey && event.ctrlKey )
			event.metaKey = event.ctrlKey;

		// Add which for click: 1 == left; 2 == middle; 3 == right
		// Note: button is not normalized, so don't use it
		if ( !event.which && event.button )
			event.which = (event.button & 1 ? 1 : ( event.button & 2 ? 3 : ( event.button & 4 ? 2 : 0 ) ));

		return event;
	},

	proxy: function( fn, proxy ){
		// Set the guid of unique handler to the same of original handler, so it can be removed
		proxy.guid = fn.guid = fn.guid || proxy.guid || this.guid++;
		// So proxy can be declared as an argument
		return proxy;
	},

	special: {
		ready: {
			setup: function() {
				// Make sure the ready event is setup
				bindReady();
				return;
			},

			teardown: function() { return; }
		},

		mouseenter: {
			setup: function() {
				if ( jQuery.browser.msie ) return false;
				jQuery(this).bind("mouseover", jQuery.event.special.mouseenter.handler);
				return true;
			},

			teardown: function() {
				if ( jQuery.browser.msie ) return false;
				jQuery(this).unbind("mouseover", jQuery.event.special.mouseenter.handler);
				return true;
			},

			handler: function(event) {
				// If we actually just moused on to a sub-element, ignore it
				if ( withinElement(event, this) ) return true;
				// Execute the right handlers by setting the event type to mouseenter
				event.type = "mouseenter";
				return jQuery.event.handle.apply(this, arguments);
			}
		},

		mouseleave: {
			setup: function() {
				if ( jQuery.browser.msie ) return false;
				jQuery(this).bind("mouseout", jQuery.event.special.mouseleave.handler);
				return true;
			},

			teardown: function() {
				if ( jQuery.browser.msie ) return false;
				jQuery(this).unbind("mouseout", jQuery.event.special.mouseleave.handler);
				return true;
			},

			handler: function(event) {
				// If we actually just moused on to a sub-element, ignore it
				if ( withinElement(event, this) ) return true;
				// Execute the right handlers by setting the event type to mouseleave
				event.type = "mouseleave";
				return jQuery.event.handle.apply(this, arguments);
			}
		}
	}
};

jQuery.fn.extend({
	bind: function( type, data, fn ) {
		return type == "unload" ? this.one(type, data, fn) : this.each(function(){
			jQuery.event.add( this, type, fn || data, fn && data );
		});
	},

	one: function( type, data, fn ) {
		var one = jQuery.event.proxy( fn || data, function(event) {
			jQuery(this).unbind(event, one);
			return (fn || data).apply( this, arguments );
		});
		return this.each(function(){
			jQuery.event.add( this, type, one, fn && data);
		});
	},

	unbind: function( type, fn ) {
		return this.each(function(){
			jQuery.event.remove( this, type, fn );
		});
	},

	trigger: function( type, data, fn ) {
		return this.each(function(){
			jQuery.event.trigger( type, data, this, true, fn );
		});
	},

	triggerHandler: function( type, data, fn ) {
		return this[0] && jQuery.event.trigger( type, data, this[0], false, fn );
	},

	toggle: function( fn ) {
		// Save reference to arguments for access in closure
		var args = arguments, i = 1;

		// link all the functions, so any of them can unbind this click handler
		while( i < args.length )
			jQuery.event.proxy( fn, args[i++] );

		return this.click( jQuery.event.proxy( fn, function(event) {
			// Figure out which function to execute
			this.lastToggle = ( this.lastToggle || 0 ) % i;

			// Make sure that clicks stop
			event.preventDefault();

			// and execute the function
			return args[ this.lastToggle++ ].apply( this, arguments ) || false;
		}));
	},

	hover: function(fnOver, fnOut) {
		return this.bind('mouseenter', fnOver).bind('mouseleave', fnOut);
	},

	ready: function(fn) {
		// Attach the listeners
		bindReady();

		// If the DOM is already ready
		if ( jQuery.isReady )
			// Execute the function immediately
			fn.call( document, jQuery );

		// Otherwise, remember the function for later
		else
			// Add the function to the wait list
			jQuery.readyList.push( function() { return fn.call(this, jQuery); } );

		return this;
	}
});

jQuery.extend({
	isReady: false,
	readyList: [],
	// Handle when the DOM is ready
	ready: function() {
		// Make sure that the DOM is not already loaded
		if ( !jQuery.isReady ) {
			// Remember that the DOM is ready
			jQuery.isReady = true;

			// If there are functions bound, to execute
			if ( jQuery.readyList ) {
				// Execute all of them
				jQuery.each( jQuery.readyList, function(){
					this.call( document );
				});

				// Reset the list of functions
				jQuery.readyList = null;
			}

			// Trigger any bound ready events
			jQuery(document).triggerHandler("ready");
		}
	}
});

var readyBound = false;

function bindReady(){
	if ( readyBound ) return;
	readyBound = true;

	// Mozilla, Opera (see further below for it) and webkit nightlies currently support this event
	if ( document.addEventListener && !jQuery.browser.opera)
		// Use the handy event callback
		document.addEventListener( "DOMContentLoaded", jQuery.ready, false );

	// If IE is used and is not in a frame
	// Continually check to see if the document is ready
	if ( jQuery.browser.msie && window == top ) (function(){
		if (jQuery.isReady) return;
		try {
			// If IE is used, use the trick by Diego Perini
			// http://javascript.nwbox.com/IEContentLoaded/
			document.documentElement.doScroll("left");
		} catch( error ) {
			setTimeout( arguments.callee, 0 );
			return;
		}
		// and execute any waiting functions
		jQuery.ready();
	})();

	if ( jQuery.browser.opera )
		document.addEventListener( "DOMContentLoaded", function () {
			if (jQuery.isReady) return;
			for (var i = 0; i < document.styleSheets.length; i++)
				if (document.styleSheets[i].disabled) {
					setTimeout( arguments.callee, 0 );
					return;
				}
			// and execute any waiting functions
			jQuery.ready();
		}, false);

	if ( jQuery.browser.safari ) {
		var numStyles;
		(function(){
			if (jQuery.isReady) return;
			if ( document.readyState != "loaded" && document.readyState != "complete" ) {
				setTimeout( arguments.callee, 0 );
				return;
			}
			if ( numStyles === undefined )
				numStyles = jQuery("style, link[rel=stylesheet]").length;
			if ( document.styleSheets.length != numStyles ) {
				setTimeout( arguments.callee, 0 );
				return;
			}
			// and execute any waiting functions
			jQuery.ready();
		})();
	}

	// A fallback to window.onload, that will always work
	jQuery.event.add( window, "load", jQuery.ready );
}

jQuery.each( ("blur,focus,load,resize,scroll,unload,click,dblclick," +
	"mousedown,mouseup,mousemove,mouseover,mouseout,change,select," +
	"submit,keydown,keypress,keyup,error").split(","), function(i, name){

	// Handle event binding
	jQuery.fn[name] = function(fn){
		return fn ? this.bind(name, fn) : this.trigger(name);
	};
});

// Checks if an event happened on an element within another element
// Used in jQuery.event.special.mouseenter and mouseleave handlers
var withinElement = function(event, elem) {
	// Check if mouse(over|out) are still within the same parent element
	var parent = event.relatedTarget;
	// Traverse up the tree
	while ( parent && parent != elem ) try { parent = parent.parentNode; } catch(error) { parent = elem; }
	// Return true if we actually just moused on to a sub-element
	return parent == elem;
};

// Prevent memory leaks in IE
// And prevent errors on refresh with events like mouseover in other browsers
// Window isn't included so as not to unbind existing unload events
jQuery(window).bind("unload", function() {
	jQuery("*").add(document).unbind();
});
jQuery.fn.extend({
	// Keep a copy of the old load
	_load: jQuery.fn.load,

	load: function( url, params, callback ) {
		if ( typeof url != 'string' )
			return this._load( url );

		var off = url.indexOf(" ");
		if ( off >= 0 ) {
			var selector = url.slice(off, url.length);
			url = url.slice(0, off);
		}

		callback = callback || function(){};

		// Default to a GET request
		var type = "GET";

		// If the second parameter was provided
		if ( params )
			// If it's a function
			if ( jQuery.isFunction( params ) ) {
				// We assume that it's the callback
				callback = params;
				params = null;

			// Otherwise, build a param string
			} else {
				params = jQuery.param( params );
				type = "POST";
			}

		var self = this;

		// Request the remote document
		jQuery.ajax({
			url: url,
			type: type,
			dataType: "html",
			data: params,
			complete: function(res, status){
				// If successful, inject the HTML into all the matched elements
				if ( status == "success" || status == "notmodified" )
					// See if a selector was specified
					self.html( selector ?
						// Create a dummy div to hold the results
						jQuery("<div/>")
							// inject the contents of the document in, removing the scripts
							// to avoid any 'Permission Denied' errors in IE
							.append(res.responseText.replace(/<script(.|\s)*?\/script>/g, ""))

							// Locate the specified elements
							.find(selector) :

						// If not, just inject the full result
						res.responseText );

				self.each( callback, [res.responseText, status, res] );
			}
		});
		return this;
	},

	serialize: function() {
		return jQuery.param(this.serializeArray());
	},
	serializeArray: function() {
		return this.map(function(){
			return jQuery.nodeName(this, "form") ?
				jQuery.makeArray(this.elements) : this;
		})
		.filter(function(){
			return this.name && !this.disabled &&
				(this.checked || /select|textarea/i.test(this.nodeName) ||
					/text|hidden|password/i.test(this.type));
		})
		.map(function(i, elem){
			var val = jQuery(this).val();
			return val == null ? null :
				val.constructor == Array ?
					jQuery.map( val, function(val, i){
						return {name: elem.name, value: val};
					}) :
					{name: elem.name, value: val};
		}).get();
	}
});

// Attach a bunch of functions for handling common AJAX events
jQuery.each( "ajaxStart,ajaxStop,ajaxComplete,ajaxError,ajaxSuccess,ajaxSend".split(","), function(i,o){
	jQuery.fn[o] = function(f){
		return this.bind(o, f);
	};
});

var jsc = now();

jQuery.extend({
	get: function( url, data, callback, type ) {
		// shift arguments if data argument was ommited
		if ( jQuery.isFunction( data ) ) {
			callback = data;
			data = null;
		}

		return jQuery.ajax({
			type: "GET",
			url: url,
			data: data,
			success: callback,
			dataType: type
		});
	},

	getScript: function( url, callback ) {
		return jQuery.get(url, null, callback, "script");
	},

	getJSON: function( url, data, callback ) {
		return jQuery.get(url, data, callback, "json");
	},

	post: function( url, data, callback, type ) {
		if ( jQuery.isFunction( data ) ) {
			callback = data;
			data = {};
		}

		return jQuery.ajax({
			type: "POST",
			url: url,
			data: data,
			success: callback,
			dataType: type
		});
	},

	ajaxSetup: function( settings ) {
		jQuery.extend( jQuery.ajaxSettings, settings );
	},

	ajaxSettings: {
		url: location.href,
		global: true,
		type: "GET",
		timeout: 0,
		contentType: "application/x-www-form-urlencoded",
		processData: true,
		async: true,
		data: null,
		username: null,
		password: null,
		accepts: {
			xml: "application/xml, text/xml",
			html: "text/html",
			script: "text/javascript, application/javascript",
			json: "application/json, text/javascript",
			text: "text/plain",
			_default: "*/*"
		}
	},

	// Last-Modified header cache for next request
	lastModified: {},

	ajax: function( s ) {
		// Extend the settings, but re-extend 's' so that it can be
		// checked again later (in the test suite, specifically)
		s = jQuery.extend(true, s, jQuery.extend(true, {}, jQuery.ajaxSettings, s));

		var jsonp, jsre = /=\?(&|$)/g, status, data,
			type = s.type.toUpperCase();

		// convert data if not already a string
		if ( s.data && s.processData && typeof s.data != "string" )
			s.data = jQuery.param(s.data);

		// Handle JSONP Parameter Callbacks
		if ( s.dataType == "jsonp" ) {
			if ( type == "GET" ) {
				if ( !s.url.match(jsre) )
					s.url += (s.url.match(/\?/) ? "&" : "?") + (s.jsonp || "callback") + "=?";
			} else if ( !s.data || !s.data.match(jsre) )
				s.data = (s.data ? s.data + "&" : "") + (s.jsonp || "callback") + "=?";
			s.dataType = "json";
		}

		// Build temporary JSONP function
		if ( s.dataType == "json" && (s.data && s.data.match(jsre) || s.url.match(jsre)) ) {
			jsonp = "jsonp" + jsc++;

			// Replace the =? sequence both in the query string and the data
			if ( s.data )
				s.data = (s.data + "").replace(jsre, "=" + jsonp + "$1");
			s.url = s.url.replace(jsre, "=" + jsonp + "$1");

			// We need to make sure
			// that a JSONP style response is executed properly
			s.dataType = "script";

			// Handle JSONP-style loading
			window[ jsonp ] = function(tmp){
				data = tmp;
				success();
				complete();
				// Garbage collect
				window[ jsonp ] = undefined;
				try{ delete window[ jsonp ]; } catch(e){}
				if ( head )
					head.removeChild( script );
			};
		}

		if ( s.dataType == "script" && s.cache == null )
			s.cache = false;

		if ( s.cache === false && type == "GET" ) {
			var ts = now();
			// try replacing _= if it is there
			var ret = s.url.replace(/(\?|&)_=.*?(&|$)/, "$1_=" + ts + "$2");
			// if nothing was replaced, add timestamp to the end
			s.url = ret + ((ret == s.url) ? (s.url.match(/\?/) ? "&" : "?") + "_=" + ts : "");
		}

		// If data is available, append data to url for get requests
		if ( s.data && type == "GET" ) {
			s.url += (s.url.match(/\?/) ? "&" : "?") + s.data;

			// IE likes to send both get and post data, prevent this
			s.data = null;
		}

		// Watch for a new set of requests
		if ( s.global && ! jQuery.active++ )
			jQuery.event.trigger( "ajaxStart" );

		// Matches an absolute URL, and saves the domain
		var remote = /^(?:\w+:)?\/\/([^\/?#]+)/;

		// If we're requesting a remote document
		// and trying to load JSON or Script with a GET
		if ( s.dataType == "script" && type == "GET"
				&& remote.test(s.url) && remote.exec(s.url)[1] != location.host ){
			var head = document.getElementsByTagName("head")[0];
			var script = document.createElement("script");
			script.src = s.url;
			if (s.scriptCharset)
				script.charset = s.scriptCharset;

			// Handle Script loading
			if ( !jsonp ) {
				var done = false;

				// Attach handlers for all browsers
				script.onload = script.onreadystatechange = function(){
					if ( !done && (!this.readyState ||
							this.readyState == "loaded" || this.readyState == "complete") ) {
						done = true;
						success();
						complete();
						head.removeChild( script );
					}
				};
			}

			head.appendChild(script);

			// We handle everything using the script element injection
			return undefined;
		}

		var requestDone = false;

		// Create the request object; Microsoft failed to properly
		// implement the XMLHttpRequest in IE7, so we use the ActiveXObject when it is available
		var xhr = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();

		// Open the socket
		// Passing null username, generates a login popup on Opera (#2865)
		if( s.username )
			xhr.open(type, s.url, s.async, s.username, s.password);
		else
			xhr.open(type, s.url, s.async);

		// Need an extra try/catch for cross domain requests in Firefox 3
		try {
			// Set the correct header, if data is being sent
			if ( s.data )
				xhr.setRequestHeader("Content-Type", s.contentType);

			// Set the If-Modified-Since header, if ifModified mode.
			if ( s.ifModified )
				xhr.setRequestHeader("If-Modified-Since",
					jQuery.lastModified[s.url] || "Thu, 01 Jan 1970 00:00:00 GMT" );

			// Set header so the called script knows that it's an XMLHttpRequest
			xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");

			// Set the Accepts header for the server, depending on the dataType
			xhr.setRequestHeader("Accept", s.dataType && s.accepts[ s.dataType ] ?
				s.accepts[ s.dataType ] + ", */*" :
				s.accepts._default );
		} catch(e){}

		// Allow custom headers/mimetypes
		if ( s.beforeSend && s.beforeSend(xhr, s) === false ) {
			// cleanup active request counter
			s.global && jQuery.active--;
			// close opended socket
			xhr.abort();
			return false;
		}

		if ( s.global )
			jQuery.event.trigger("ajaxSend", [xhr, s]);

		// Wait for a response to come back
		var onreadystatechange = function(isTimeout){
			// The transfer is complete and the data is available, or the request timed out
			if ( !requestDone && xhr && (xhr.readyState == 4 || isTimeout == "timeout") ) {
				requestDone = true;

				// clear poll interval
				if (ival) {
					clearInterval(ival);
					ival = null;
				}

				status = isTimeout == "timeout" && "timeout" ||
					!jQuery.httpSuccess( xhr ) && "error" ||
					s.ifModified && jQuery.httpNotModified( xhr, s.url ) && "notmodified" ||
					"success";

				if ( status == "success" ) {
					// Watch for, and catch, XML document parse errors
					try {
						// process the data (runs the xml through httpData regardless of callback)
						data = jQuery.httpData( xhr, s.dataType, s.dataFilter );
					} catch(e) {
						status = "parsererror";
					}
				}

				// Make sure that the request was successful or notmodified
				if ( status == "success" ) {
					// Cache Last-Modified header, if ifModified mode.
					var modRes;
					try {
						modRes = xhr.getResponseHeader("Last-Modified");
					} catch(e) {} // swallow exception thrown by FF if header is not available

					if ( s.ifModified && modRes )
						jQuery.lastModified[s.url] = modRes;

					// JSONP handles its own success callback
					if ( !jsonp )
						success();
				} else
					jQuery.handleError(s, xhr, status);

				// Fire the complete handlers
				complete();

				// Stop memory leaks
				if ( s.async )
					xhr = null;
			}
		};

		if ( s.async ) {
			// don't attach the handler to the request, just poll it instead
			var ival = setInterval(onreadystatechange, 13);

			// Timeout checker
			if ( s.timeout > 0 )
				setTimeout(function(){
					// Check to see if the request is still happening
					if ( xhr ) {
						// Cancel the request
						xhr.abort();

						if( !requestDone )
							onreadystatechange( "timeout" );
					}
				}, s.timeout);
		}

		// Send the data
		try {
			xhr.send(s.data);
		} catch(e) {
			jQuery.handleError(s, xhr, null, e);
		}

		// firefox 1.5 doesn't fire statechange for sync requests
		if ( !s.async )
			onreadystatechange();

		function success(){
			// If a local callback was specified, fire it and pass it the data
			if ( s.success )
				s.success( data, status );

			// Fire the global callback
			if ( s.global )
				jQuery.event.trigger( "ajaxSuccess", [xhr, s] );
		}

		function complete(){
			// Process result
			if ( s.complete )
				s.complete(xhr, status);

			// The request was completed
			if ( s.global )
				jQuery.event.trigger( "ajaxComplete", [xhr, s] );

			// Handle the global AJAX counter
			if ( s.global && ! --jQuery.active )
				jQuery.event.trigger( "ajaxStop" );
		}

		// return XMLHttpRequest to allow aborting the request etc.
		return xhr;
	},

	handleError: function( s, xhr, status, e ) {
		// If a local callback was specified, fire it
		if ( s.error ) s.error( xhr, status, e );

		// Fire the global callback
		if ( s.global )
			jQuery.event.trigger( "ajaxError", [xhr, s, e] );
	},

	// Counter for holding the number of active queries
	active: 0,

	// Determines if an XMLHttpRequest was successful or not
	httpSuccess: function( xhr ) {
		try {
			// IE error sometimes returns 1223 when it should be 204 so treat it as success, see #1450
			return !xhr.status && location.protocol == "file:" ||
				( xhr.status >= 200 && xhr.status < 300 ) || xhr.status == 304 || xhr.status == 1223 ||
				jQuery.browser.safari && xhr.status == undefined;
		} catch(e){}
		return false;
	},

	// Determines if an XMLHttpRequest returns NotModified
	httpNotModified: function( xhr, url ) {
		try {
			var xhrRes = xhr.getResponseHeader("Last-Modified");

			// Firefox always returns 200. check Last-Modified date
			return xhr.status == 304 || xhrRes == jQuery.lastModified[url] ||
				jQuery.browser.safari && xhr.status == undefined;
		} catch(e){}
		return false;
	},

	httpData: function( xhr, type, filter ) {
		var ct = xhr.getResponseHeader("content-type"),
			xml = type == "xml" || !type && ct && ct.indexOf("xml") >= 0,
			data = xml ? xhr.responseXML : xhr.responseText;

		if ( xml && data.documentElement.tagName == "parsererror" )
			throw "parsererror";
			
		// Allow a pre-filtering function to sanitize the response
		if( filter )
			data = filter( data, type );

		// If the type is "script", eval it in global context
		if ( type == "script" )
			jQuery.globalEval( data );

		// Get the JavaScript object, if JSON is used.
		if ( type == "json" )
			data = eval("(" + data + ")");

		return data;
	},

	// Serialize an array of form elements or a set of
	// key/values into a query string
	param: function( a ) {
		var s = [];

		// If an array was passed in, assume that it is an array
		// of form elements
		if ( a.constructor == Array || a.jquery )
			// Serialize the form elements
			jQuery.each( a, function(){
				s.push( encodeURIComponent(this.name) + "=" + encodeURIComponent( this.value ) );
			});

		// Otherwise, assume that it's an object of key/value pairs
		else
			// Serialize the key/values
			for ( var j in a )
				// If the value is an array then the key names need to be repeated
				if ( a[j] && a[j].constructor == Array )
					jQuery.each( a[j], function(){
						s.push( encodeURIComponent(j) + "=" + encodeURIComponent( this ) );
					});
				else
					s.push( encodeURIComponent(j) + "=" + encodeURIComponent( jQuery.isFunction(a[j]) ? a[j]() : a[j] ) );

		// Return the resulting serialization
		return s.join("&").replace(/%20/g, "+");
	}

});
jQuery.fn.extend({
	show: function(speed,callback){
		return speed ?
			this.animate({
				height: "show", width: "show", opacity: "show"
			}, speed, callback) :

			this.filter(":hidden").each(function(){
				this.style.display = this.oldblock || "";
				if ( jQuery.css(this,"display") == "none" ) {
					var elem = jQuery("<" + this.tagName + " />").appendTo("body");
					this.style.display = elem.css("display");
					// handle an edge condition where css is - div { display:none; } or similar
					if (this.style.display == "none")
						this.style.display = "block";
					elem.remove();
				}
			}).end();
	},

	hide: function(speed,callback){
		return speed ?
			this.animate({
				height: "hide", width: "hide", opacity: "hide"
			}, speed, callback) :

			this.filter(":visible").each(function(){
				this.oldblock = this.oldblock || jQuery.css(this,"display");
				this.style.display = "none";
			}).end();
	},

	// Save the old toggle function
	_toggle: jQuery.fn.toggle,

	toggle: function( fn, fn2 ){
		return jQuery.isFunction(fn) && jQuery.isFunction(fn2) ?
			this._toggle.apply( this, arguments ) :
			fn ?
				this.animate({
					height: "toggle", width: "toggle", opacity: "toggle"
				}, fn, fn2) :
				this.each(function(){
					jQuery(this)[ jQuery(this).is(":hidden") ? "show" : "hide" ]();
				});
	},

	slideDown: function(speed,callback){
		return this.animate({height: "show"}, speed, callback);
	},

	slideUp: function(speed,callback){
		return this.animate({height: "hide"}, speed, callback);
	},

	slideToggle: function(speed, callback){
		return this.animate({height: "toggle"}, speed, callback);
	},

	fadeIn: function(speed, callback){
		return this.animate({opacity: "show"}, speed, callback);
	},

	fadeOut: function(speed, callback){
		return this.animate({opacity: "hide"}, speed, callback);
	},

	fadeTo: function(speed,to,callback){
		return this.animate({opacity: to}, speed, callback);
	},

	animate: function( prop, speed, easing, callback ) {
		var optall = jQuery.speed(speed, easing, callback);

		return this[ optall.queue === false ? "each" : "queue" ](function(){
			if ( this.nodeType != 1)
				return false;

			var opt = jQuery.extend({}, optall), p,
				hidden = jQuery(this).is(":hidden"), self = this;

			for ( p in prop ) {
				if ( prop[p] == "hide" && hidden || prop[p] == "show" && !hidden )
					return opt.complete.call(this);

				if ( p == "height" || p == "width" ) {
					// Store display property
					opt.display = jQuery.css(this, "display");

					// Make sure that nothing sneaks out
					opt.overflow = this.style.overflow;
				}
			}

			if ( opt.overflow != null )
				this.style.overflow = "hidden";

			opt.curAnim = jQuery.extend({}, prop);

			jQuery.each( prop, function(name, val){
				var e = new jQuery.fx( self, opt, name );

				if ( /toggle|show|hide/.test(val) )
					e[ val == "toggle" ? hidden ? "show" : "hide" : val ]( prop );
				else {
					var parts = val.toString().match(/^([+-]=)?([\d+-.]+)(.*)$/),
						start = e.cur(true) || 0;

					if ( parts ) {
						var end = parseFloat(parts[2]),
							unit = parts[3] || "px";

						// We need to compute starting value
						if ( unit != "px" ) {
							self.style[ name ] = (end || 1) + unit;
							start = ((end || 1) / e.cur(true)) * start;
							self.style[ name ] = start + unit;
						}

						// If a +=/-= token was provided, we're doing a relative animation
						if ( parts[1] )
							end = ((parts[1] == "-=" ? -1 : 1) * end) + start;

						e.custom( start, end, unit );
					} else
						e.custom( start, val, "" );
				}
			});

			// For JS strict compliance
			return true;
		});
	},

	queue: function(type, fn){
		if ( jQuery.isFunction(type) || ( type && type.constructor == Array )) {
			fn = type;
			type = "fx";
		}

		if ( !type || (typeof type == "string" && !fn) )
			return queue( this[0], type );

		return this.each(function(){
			if ( fn.constructor == Array )
				queue(this, type, fn);
			else {
				queue(this, type).push( fn );

				if ( queue(this, type).length == 1 )
					fn.call(this);
			}
		});
	},

	stop: function(clearQueue, gotoEnd){
		var timers = jQuery.timers;

		if (clearQueue)
			this.queue([]);

		this.each(function(){
			// go in reverse order so anything added to the queue during the loop is ignored
			for ( var i = timers.length - 1; i >= 0; i-- )
				if ( timers[i].elem == this ) {
					if (gotoEnd)
						// force the next step to be the last
						timers[i](true);
					timers.splice(i, 1);
				}
		});

		// start the next in the queue if the last step wasn't forced
		if (!gotoEnd)
			this.dequeue();

		return this;
	}

});

var queue = function( elem, type, array ) {
	if ( elem ){

		type = type || "fx";

		var q = jQuery.data( elem, type + "queue" );

		if ( !q || array )
			q = jQuery.data( elem, type + "queue", jQuery.makeArray(array) );

	}
	return q;
};

jQuery.fn.dequeue = function(type){
	type = type || "fx";

	return this.each(function(){
		var q = queue(this, type);

		q.shift();

		if ( q.length )
			q[0].call( this );
	});
};

jQuery.extend({

	speed: function(speed, easing, fn) {
		var opt = speed && speed.constructor == Object ? speed : {
			complete: fn || !fn && easing ||
				jQuery.isFunction( speed ) && speed,
			duration: speed,
			easing: fn && easing || easing && easing.constructor != Function && easing
		};

		opt.duration = (opt.duration && opt.duration.constructor == Number ?
			opt.duration :
			jQuery.fx.speeds[opt.duration]) || jQuery.fx.speeds.def;

		// Queueing
		opt.old = opt.complete;
		opt.complete = function(){
			if ( opt.queue !== false )
				jQuery(this).dequeue();
			if ( jQuery.isFunction( opt.old ) )
				opt.old.call( this );
		};

		return opt;
	},

	easing: {
		linear: function( p, n, firstNum, diff ) {
			return firstNum + diff * p;
		},
		swing: function( p, n, firstNum, diff ) {
			return ((-Math.cos(p*Math.PI)/2) + 0.5) * diff + firstNum;
		}
	},

	timers: [],
	timerId: null,

	fx: function( elem, options, prop ){
		this.options = options;
		this.elem = elem;
		this.prop = prop;

		if ( !options.orig )
			options.orig = {};
	}

});

jQuery.fx.prototype = {

	// Simple function for setting a style value
	update: function(){
		if ( this.options.step )
			this.options.step.call( this.elem, this.now, this );

		(jQuery.fx.step[this.prop] || jQuery.fx.step._default)( this );

		// Set display property to block for height/width animations
		if ( this.prop == "height" || this.prop == "width" )
			this.elem.style.display = "block";
	},

	// Get the current size
	cur: function(force){
		if ( this.elem[this.prop] != null && this.elem.style[this.prop] == null )
			return this.elem[ this.prop ];

		var r = parseFloat(jQuery.css(this.elem, this.prop, force));
		return r && r > -10000 ? r : parseFloat(jQuery.curCSS(this.elem, this.prop)) || 0;
	},

	// Start an animation from one number to another
	custom: function(from, to, unit){
		this.startTime = now();
		this.start = from;
		this.end = to;
		this.unit = unit || this.unit || "px";
		this.now = this.start;
		this.pos = this.state = 0;
		this.update();

		var self = this;
		function t(gotoEnd){
			return self.step(gotoEnd);
		}

		t.elem = this.elem;

		jQuery.timers.push(t);

		if ( jQuery.timerId == null ) {
			jQuery.timerId = setInterval(function(){
				var timers = jQuery.timers;

				for ( var i = 0; i < timers.length; i++ )
					if ( !timers[i]() )
						timers.splice(i--, 1);

				if ( !timers.length ) {
					clearInterval( jQuery.timerId );
					jQuery.timerId = null;
				}
			}, 13);
		}
	},

	// Simple 'show' function
	show: function(){
		// Remember where we started, so that we can go back to it later
		this.options.orig[this.prop] = jQuery.attr( this.elem.style, this.prop );
		this.options.show = true;

		// Begin the animation
		this.custom(0, this.cur());

		// Make sure that we start at a small width/height to avoid any
		// flash of content
		if ( this.prop == "width" || this.prop == "height" )
			this.elem.style[this.prop] = "1px";

		// Start by showing the element
		jQuery(this.elem).show();
	},

	// Simple 'hide' function
	hide: function(){
		// Remember where we started, so that we can go back to it later
		this.options.orig[this.prop] = jQuery.attr( this.elem.style, this.prop );
		this.options.hide = true;

		// Begin the animation
		this.custom(this.cur(), 0);
	},

	// Each step of an animation
	step: function(gotoEnd){
		var t = now();

		if ( gotoEnd || t > this.options.duration + this.startTime ) {
			this.now = this.end;
			this.pos = this.state = 1;
			this.update();

			this.options.curAnim[ this.prop ] = true;

			var done = true;
			for ( var i in this.options.curAnim )
				if ( this.options.curAnim[i] !== true )
					done = false;

			if ( done ) {
				if ( this.options.display != null ) {
					// Reset the overflow
					this.elem.style.overflow = this.options.overflow;

					// Reset the display
					this.elem.style.display = this.options.display;
					if ( jQuery.css(this.elem, "display") == "none" )
						this.elem.style.display = "block";
				}

				// Hide the element if the "hide" operation was done
				if ( this.options.hide )
					this.elem.style.display = "none";

				// Reset the properties, if the item has been hidden or shown
				if ( this.options.hide || this.options.show )
					for ( var p in this.options.curAnim )
						jQuery.attr(this.elem.style, p, this.options.orig[p]);
			}

			if ( done )
				// Execute the complete function
				this.options.complete.call( this.elem );

			return false;
		} else {
			var n = t - this.startTime;
			this.state = n / this.options.duration;

			// Perform the easing function, defaults to swing
			this.pos = jQuery.easing[this.options.easing || (jQuery.easing.swing ? "swing" : "linear")](this.state, n, 0, 1, this.options.duration);
			this.now = this.start + ((this.end - this.start) * this.pos);

			// Perform the next step of the animation
			this.update();
		}

		return true;
	}

};

jQuery.extend( jQuery.fx, {
	speeds:{
		slow: 600,
 		fast: 200,
 		// Default speed
 		def: 400
	},
	step: {
		scrollLeft: function(fx){
			fx.elem.scrollLeft = fx.now;
		},

		scrollTop: function(fx){
			fx.elem.scrollTop = fx.now;
		},

		opacity: function(fx){
			jQuery.attr(fx.elem.style, "opacity", fx.now);
		},

		_default: function(fx){
			fx.elem.style[ fx.prop ] = fx.now + fx.unit;
		}
	}
});
// The Offset Method
// Originally By Brandon Aaron, part of the Dimension Plugin
// http://jquery.com/plugins/project/dimensions
jQuery.fn.offset = function() {
	var left = 0, top = 0, elem = this[0], results;

	if ( elem ) with ( jQuery.browser ) {
		var parent       = elem.parentNode,
		    offsetChild  = elem,
		    offsetParent = elem.offsetParent,
		    doc          = elem.ownerDocument,
		    safari2      = safari && parseInt(version) < 522 && !/adobeair/i.test(userAgent),
		    css          = jQuery.curCSS,
		    fixed        = css(elem, "position") == "fixed";

		// Use getBoundingClientRect if available
		if ( elem.getBoundingClientRect ) {
			var box = elem.getBoundingClientRect();

			// Add the document scroll offsets
			add(box.left + Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft),
				box.top  + Math.max(doc.documentElement.scrollTop,  doc.body.scrollTop));

			// IE adds the HTML element's border, by default it is medium which is 2px
			// IE 6 and 7 quirks mode the border width is overwritable by the following css html { border: 0; }
			// IE 7 standards mode, the border is always 2px
			// This border/offset is typically represented by the clientLeft and clientTop properties
			// However, in IE6 and 7 quirks mode the clientLeft and clientTop properties are not updated when overwriting it via CSS
			// Therefore this method will be off by 2px in IE while in quirksmode
			add( -doc.documentElement.clientLeft, -doc.documentElement.clientTop );

		// Otherwise loop through the offsetParents and parentNodes
		} else {

			// Initial element offsets
			add( elem.offsetLeft, elem.offsetTop );

			// Get parent offsets
			while ( offsetParent ) {
				// Add offsetParent offsets
				add( offsetParent.offsetLeft, offsetParent.offsetTop );

				// Mozilla and Safari > 2 does not include the border on offset parents
				// However Mozilla adds the border for table or table cells
				if ( mozilla && !/^t(able|d|h)$/i.test(offsetParent.tagName) || safari && !safari2 )
					border( offsetParent );

				// Add the document scroll offsets if position is fixed on any offsetParent
				if ( !fixed && css(offsetParent, "position") == "fixed" )
					fixed = true;

				// Set offsetChild to previous offsetParent unless it is the body element
				offsetChild  = /^body$/i.test(offsetParent.tagName) ? offsetChild : offsetParent;
				// Get next offsetParent
				offsetParent = offsetParent.offsetParent;
			}

			// Get parent scroll offsets
			while ( parent && parent.tagName && !/^body|html$/i.test(parent.tagName) ) {
				// Remove parent scroll UNLESS that parent is inline or a table to work around Opera inline/table scrollLeft/Top bug
				if ( !/^inline|table.*$/i.test(css(parent, "display")) )
					// Subtract parent scroll offsets
					add( -parent.scrollLeft, -parent.scrollTop );

				// Mozilla does not add the border for a parent that has overflow != visible
				if ( mozilla && css(parent, "overflow") != "visible" )
					border( parent );

				// Get next parent
				parent = parent.parentNode;
			}

			// Safari <= 2 doubles body offsets with a fixed position element/offsetParent or absolutely positioned offsetChild
			// Mozilla doubles body offsets with a non-absolutely positioned offsetChild
			if ( (safari2 && (fixed || css(offsetChild, "position") == "absolute")) ||
				(mozilla && css(offsetChild, "position") != "absolute") )
					add( -doc.body.offsetLeft, -doc.body.offsetTop );

			// Add the document scroll offsets if position is fixed
			if ( fixed )
				add(Math.max(doc.documentElement.scrollLeft, doc.body.scrollLeft),
					Math.max(doc.documentElement.scrollTop,  doc.body.scrollTop));
		}

		// Return an object with top and left properties
		results = { top: top, left: left };
	}

	function border(elem) {
		add( jQuery.curCSS(elem, "borderLeftWidth", true), jQuery.curCSS(elem, "borderTopWidth", true) );
	}

	function add(l, t) {
		left += parseInt(l, 10) || 0;
		top += parseInt(t, 10) || 0;
	}

	return results;
};


jQuery.fn.extend({
	position: function() {
		var left = 0, top = 0, results;

		if ( this[0] ) {
			// Get *real* offsetParent
			var offsetParent = this.offsetParent(),

			// Get correct offsets
			offset       = this.offset(),
			parentOffset = /^body|html$/i.test(offsetParent[0].tagName) ? { top: 0, left: 0 } : offsetParent.offset();

			// Subtract element margins
			// note: when an element has margin: auto the offsetLeft and marginLeft 
			// are the same in Safari causing offset.left to incorrectly be 0
			offset.top  -= num( this, 'marginTop' );
			offset.left -= num( this, 'marginLeft' );

			// Add offsetParent borders
			parentOffset.top  += num( offsetParent, 'borderTopWidth' );
			parentOffset.left += num( offsetParent, 'borderLeftWidth' );

			// Subtract the two offsets
			results = {
				top:  offset.top  - parentOffset.top,
				left: offset.left - parentOffset.left
			};
		}

		return results;
	},

	offsetParent: function() {
		var offsetParent = this[0].offsetParent;
		while ( offsetParent && (!/^body|html$/i.test(offsetParent.tagName) && jQuery.css(offsetParent, 'position') == 'static') )
			offsetParent = offsetParent.offsetParent;
		return jQuery(offsetParent);
	}
});


// Create scrollLeft and scrollTop methods
jQuery.each( ['Left', 'Top'], function(i, name) {
	var method = 'scroll' + name;
	
	jQuery.fn[ method ] = function(val) {
		if (!this[0]) return;

		return val != undefined ?

			// Set the scroll offset
			this.each(function() {
				this == window || this == document ?
					window.scrollTo(
						!i ? val : jQuery(window).scrollLeft(),
						 i ? val : jQuery(window).scrollTop()
					) :
					this[ method ] = val;
			}) :

			// Return the scroll offset
			this[0] == window || this[0] == document ?
				self[ i ? 'pageYOffset' : 'pageXOffset' ] ||
					jQuery.boxModel && document.documentElement[ method ] ||
					document.body[ method ] :
				this[0][ method ];
	};
});
// Create innerHeight, innerWidth, outerHeight and outerWidth methods
jQuery.each([ "Height", "Width" ], function(i, name){

	var tl = i ? "Left"  : "Top",  // top or left
		br = i ? "Right" : "Bottom"; // bottom or right

	// innerHeight and innerWidth
	jQuery.fn["inner" + name] = function(){
		return this[ name.toLowerCase() ]() +
			num(this, "padding" + tl) +
			num(this, "padding" + br);
	};

	// outerHeight and outerWidth
	jQuery.fn["outer" + name] = function(margin) {
		return this["inner" + name]() +
			num(this, "border" + tl + "Width") +
			num(this, "border" + br + "Width") +
			(margin ?
				num(this, "margin" + tl) + num(this, "margin" + br) : 0);
	};

});})();

/**
 * Cookie plugin
 *
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */

/**
 * Create a cookie with the given name and value and other optional parameters.
 *
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'jquery.com', secure: true});
 * @desc Create a cookie with all available options.
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example $.cookie('the_cookie', '', {expires: -1});
 * @desc Delete a cookie by setting a date in the past.
 *
 * @param String name The name of the cookie.
 * @param String value The value of the cookie.
 * @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
 *                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
 *                             If set to null or omitted, the cookie will be a session cookie and will not be retained
 *                             when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
 *                        require a secure protocol (like HTTPS).
 * @type undefined
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

/**
 * Get the value of a cookie with the given name.
 *
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 *
 * @param String name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toGMTString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toGMTString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};
/*
 * jQuery Form Plugin
 * version: 2.07 (03/04/2008)
 * @requires jQuery v1.2.2 or later
 *
 * Examples at: http://malsup.com/jquery/form/
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id$
 */
 (function($) {
/**
 * ajaxSubmit() provides a mechanism for submitting an HTML form using AJAX.
 *
 * ajaxSubmit accepts a single argument which can be either a success callback function
 * or an options Object.  If a function is provided it will be invoked upon successful
 * completion of the submit and will be passed the response from the server.
 * If an options Object is provided, the following attributes are supported:
 *
 *  target:   Identifies the element(s) in the page to be updated with the server response.
 *            This value may be specified as a jQuery selection string, a jQuery object,
 *            or a DOM element.
 *            default value: null
 *
 *  url:      URL to which the form data will be submitted.
 *            default value: value of form's 'action' attribute
 *
 *  type:     The method in which the form data should be submitted, 'GET' or 'POST'.
 *            default value: value of form's 'method' attribute (or 'GET' if none found)
 *
 *  data:     Additional data to add to the request, specified as key/value pairs (see $.ajax).
 *
 *  beforeSubmit:  Callback method to be invoked before the form is submitted.
 *            default value: null
 *
 *  success:  Callback method to be invoked after the form has been successfully submitted
 *            and the response has been returned from the server
 *            default value: null
 *
 *  dataType: Expected dataType of the response.  One of: null, 'xml', 'script', or 'json'
 *            default value: null
 *
 *  semantic: Boolean flag indicating whether data must be submitted in semantic order (slower).
 *            default value: false
 *
 *  resetForm: Boolean flag indicating whether the form should be reset if the submit is successful
 *
 *  clearForm: Boolean flag indicating whether the form should be cleared if the submit is successful
 *
 *
 * The 'beforeSubmit' callback can be provided as a hook for running pre-submit logic or for
 * validating the form data.  If the 'beforeSubmit' callback returns false then the form will
 * not be submitted. The 'beforeSubmit' callback is invoked with three arguments: the form data
 * in array format, the jQuery object, and the options object passed into ajaxSubmit.
 * The form data array takes the following form:
 *
 *     [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
 *
 * If a 'success' callback method is provided it is invoked after the response has been returned
 * from the server.  It is passed the responseText or responseXML value (depending on dataType).
 * See jQuery.ajax for further details.
 *
 *
 * The dataType option provides a means for specifying how the server response should be handled.
 * This maps directly to the jQuery.httpData method.  The following values are supported:
 *
 *      'xml':    if dataType == 'xml' the server response is treated as XML and the 'success'
 *                   callback method, if specified, will be passed the responseXML value
 *      'json':   if dataType == 'json' the server response will be evaluted and passed to
 *                   the 'success' callback, if specified
 *      'script': if dataType == 'script' the server response is evaluated in the global context
 *
 *
 * Note that it does not make sense to use both the 'target' and 'dataType' options.  If both
 * are provided the target will be ignored.
 *
 * The semantic argument can be used to force form serialization in semantic order.
 * This is normally true anyway, unless the form contains input elements of type='image'.
 * If your form must be submitted with name/value pairs in semantic order and your form
 * contains an input of type='image" then pass true for this arg, otherwise pass false
 * (or nothing) to avoid the overhead for this logic.
 *
 *
 * When used on its own, ajaxSubmit() is typically bound to a form's submit event like this:
 *
 * $("#form-id").submit(function() {
 *     $(this).ajaxSubmit(options);
 *     return false; // cancel conventional submit
 * });
 *
 * When using ajaxForm(), however, this is done for you.
 *
 * @example
 * $('#myForm').ajaxSubmit(function(data) {
 *     alert('Form submit succeeded! Server returned: ' + data);
 * });
 * @desc Submit form and alert server response
 *
 *
 * @example
 * var options = {
 *     target: '#myTargetDiv'
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc Submit form and update page element with server response
 *
 *
 * @example
 * var options = {
 *     success: function(responseText) {
 *         alert(responseText);
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc Submit form and alert the server response
 *
 *
 * @example
 * var options = {
 *     beforeSubmit: function(formArray, jqForm) {
 *         if (formArray.length == 0) {
 *             alert('Please enter data.');
 *             return false;
 *         }
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc Pre-submit validation which aborts the submit operation if form data is empty
 *
 *
 * @example
 * var options = {
 *     url: myJsonUrl.php,
 *     dataType: 'json',
 *     success: function(data) {
 *        // 'data' is an object representing the the evaluated json data
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc json data returned and evaluated
 *
 *
 * @example
 * var options = {
 *     url: myXmlUrl.php,
 *     dataType: 'xml',
 *     success: function(responseXML) {
 *        // responseXML is XML document object
 *        var data = $('myElement', responseXML).text();
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc XML data returned from server
 *
 *
 * @example
 * var options = {
 *     resetForm: true
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc submit form and reset it if successful
 *
 * @example
 * $('#myForm).submit(function() {
 *    $(this).ajaxSubmit();
 *    return false;
 * });
 * @desc Bind form's submit event to use ajaxSubmit
 *
 *
 * @name ajaxSubmit
 * @type jQuery
 * @param options  object literal containing options which control the form submission process
 * @cat Plugins/Form
 * @return jQuery
 */
$.fn.ajaxSubmit = function(options) {
    if (typeof options == 'function')
        options = { success: options };

    options = $.extend({
        url:  this.attr('action') || window.location.toString(),
        type: this.attr('method') || 'GET'
    }, options || {});

    // hook for manipulating the form data before it is extracted;
    // convenient for use with rich editors like tinyMCE or FCKEditor
    var veto = {};
    this.trigger('form-pre-serialize', [this, options, veto]);
    if (veto.veto) return this;

    var a = this.formToArray(options.semantic);
    if (options.data) {
        options.extraData = options.data;
        for (var n in options.data)
            a.push( { name: n, value: options.data[n] } );
    }

    // give pre-submit callback an opportunity to abort the submit
    if (options.beforeSubmit && options.beforeSubmit(a, this, options) === false) return this;

    // fire vetoable 'validate' event
    this.trigger('form-submit-validate', [a, this, options, veto]);
    if (veto.veto) return this;

    var q = $.param(a);

    if (options.type.toUpperCase() == 'GET') {
        options.url += (options.url.indexOf('?') >= 0 ? '&' : '?') + q;
        options.data = null;  // data is null for 'get'
    }
    else
        options.data = q; // data is the query string for 'post'

    var $form = this, callbacks = [];
    if (options.resetForm) callbacks.push(function() { $form.resetForm(); });
    if (options.clearForm) callbacks.push(function() { $form.clearForm(); });

    // perform a load on the target only if dataType is not provided
    if (!options.dataType && options.target) {
        var oldSuccess = options.success || function(){};
        callbacks.push(function(data) {
            $(options.target).html(data).each(oldSuccess, arguments);
        });
    }
    else if (options.success)
        callbacks.push(options.success);

    options.success = function(data, status) {
        for (var i=0, max=callbacks.length; i < max; i++)
            callbacks[i](data, status, $form);
    };

    // are there files to upload?
    var files = $('input:file', this).fieldValue();
    var found = false;
    for (var j=0; j < files.length; j++)
        if (files[j])
            found = true;

    // options.iframe allows user to force iframe mode
   if (options.iframe || found) { 
       // hack to fix Safari hang (thanks to Tim Molendijk for this)
       // see:  http://groups.google.com/group/jquery-dev/browse_thread/thread/36395b7ab510dd5d
       if ($.browser.safari && options.closeKeepAlive)
           $.get(options.closeKeepAlive, fileUpload);
       else
           fileUpload();
       }
   else
       $.ajax(options);

    // fire 'notify' event
    this.trigger('form-submit-notify', [this, options]);
    return this;


    // private function for handling file uploads (hat tip to YAHOO!)
    function fileUpload() {
        var form = $form[0];
        var opts = $.extend({}, $.ajaxSettings, options);

        var id = 'jqFormIO' + (new Date().getTime());
        var $io = $('<iframe id="' + id + '" name="' + id + '" />');
        var io = $io[0];
        var op8 = $.browser.opera && window.opera.version() < 9;
        if ($.browser.msie || op8) io.src = 'javascript:false;document.write("");';
        $io.css({ position: 'absolute', top: '-1000px', left: '-1000px' });

        var xhr = { // mock object
            responseText: null,
            responseXML: null,
            status: 0,
            statusText: 'n/a',
            getAllResponseHeaders: function() {},
            getResponseHeader: function() {},
            setRequestHeader: function() {}
        };

        var g = opts.global;
        // trigger ajax global events so that activity/block indicators work like normal
        if (g && ! $.active++) $.event.trigger("ajaxStart");
        if (g) $.event.trigger("ajaxSend", [xhr, opts]);

        var cbInvoked = 0;
        var timedOut = 0;

        // take a breath so that pending repaints get some cpu time before the upload starts
        setTimeout(function() {
            // make sure form attrs are set
            var t = $form.attr('target'), a = $form.attr('action');
            $form.attr({
                target:   id,
                encoding: 'multipart/form-data',
                enctype:  'multipart/form-data',
                method:   'POST',
                action:   opts.url
            });

            // support timout
            if (opts.timeout)
                setTimeout(function() { timedOut = true; cb(); }, opts.timeout);

            // add "extra" data to form if provided in options
            var extraInputs = [];
            try {
                if (options.extraData)
                    for (var n in options.extraData)
                        extraInputs.push(
                            $('<input type="hidden" name="'+n+'" value="'+options.extraData[n]+'" />')
                                .appendTo(form)[0]);
            
                // add iframe to doc and submit the form
                $io.appendTo('body');
                io.attachEvent ? io.attachEvent('onload', cb) : io.addEventListener('load', cb, false);
                form.submit();
            }
            finally {
                // reset attrs and remove "extra" input elements
                $form.attr('action', a);
                t ? $form.attr('target', t) : $form.removeAttr('target');
                $(extraInputs).remove();
            }
        }, 10);

        function cb() {
            if (cbInvoked++) return;

            io.detachEvent ? io.detachEvent('onload', cb) : io.removeEventListener('load', cb, false);

            var ok = true;
            try {
                if (timedOut) throw 'timeout';
                // extract the server response from the iframe
                var data, doc;
                doc = io.contentWindow ? io.contentWindow.document : io.contentDocument ? io.contentDocument : io.document;
                xhr.responseText = doc.body ? doc.body.innerHTML : null;
                xhr.responseXML = doc.XMLDocument ? doc.XMLDocument : doc;
                xhr.getResponseHeader = function(header){
                    var headers = {'content-type': opts.dataType};
                    return headers[header];
                };

                if (opts.dataType == 'json' || opts.dataType == 'script') {
                    var ta = doc.getElementsByTagName('textarea')[0];
                    xhr.responseText = ta ? ta.value : xhr.responseText;
                }
                else if (opts.dataType == 'xml' && !xhr.responseXML && xhr.responseText != null) {
                    xhr.responseXML = toXml(xhr.responseText);
                }
                data = $.httpData(xhr, opts.dataType);
            }
            catch(e){
                ok = false;
                $.handleError(opts, xhr, 'error', e);
            }

            // ordering of these callbacks/triggers is odd, but that's how $.ajax does it
            if (ok) {
                opts.success(data, 'success');
                if (g) $.event.trigger("ajaxSuccess", [xhr, opts]);
            }
            if (g) $.event.trigger("ajaxComplete", [xhr, opts]);
            if (g && ! --$.active) $.event.trigger("ajaxStop");
            if (opts.complete) opts.complete(xhr, ok ? 'success' : 'error');

            // clean up
            setTimeout(function() {
                $io.remove();
                xhr.responseXML = null;
            }, 100);
        };

        function toXml(s, doc) {
            if (window.ActiveXObject) {
                doc = new ActiveXObject('Microsoft.XMLDOM');
                doc.async = 'false';
                doc.loadXML(s);
            }
            else
                doc = (new DOMParser()).parseFromString(s, 'text/xml');
            return (doc && doc.documentElement && doc.documentElement.tagName != 'parsererror') ? doc : null;
        };
    };
};

/**
 * ajaxForm() provides a mechanism for fully automating form submission.
 *
 * The advantages of using this method instead of ajaxSubmit() are:
 *
 * 1: This method will include coordinates for <input type="image" /> elements (if the element
 *    is used to submit the form).
 * 2. This method will include the submit element's name/value data (for the element that was
 *    used to submit the form).
 * 3. This method binds the submit() method to the form for you.
 *
 * Note that for accurate x/y coordinates of image submit elements in all browsers
 * you need to also use the "dimensions" plugin (this method will auto-detect its presence).
 *
 * The options argument for ajaxForm works exactly as it does for ajaxSubmit.  ajaxForm merely
 * passes the options argument along after properly binding events for submit elements and
 * the form itself.  See ajaxSubmit for a full description of the options argument.
 *
 *
 * @example
 * var options = {
 *     target: '#myTargetDiv'
 * };
 * $('#myForm').ajaxSForm(options);
 * @desc Bind form's submit event so that 'myTargetDiv' is updated with the server response
 *       when the form is submitted.
 *
 *
 * @example
 * var options = {
 *     success: function(responseText) {
 *         alert(responseText);
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc Bind form's submit event so that server response is alerted after the form is submitted.
 *
 *
 * @example
 * var options = {
 *     beforeSubmit: function(formArray, jqForm) {
 *         if (formArray.length == 0) {
 *             alert('Please enter data.');
 *             return false;
 *         }
 *     }
 * };
 * $('#myForm').ajaxSubmit(options);
 * @desc Bind form's submit event so that pre-submit callback is invoked before the form
 *       is submitted.
 *
 *
 * @name   ajaxForm
 * @param  options  object literal containing options which control the form submission process
 * @return jQuery
 * @cat    Plugins/Form
 * @type   jQuery
 */
$.fn.ajaxForm = function(options) {
    return this.ajaxFormUnbind().bind('submit.form-plugin',function() {
        $(this).ajaxSubmit(options);
        return false;
    }).each(function() {
        // store options in hash
        $(":submit,input:image", this).bind('click.form-plugin',function(e) {
            var $form = this.form;
            $form.clk = this;
            if (this.type == 'image') {
                if (e.offsetX != undefined) {
                    $form.clk_x = e.offsetX;
                    $form.clk_y = e.offsetY;
                } else if (typeof $.fn.offset == 'function') { // try to use dimensions plugin
                    var offset = $(this).offset();
                    $form.clk_x = e.pageX - offset.left;
                    $form.clk_y = e.pageY - offset.top;
                } else {
                    $form.clk_x = e.pageX - this.offsetLeft;
                    $form.clk_y = e.pageY - this.offsetTop;
                }
            }
            // clear form vars
            setTimeout(function() { $form.clk = $form.clk_x = $form.clk_y = null; }, 10);
        });
    });
};


/**
 * ajaxFormUnbind unbinds the event handlers that were bound by ajaxForm
 *
 * @name   ajaxFormUnbind
 * @return jQuery
 * @cat    Plugins/Form
 * @type   jQuery
 */
$.fn.ajaxFormUnbind = function() {
    this.unbind('submit.form-plugin');
    return this.each(function() {
        $(":submit,input:image", this).unbind('click.form-plugin');
    });

};

/**
 * formToArray() gathers form element data into an array of objects that can
 * be passed to any of the following ajax functions: $.get, $.post, or load.
 * Each object in the array has both a 'name' and 'value' property.  An example of
 * an array for a simple login form might be:
 *
 * [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
 *
 * It is this array that is passed to pre-submit callback functions provided to the
 * ajaxSubmit() and ajaxForm() methods.
 *
 * The semantic argument can be used to force form serialization in semantic order.
 * This is normally true anyway, unless the form contains input elements of type='image'.
 * If your form must be submitted with name/value pairs in semantic order and your form
 * contains an input of type='image" then pass true for this arg, otherwise pass false
 * (or nothing) to avoid the overhead for this logic.
 *
 * @example var data = $("#myForm").formToArray();
 * $.post( "myscript.cgi", data );
 * @desc Collect all the data from a form and submit it to the server.
 *
 * @name formToArray
 * @param semantic true if serialization must maintain strict semantic ordering of elements (slower)
 * @type Array<Object>
 * @cat Plugins/Form
 */
$.fn.formToArray = function(semantic) {
    var a = [];
    if (this.length == 0) return a;

    var form = this[0];
    var els = semantic ? form.getElementsByTagName('*') : form.elements;
    if (!els) return a;
    for(var i=0, max=els.length; i < max; i++) {
        var el = els[i];
        var n = el.name;
        if (!n) continue;

        if (semantic && form.clk && el.type == "image") {
            // handle image inputs on the fly when semantic == true
            if(!el.disabled && form.clk == el)
                a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
            continue;
        }

        var v = $.fieldValue(el, true);
        if (v && v.constructor == Array) {
            for(var j=0, jmax=v.length; j < jmax; j++)
                a.push({name: n, value: v[j]});
        }
        else if (v !== null && typeof v != 'undefined')
            a.push({name: n, value: v});
    }

    if (!semantic && form.clk) {
        // input type=='image' are not found in elements array! handle them here
        var inputs = form.getElementsByTagName("input");
        for(var i=0, max=inputs.length; i < max; i++) {
            var input = inputs[i];
            var n = input.name;
            if(n && !input.disabled && input.type == "image" && form.clk == input)
                a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
        }
    }
    return a;
};


/**
 * Serializes form data into a 'submittable' string. This method will return a string
 * in the format: name1=value1&amp;name2=value2
 *
 * The semantic argument can be used to force form serialization in semantic order.
 * If your form must be submitted with name/value pairs in semantic order then pass
 * true for this arg, otherwise pass false (or nothing) to avoid the overhead for
 * this logic (which can be significant for very large forms).
 *
 * @example var data = $("#myForm").formSerialize();
 * $.ajax('POST', "myscript.cgi", data);
 * @desc Collect all the data from a form into a single string
 *
 * @name formSerialize
 * @param semantic true if serialization must maintain strict semantic ordering of elements (slower)
 * @type String
 * @cat Plugins/Form
 */
$.fn.formSerialize = function(semantic) {
    //hand off to jQuery.param for proper encoding
    return $.param(this.formToArray(semantic));
};


/**
 * Serializes all field elements in the jQuery object into a query string.
 * This method will return a string in the format: name1=value1&amp;name2=value2
 *
 * The successful argument controls whether or not serialization is limited to
 * 'successful' controls (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
 * The default value of the successful argument is true.
 *
 * @example var data = $("input").fieldSerialize();
 * @desc Collect the data from all successful input elements into a query string
 *
 * @example var data = $(":radio").fieldSerialize();
 * @desc Collect the data from all successful radio input elements into a query string
 *
 * @example var data = $("#myForm :checkbox").fieldSerialize();
 * @desc Collect the data from all successful checkbox input elements in myForm into a query string
 *
 * @example var data = $("#myForm :checkbox").fieldSerialize(false);
 * @desc Collect the data from all checkbox elements in myForm (even the unchecked ones) into a query string
 *
 * @example var data = $(":input").fieldSerialize();
 * @desc Collect the data from all successful input, select, textarea and button elements into a query string
 *
 * @name fieldSerialize
 * @param successful true if only successful controls should be serialized (default is true)
 * @type String
 * @cat Plugins/Form
 */
$.fn.fieldSerialize = function(successful) {
    var a = [];
    this.each(function() {
        var n = this.name;
        if (!n) return;
        var v = $.fieldValue(this, successful);
        if (v && v.constructor == Array) {
            for (var i=0,max=v.length; i < max; i++)
                a.push({name: n, value: v[i]});
        }
        else if (v !== null && typeof v != 'undefined')
            a.push({name: this.name, value: v});
    });
    //hand off to jQuery.param for proper encoding
    return $.param(a);
};


/**
 * Returns the value(s) of the element in the matched set.  For example, consider the following form:
 *
 *  <form><fieldset>
 *      <input name="A" type="text" />
 *      <input name="A" type="text" />
 *      <input name="B" type="checkbox" value="B1" />
 *      <input name="B" type="checkbox" value="B2"/>
 *      <input name="C" type="radio" value="C1" />
 *      <input name="C" type="radio" value="C2" />
 *  </fieldset></form>
 *
 *  var v = $(':text').fieldValue();
 *  // if no values are entered into the text inputs
 *  v == ['','']
 *  // if values entered into the text inputs are 'foo' and 'bar'
 *  v == ['foo','bar']
 *
 *  var v = $(':checkbox').fieldValue();
 *  // if neither checkbox is checked
 *  v === undefined
 *  // if both checkboxes are checked
 *  v == ['B1', 'B2']
 *
 *  var v = $(':radio').fieldValue();
 *  // if neither radio is checked
 *  v === undefined
 *  // if first radio is checked
 *  v == ['C1']
 *
 * The successful argument controls whether or not the field element must be 'successful'
 * (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
 * The default value of the successful argument is true.  If this value is false the value(s)
 * for each element is returned.
 *
 * Note: This method *always* returns an array.  If no valid value can be determined the
 *       array will be empty, otherwise it will contain one or more values.
 *
 * @example var data = $("#myPasswordElement").fieldValue();
 * alert(data[0]);
 * @desc Alerts the current value of the myPasswordElement element
 *
 * @example var data = $("#myForm :input").fieldValue();
 * @desc Get the value(s) of the form elements in myForm
 *
 * @example var data = $("#myForm :checkbox").fieldValue();
 * @desc Get the value(s) for the successful checkbox element(s) in the jQuery object.
 *
 * @example var data = $("#mySingleSelect").fieldValue();
 * @desc Get the value(s) of the select control
 *
 * @example var data = $(':text').fieldValue();
 * @desc Get the value(s) of the text input or textarea elements
 *
 * @example var data = $("#myMultiSelect").fieldValue();
 * @desc Get the values for the select-multiple control
 *
 * @name fieldValue
 * @param Boolean successful true if only the values for successful controls should be returned (default is true)
 * @type Array<String>
 * @cat Plugins/Form
 */
$.fn.fieldValue = function(successful) {
    for (var val=[], i=0, max=this.length; i < max; i++) {
        var el = this[i];
        var v = $.fieldValue(el, successful);
        if (v === null || typeof v == 'undefined' || (v.constructor == Array && !v.length))
            continue;
        v.constructor == Array ? $.merge(val, v) : val.push(v);
    }
    return val;
};

/**
 * Returns the value of the field element.
 *
 * The successful argument controls whether or not the field element must be 'successful'
 * (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
 * The default value of the successful argument is true.  If the given element is not
 * successful and the successful arg is not false then the returned value will be null.
 *
 * Note: If the successful flag is true (default) but the element is not successful, the return will be null
 * Note: The value returned for a successful select-multiple element will always be an array.
 * Note: If the element has no value the return value will be undefined.
 *
 * @example var data = jQuery.fieldValue($("#myPasswordElement")[0]);
 * @desc Gets the current value of the myPasswordElement element
 *
 * @name fieldValue
 * @param Element el The DOM element for which the value will be returned
 * @param Boolean successful true if value returned must be for a successful controls (default is true)
 * @type String or Array<String> or null or undefined
 * @cat Plugins/Form
 */
$.fieldValue = function(el, successful) {
    var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
    if (typeof successful == 'undefined') successful = true;

    if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
        (t == 'checkbox' || t == 'radio') && !el.checked ||
        (t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
        tag == 'select' && el.selectedIndex == -1))
            return null;

    if (tag == 'select') {
        var index = el.selectedIndex;
        if (index < 0) return null;
        var a = [], ops = el.options;
        var one = (t == 'select-one');
        var max = (one ? index+1 : ops.length);
        for(var i=(one ? index : 0); i < max; i++) {
            var op = ops[i];
            if (op.selected) {
                // extra pain for IE...
                var v = $.browser.msie && !(op.attributes['value'].specified) ? op.text : op.value;
                if (one) return v;
                a.push(v);
            }
        }
        return a;
    }
    return el.value;
};


/**
 * Clears the form data.  Takes the following actions on the form's input fields:
 *  - input text fields will have their 'value' property set to the empty string
 *  - select elements will have their 'selectedIndex' property set to -1
 *  - checkbox and radio inputs will have their 'checked' property set to false
 *  - inputs of type submit, button, reset, and hidden will *not* be effected
 *  - button elements will *not* be effected
 *
 * @example $('form').clearForm();
 * @desc Clears all forms on the page.
 *
 * @name clearForm
 * @type jQuery
 * @cat Plugins/Form
 */
$.fn.clearForm = function() {
    return this.each(function() {
        $('input,select,textarea', this).clearFields();
    });
};

/**
 * Clears the selected form elements.  Takes the following actions on the matched elements:
 *  - input text fields will have their 'value' property set to the empty string
 *  - select elements will have their 'selectedIndex' property set to -1
 *  - checkbox and radio inputs will have their 'checked' property set to false
 *  - inputs of type submit, button, reset, and hidden will *not* be effected
 *  - button elements will *not* be effected
 *
 * @example $('.myInputs').clearFields();
 * @desc Clears all inputs with class myInputs
 *
 * @name clearFields
 * @type jQuery
 * @cat Plugins/Form
 */
$.fn.clearFields = $.fn.clearInputs = function() {
    return this.each(function() {
        var t = this.type, tag = this.tagName.toLowerCase();
        if (t == 'text' || t == 'password' || tag == 'textarea')
            this.value = '';
        else if (t == 'checkbox' || t == 'radio')
            this.checked = false;
        else if (tag == 'select')
            this.selectedIndex = -1;
    });
};


/**
 * Resets the form data.  Causes all form elements to be reset to their original value.
 *
 * @example $('form').resetForm();
 * @desc Resets all forms on the page.
 *
 * @name resetForm
 * @type jQuery
 * @cat Plugins/Form
 */
$.fn.resetForm = function() {
    return this.each(function() {
        // guard against an input with the name of 'reset'
        // note that IE reports the reset function as an 'object'
        if (typeof this.reset == 'function' || (typeof this.reset == 'object' && !this.reset.nodeType))
            this.reset();
    });
};


/**
 * Enables or disables any matching elements.
 *
 * @example $(':radio').enabled(false);
 * @desc Disables all radio buttons
 *
 * @name select
 * @type jQuery
 * @cat Plugins/Form
 */
$.fn.enable = function(b) { 
    if (b == undefined) b = true;
    return this.each(function() { 
        this.disabled = !b 
    });
};

/**
 * Checks/unchecks any matching checkboxes or radio buttons and
 * selects/deselects and matching option elements.
 *
 * @example $(':checkbox').select();
 * @desc Checks all checkboxes
 *
 * @name select
 * @type jQuery
 * @cat Plugins/Form
 */
$.fn.select = function(select) {
    if (select == undefined) select = true;
    return this.each(function() { 
        var t = this.type;
        if (t == 'checkbox' || t == 'radio')
            this.checked = select;
        else if (this.tagName.toLowerCase() == 'option') {
            var $sel = $(this).parent('select');
            if (select && $sel[0] && $sel[0].type == 'select-one') {
                // deselect all other options
                $sel.find('option').select(false);
            }
            this.selected = select;
        }
    });
};

})(jQuery);

/**
* hoverIntent is similar to jQuery's built-in "hover" function except that
* instead of firing the onMouseOver event immediately, hoverIntent checks
* to see if the user's mouse has slowed down (beneath the sensitivity
* threshold) before firing the onMouseOver event.
* 
* hoverIntent r6 // 2007.04.22 // jQuery 1.2.3+
* <http://cherne.net/brian/resources/jquery.hoverIntent.html>
* 
* hoverIntent is currently available for use in all personal or commercial 
* projects under both MIT and GPL licenses. This means that you can choose 
* the license that best suits your project, and use it accordingly.
* 
* // basic usage (just like .hover) receives onMouseOver and onMouseOut functions
* $("ul li").hoverIntent( showNav , hideNav );
* 
* // advanced usage receives configuration object only
* $("ul li").hoverIntent({
*	sensitivity: 7, // number = sensitivity threshold (must be 1 or higher)
*	interval: 100,   // number = milliseconds of polling interval
*	over: showNav,  // function = onMouseOver callback (required)
*	timeout: 0,   // number = milliseconds delay before onMouseOut function call
*	out: hideNav    // function = onMouseOut callback (required)
* });
* 
* @param  f  onMouseOver function || An object with configuration options
* @param  g  onMouseOut function  || Nothing (use configuration options object)
* @author    Brian Cherne <brian@cherne.net>
*/
(function($) {
	$.fn.hoverIntent = function(f,g) {
		// default configuration options
		var cfg = {
			sensitivity: 7,
			interval: 100,
			timeout: 0
		};
		// override configuration options with user supplied object
		cfg = $.extend(cfg, g ? { over: f, out: g } : f );

		// instantiate variables
		// cX, cY = current X and Y position of mouse, updated by mousemove event
		// pX, pY = previous X and Y position of mouse, set by mouseover and polling interval
		var cX, cY, pX, pY;

		// A private function for getting mouse position
		var track = function(ev) {
			cX = ev.pageX;
			cY = ev.pageY;
		};

		// A private function for comparing current and previous mouse position
		var compare = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			// compare mouse positions to see if they've crossed the threshold
			if ( ( Math.abs(pX-cX) + Math.abs(pY-cY) ) < cfg.sensitivity ) {
				$(ob).unbind("mousemove",track);
				// set hoverIntent state to true (so mouseOut can be called)
				ob.hoverIntent_s = 1;
				return cfg.over.apply(ob,[ev]);
			} else {
				// set previous coordinates for next time
				pX = cX; pY = cY;
				// use self-calling timeout, guarantees intervals are spaced out properly (avoids JavaScript timer bugs)
				ob.hoverIntent_t = setTimeout( function(){compare(ev, ob);} , cfg.interval );
			}
		};

		// A private function for delaying the mouseOut function
		var delay = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			ob.hoverIntent_s = 0;
			return cfg.out.apply(ob,[ev]);
		};

		// A private function for handling mouse 'hovering'
		var handleHover = function(e) {
			// copy objects to be passed into t (required for event object to be passed in IE)
			var ev = jQuery.extend({},e);
			var ob = this;

			// cancel hoverIntent timer if it exists
			if (ob.hoverIntent_t) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); }

			// if e.type == "mouseenter"
			if (e.type == "mouseenter") {
				// set "previous" X and Y position based on initial entry point
				pX = ev.pageX; pY = ev.pageY;
				// update "current" X and Y position based on mousemove
				$(ob).bind("mousemove",track);
				// start polling interval (self-calling timeout) to compare mouse coordinates over time
				if (ob.hoverIntent_s != 1) { ob.hoverIntent_t = setTimeout( function(){compare(ev,ob);} , cfg.interval );}

			// else e.type == "mouseleave"
			} else {
				// unbind expensive mousemove event
				$(ob).unbind("mousemove",track);
				// if hoverIntent state is true, then call the mouseOut function after the specified delay
				if (ob.hoverIntent_s == 1) { ob.hoverIntent_t = setTimeout( function(){delay(ev,ob);} , cfg.timeout );}
			}
		};

		// bind the function to the two event listeners
		return this.bind('mouseenter',handleHover).bind('mouseleave',handleHover);
	};
})(jQuery);
/*

jQuery Browser Plugin
	* Version 1.2.0
	* 2008-05-26 14:39:29
	* URL: http://jquery.thewikies.com/browser
	* Description: jQuery Browser Plugin extends browser detection capabilities and can implements CSS browser selectors.
	* Author: Nate Cavanaugh, Minhchau Dang, & Jonathan Neal
	* Copyright: Copyright (c) 2008 Jonathan Neal under dual MIT/GPL license.

*/

(function($) {

	// Define whether Browser Selectors will be added automatically; set as false to disable.
	var addSelectors = true;

	// Define Navigator Properties.
	var p = navigator.platform;
	var u = navigator.userAgent;
	var b = /(Firefox|Opera|Safari|KDE|iCab|Flock|IE)/.exec(u);
	var os = /(Win|Mac|Linux|iPhone|Sun|Solaris)/.exec(p);
	var versionDefaults = [0,0];

	b = (!b || !b.length) ? (/(Mozilla)/.exec(u) || ['']) : b;
	os = (!os || !os.length) ? [''] : os;

	// Define Browser Properties.
	var o = jQuery.extend($.browser, {

		// Define the rendering client
		gecko: /Gecko/.test(u) && !/like Gecko/.test(u),
		webkit: /WebKit/.test(u),

		// Define the browser
		aol: /America Online Browser/.test(u),
		camino: /Camino/.test(u),
		firefox: /Firefox/.test(u),
		flock: /Flock/.test(u),
		icab: /iCab/.test(u),
		konqueror: /KDE/.test(u),
		mozilla: /mozilla/.test(u),
		ie: /MSIE/.test(u),
		netscape: /Netscape/.test(u),
		opera: /Opera/.test(u),
		safari: /Safari/.test(u),
		browser: b[0].toLowerCase(),

		// Define the opperating system
		win: /Win/.test(p),
		mac: /Mac/.test(p),
		linux: /Linux/.test(p),
		iphone: /iPhone/.test(p),
		sun: /Solaris|SunOS/.test(p),
		os: os[0].toLowerCase(),

		// Define the classic navigator properties
		platform: p,
		agent: u,

		// Define the 'addSelectors' function which adds Browser Selectors to a tag; by default <HTML>.
		addSelectors: function(e) {
			jQuery(e || 'html').addClass(o.selectors).removeClass('nojs');
		},

		// Define the 'removeSelectors' function which removes Browser Selectors to a tag; by default <HTML>.
		removeSelectors: function(e) {
			jQuery(e || 'html').addClass(o.selectors);
		}

	});

	// Define the Browser Client Version.
	o.version = {
			string: (o.msie)
			? (/MSIE ([^;]+)/.exec(u) || versionDefaults)[1]
			: (o.firefox)
				? (/Firefox\/(.+)/.exec(u) || versionDefaults)[1]
				: (o.safari)
					? (/Version\/([^\s]+)/.exec(u) || versionDefaults)[1]
					: (o.opera)
						? (/Opera\/([^\s]+)/.exec(u) || versionDefaults)[1]
						: 'undefined' };
	o.version.number = parseFloat(o.version.string) || versionDefaults[0];
	o.version.major = /([^\.]+)/.exec(o.version.string)[1];

	// Define the Browser with Client Version.
	o[o.browser + o.version.major] = true;

	// Define the Rendering Client.
	o.renderer = (o.gecko) ? 'gecko' : (o.webkit) ? 'webkit' : '';

	// Define the selector.
	o.selectors = [o.renderer, o.browser, o.browser + o.version.major, o.os, 'js'].join(' ');

	// Run the 'addSelectors' Function if the 'addSelectors' Variable is set as true.
	if (addSelectors) o.addSelectors();

}(jQuery));
/*
+-----------------------------------------------------------------------+
| Copyright (c) 2006-2007 Mika Tuupola, Dylan Verheul                   |
| All rights reserved.                                                  |
|                                                                       |
| Redistribution and use in source and binary forms, with or without    |
| modification, are permitted provided that the following conditions    |
| are met:                                                              |
|                                                                       |
| o Redistributions of source code must retain the above copyright      |
|   notice, this list of conditions and the following disclaimer.       |
| o Redistributions in binary form must reproduce the above copyright   |
|   notice, this list of conditions and the following disclaimer in the |
|   documentation and/or other materials provided with the distribution.|
|                                                                       |
| THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS   |
| "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT     |
| LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR |
| A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT  |
| OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, |
| SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT      |
| LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, |
| DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY |
| THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT   |
| (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE |
| OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  |
|                                                                       |
+-----------------------------------------------------------------------+
*/

/* $Id$ */

/**
  * jQuery inplace editor plugin (version 1.3.x)
  *
  * Based on editable by Dylan Verheul <dylan@dyve.net>
  * http://www.dyve.net/jquery/?editable
  *
  * @name  jEditable
  * @type  jQuery
  * @param String  target             POST URL or function name to send edited content
  * @param Hash    options            additional options 
  * @param String  options[name]      POST parameter name of edited content
  * @param String  options[id]        POST parameter name of edited div id
  * @param String  options[type]      text, textarea or select
  * @param Integer options[rows]      number of rows if using textarea
  * @param Integer options[cols]      number of columns if using textarea
  * @param Mixed   options[height]    'auto' or height in pixels
  * @param Mixed   options[width]     'auto' or width in pixels 
  * @param String  options[loadurl]   URL to fetch content before editing
  * @param String  options[loadtype]  Request type for load url. Should be GET or POST.
  * @param String  options[data]      Or content given as paramameter.
  * @param String  options[indicator] indicator html to show when saving
  * @param String  options[tooltip]   optional tooltip text via title attribute
  * @param String  options[event]     jQuery event such as 'click' of 'dblclick'
  * @param String  options[onblur]    'cancel', 'submit' or 'ignore'
  * @param String  options[submit]    submit button value, empty means no button
  * @param String  options[cancel]    cancel button value, empty means no button
  * @param String  options[cssclass]  CSS class to apply to input form. 'inherit' to copy from parent.
  * @param String  options[style]     Style to apply to input form 'inherit' to copy from parent.
  * @param String  options[select]    true or false, when true text is highlighted
  *             
  */

jQuery.fn.editable = function(target, options, callback) {

    /* prevent elem has no properties error */
    if (this.length == 0) { 
        return(this); 
    };
    
    var settings = {
        target   : target,
        name     : 'value',
        id       : 'id',
        type     : 'text',
        width    : 'auto',
        height   : 'auto',
        event    : 'click',
        onblur   : 'cancel',
        loadtype : 'GET'
    };
        
    if(options) {
        jQuery.extend(settings, options);
    };
    
    var callback = callback || function() { };
      
    jQuery(this).attr('title', settings.tooltip);

    jQuery(this)[settings.event](function(e) {

        /* save this to self because this changes when scope changes */
        var self = this;

        /* prevent throwing an exeption if edit field is clicked again */
        if (self.editing) {
            return;
        }

        /* figure out how wide and tall we are */
        var width = 
            ('auto' == settings.width)  ? jQuery(self).width()  : settings.width;
        var height = 
            ('auto' == settings.height) ? jQuery(self).height() : settings.height;

        self.editing    = true;
        self.revert     = jQuery(self).html();
        self.innerHTML  = '';

        /* create the form object */
        var f = document.createElement('form');
        
        /* apply css or style or both */
        if (settings.cssclass) {
            if ('inherit' == settings.cssclass) {
                jQuery(f).attr('class', jQuery(self).attr('class'));
            } else {
                jQuery(f).attr('class', settings.cssclass);
            }
        }
        
        if (settings.style) {
            if ('inherit' == settings.style) {
                jQuery(f).attr('style', jQuery(self).attr('style'));
                /* IE needs the second line or display wont be inherited */
                jQuery(f).css('display', jQuery(self).css('display'));                
            } else {
                jQuery(f).attr('style', settings.style);
            }
        }
        
        /*  main input element */
        var i;
        switch (settings.type) {
            case 'textarea':
                i = document.createElement('textarea');
                if (settings.rows) {
                    i.rows = settings.rows;
                } else {
                    jQuery(i).height(height);
                }
                if (settings.cols) {
                    i.cols = settings.cols;
                } else {
                    jQuery(i).width(width);
                }   
                break;
            case 'select':
                i = document.createElement('select');
                break;
            default:
                i = document.createElement('input');
                i.type  = settings.type;
                jQuery(i).width(width);
                jQuery(i).height(height);
                /* https://bugzilla.mozilla.org/show_bug.cgi?id=236791 */
                i.setAttribute('autocomplete','off');
        }

        /* maintain bc with 1.1.1 and earlier versions */        
        if (settings.getload) {
            settings.loadurl    = settings.getload;
            settings.loadtype = 'GET';
        } else if (settings.postload) {
            settings.loadurl    = settings.postload;
            settings.loadtype = 'POST';
        }

        /* set input content via POST, GET, given data or existing value */
        if (settings.loadurl) {
            var data = {};
            data[settings.id] = self.id;
            jQuery.ajax({
               type : settings.loadtype,
               url  : settings.loadurl,
               data : data,
               success: function(str) {
                  setInputContent(str);
               }
            });
        } else if (settings.data) {
            setInputContent(settings.data);
        } else { 
            setInputContent(self.revert);
        }

        i.name  = settings.name;
        f.appendChild(i);

        if (settings.submit) {
            var b = document.createElement('input');
            b.type = 'submit';
            b.value = settings.submit;
            f.appendChild(b);
        }

        if (settings.cancel) {
            var b = document.createElement('input');
            b.type = 'button';
            b.value = settings.cancel;
            jQuery(b).click(function() {
                reset();
            });
            f.appendChild(b);
        }

        /* add created form to self */
        self.appendChild(f);

        i.focus();
        
        /* highlight input contents when requested */
        if (settings.select) {
            i.select();
        }
         
        /* discard changes if pressing esc */
        jQuery(i).keydown(function(e) {
            if (e.keyCode == 27) {
                e.preventDefault();
                reset();
            }
        });

        /* discard, submit or nothing with changes when clicking outside */
        /* do nothing is usable when navigating with tab */
        var t;
        if ('cancel' == settings.onblur) {
            jQuery(i).blur(function(e) {
                t = setTimeout(reset, 500)
            });
        } else if ('submit' == settings.onblur) {
            jQuery(i).blur(function(e) {
                jQuery(f).submit();
            });
        } else {
            jQuery(i).blur(function(e) {
              /* TODO: maybe something here */
            });
        }

        jQuery(f).submit(function(e) {

            if (t) { 
                clearTimeout(t);
            }

            /* do no submit */
            e.preventDefault(); 

            /* check if given target is function */
            if (jQuery.isFunction(settings.target)) {
                var str = settings.target.apply(self, [jQuery(i).val(), settings]);
                self.innerHTML = str;
                self.editing = false;
                callback.apply(self, [self.innerHTML, settings]);
            } else {
                /* add edited content and id of edited element to POST */           
                var p = {};
                p[i.name] = jQuery(i).val();
                p[settings.id] = self.id;

                /* show the saving indicator */
                jQuery(self).html(settings.indicator);
                jQuery.post(settings.target, p, function(str) {
                    self.innerHTML = str;
                    self.editing = false;
                    callback.apply(self, [self.innerHTML, settings]);
                });
            }
                        
            return false;
        });

        function reset() {
            self.innerHTML = self.revert;
            self.editing   = false;
        };
        
        function setInputContent(str) {
            if (jQuery.isFunction(str)) {
                var str = str.apply(self, [self.revert, settings]);
            }
            switch (settings.type) { 	 
                case 'select': 	 
                    if (String == str.constructor) { 	 
                        eval ("var json = " + str);
                        for (var key in json) {
                            if ('selected' == key) {
                                continue;
                            } 
                            o = document.createElement('option'); 	 
                            o.value = key;
                            var text = document.createTextNode(json[key]);
                            o.appendChild(text);
                            if (key == json['selected']) {
                                o.selected = true;
                            }
                            i.appendChild(o); 	 
                        }
                    } 	 
                    break; 	 
                default: 	 
                    i.value = str; 	 
                    break; 	 
            } 	 
        }

    });

    return(this);
};
(function ($) {
    var m = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        s = {
            'array': function (x) {
                var a = ['['], b, f, i, l = x.length, v;
                for (i = 0; i < l; i += 1) {
                    v = x[i];
                    f = s[typeof v];
                    if (f) {
                        v = f(v);
                        if (typeof v == 'string') {
                            if (b) {
                                a[a.length] = ',';
                            }
                            a[a.length] = v;
                            b = true;
                        }
                    }
                }
                a[a.length] = ']';
                return a.join('');
            },
            'boolean': function (x) {
                return String(x);
            },
            'null': function (x) {
                return "null";
            },
            'number': function (x) {
                return isFinite(x) ? String(x) : 'null';
            },
            'object': function (x) {
                if (x) {
                    if (x instanceof Array) {
                        return s.array(x);
                    }
                    var a = ['{'], b, f, i, v;
                    for (i in x) {
                        v = x[i];
                        f = s[typeof v];
                        if (f) {
                            v = f(v);
                            if (typeof v == 'string') {
                                if (b) {
                                    a[a.length] = ',';
                                }
                                a.push(s.string(i), ':', v);
                                b = true;
                            }
                        }
                    }
                    a[a.length] = '}';
                    return a.join('');
                }
                return 'null';
            },
            'string': function (x) {
                if (/["\\\x00-\x1f]/.test(x)) {
                    x = x.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                        var c = m[b];
                        if (c) {
                            return c;
                        }
                        c = b.charCodeAt();
                        return '\\u00' +
                            Math.floor(c / 16).toString(16) +
                            (c % 16).toString(16);
                    });
                }
                return '"' + x + '"';
            }
        };

	$.toJSON = function(v) {
		var f = isNaN(v) ? s[typeof v] : s['number'];
		if (f) return f(v);
	};
	
	$.parseJSON = function(v, safe) {
		if (safe === undefined) safe = $.parseJSON.safe;
		if (safe && !/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.test(v))
			return undefined;
		return eval('('+v+')');
	};
	
	$.parseJSON.safe = false;

})(jQuery);

/* Copyright (c) 2007 Brandon Aaron (brandon.aaron@gmail.com || http://brandonaaron.net)
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php) 
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *
 * Version: 1.0.2
 * Requires jQuery 1.1.3+
 * Docs: http://docs.jquery.com/Plugins/livequery
 */

(function($) {
	
$.extend($.fn, {
	livequery: function(type, fn, fn2) {
		var self = this, q;
		
		// Handle different call patterns
		if ($.isFunction(type))
			fn2 = fn, fn = type, type = undefined;
			
		// See if Live Query already exists
		$.each( $.livequery.queries, function(i, query) {
			if ( self.selector == query.selector && self.context == query.context &&
				type == query.type && (!fn || fn.$lqguid == query.fn.$lqguid) && (!fn2 || fn2.$lqguid == query.fn2.$lqguid) )
					// Found the query, exit the each loop
					return (q = query) && false;
		});
		
		// Create new Live Query if it wasn't found
		q = q || new $.livequery(this.selector, this.context, type, fn, fn2);
		
		// Make sure it is running
		q.stopped = false;
		
		// Run it
		$.livequery.run( q.id );
		
		// Contnue the chain
		return this;
	},
	
	expire: function(type, fn, fn2) {
		var self = this;
		
		// Handle different call patterns
		if ($.isFunction(type))
			fn2 = fn, fn = type, type = undefined;
			
		// Find the Live Query based on arguments and stop it
		$.each( $.livequery.queries, function(i, query) {
			if ( self.selector == query.selector && self.context == query.context && 
				(!type || type == query.type) && (!fn || fn.$lqguid == query.fn.$lqguid) && (!fn2 || fn2.$lqguid == query.fn2.$lqguid) && !this.stopped )
					$.livequery.stop(query.id);
		});
		
		// Continue the chain
		return this;
	}
});

$.livequery = function(selector, context, type, fn, fn2) {
	this.selector = selector;
	this.context  = context || document;
	this.type     = type;
	this.fn       = fn;
	this.fn2      = fn2;
	this.elements = [];
	this.stopped  = false;
	
	// The id is the index of the Live Query in $.livequery.queries
	this.id = $.livequery.queries.push(this)-1;
	
	// Mark the functions for matching later on
	fn.$lqguid = fn.$lqguid || $.livequery.guid++;
	if (fn2) fn2.$lqguid = fn2.$lqguid || $.livequery.guid++;
	
	// Return the Live Query
	return this;
};

$.livequery.prototype = {
	stop: function() {
		var query = this;
		
		if ( this.type )
			// Unbind all bound events
			this.elements.unbind(this.type, this.fn);
		else if (this.fn2)
			// Call the second function for all matched elements
			this.elements.each(function(i, el) {
				query.fn2.apply(el);
			});
			
		// Clear out matched elements
		this.elements = [];
		
		// Stop the Live Query from running until restarted
		this.stopped = true;
	},
	
	run: function() {
		// Short-circuit if stopped
		if ( this.stopped ) return;
		var query = this;
		
		var oEls = this.elements,
			els  = $(this.selector, this.context),
			nEls = els.not(oEls);
		
		// Set elements to the latest set of matched elements
		this.elements = els;
		
		if (this.type) {
			// Bind events to newly matched elements
			nEls.bind(this.type, this.fn);
			
			// Unbind events to elements no longer matched
			if (oEls.length > 0)
				$.each(oEls, function(i, el) {
					if ( $.inArray(el, els) < 0 )
						$.event.remove(el, query.type, query.fn);
				});
		}
		else {
			// Call the first function for newly matched elements
			nEls.each(function() {
				query.fn.apply(this);
			});
			
			// Call the second function for elements no longer matched
			if ( this.fn2 && oEls.length > 0 )
				$.each(oEls, function(i, el) {
					if ( $.inArray(el, els) < 0 )
						query.fn2.apply(el);
				});
		}
	}
};

$.extend($.livequery, {
	guid: 0,
	queries: [],
	queue: [],
	running: false,
	timeout: null,
	
	checkQueue: function() {
		if ( $.livequery.running && $.livequery.queue.length ) {
			var length = $.livequery.queue.length;
			// Run each Live Query currently in the queue
			while ( length-- )
				$.livequery.queries[ $.livequery.queue.shift() ].run();
		}
	},
	
	pause: function() {
		// Don't run anymore Live Queries until restarted
		$.livequery.running = false;
	},
	
	play: function() {
		// Restart Live Queries
		$.livequery.running = true;
		// Request a run of the Live Queries
		$.livequery.run();
	},
	
	registerPlugin: function() {
		$.each( arguments, function(i,n) {
			// Short-circuit if the method doesn't exist
			if (!$.fn[n]) return;
			
			// Save a reference to the original method
			var old = $.fn[n];
			
			// Create a new method
			$.fn[n] = function() {
				// Call the original method
				var r = old.apply(this, arguments);
				
				// Request a run of the Live Queries
				$.livequery.run();
				
				// Return the original methods result
				return r;
			}
		});
	},
	
	run: function(id) {
		if (id != undefined) {
			// Put the particular Live Query in the queue if it doesn't already exist
			if ( $.inArray(id, $.livequery.queue) < 0 )
				$.livequery.queue.push( id );
		}
		else
			// Put each Live Query in the queue if it doesn't already exist
			$.each( $.livequery.queries, function(id) {
				if ( $.inArray(id, $.livequery.queue) < 0 )
					$.livequery.queue.push( id );
			});
		
		// Clear timeout if it already exists
		if ($.livequery.timeout) clearTimeout($.livequery.timeout);
		// Create a timeout to check the queue and actually run the Live Queries
		$.livequery.timeout = setTimeout($.livequery.checkQueue, 20);
	},
	
	stop: function(id) {
		if (id != undefined)
			// Stop are particular Live Query
			$.livequery.queries[ id ].stop();
		else
			// Stop all Live Queries
			$.each( $.livequery.queries, function(id) {
				$.livequery.queries[ id ].stop();
			});
	}
});

// Register core DOM manipulation methods
$.livequery.registerPlugin('append', 'prepend', 'after', 'before', 'wrap', 'attr', 'removeAttr', 'addClass', 'removeClass', 'toggleClass', 'empty', 'remove');

// Run Live Queries when the Document is ready
$(function() { $.livequery.play(); });


// Save a reference to the original init method
var init = $.prototype.init;

// Create a new init method that exposes two new properties: selector and context
$.prototype.init = function(a,c) {
	// Call the original init and save the result
	var r = init.apply(this, arguments);
	
	// Copy over properties if they exist already
	if (a && a.selector)
		r.context = a.context, r.selector = a.selector;
		
	// Set properties
	if ( typeof a == 'string' )
		r.context = c || document, r.selector = a;
	
	// Return the result
	return r;
};

// Give the init function the jQuery prototype for later instantiation (needed after Rev 4091)
$.prototype.init.prototype = $.prototype;
	
})(jQuery);
/*
 * jQuery Media Plugin for converting elements into rich media content.
 *
 * Examples and documentation at: http://malsup.com/jquery/media/
 * Copyright (c) 2007 M. Alsup
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * @author: M. Alsup
 * @version: 0.66 (6/08/2007)
 * @requires jQuery v1.1.2 or later
 *
 * Supported Media Players:
 *    - Flash
 *    - Quicktime
 *    - Real Player
 *    - Silverlight
 *    - Windows Media Player
 *    - iframe
 *
 * Supported Media Formats:
 *   Any types supported by the above players, such as:
 *     Video: asf, avi, flv, mov, mpg, mpeg, mp4, qt, smil, swf, wmv, 3g2, 3gp
 *     Audio: aif, aac, au, gsm, mid, midi, mov, mp3, m4a, snd, rm, wav, wma
 *     Other: bmp, html, pdf, psd, qif, qtif, qti, tif, tiff, xaml
 *
 * Thanks to Mark Hicken for helping me debug this on Safari!
 */
(function($) {

/**
 * Chainable method for converting elements into rich media.
 *
 * @name media
 * @param Object options Options object
 * @param Function callback fn invoked for each matched element before conversion
 * @param Function callback fn invoked for each matched element after conversion
 * @cat Plugins/media
 */
$.fn.media = function(options, f1, f2) {
    return this.each(function() {
        if (typeof options == 'function') {
            f2 = f1;
            f1 = options;
            options = {};
        }
        var o = getSettings(this, options);
        // pre-conversion callback, passes original element and fully populated options
        if (typeof f1 == 'function') f1(this, o);
        
        var r = getTypesRegExp();
        var m = r.exec(o.src) || [''];
        o.type ? m[0] = o.type : m.shift();
        for (var i=0; i < m.length; i++) {
            fn = m[i].toLowerCase();
            if (isDigit(fn[0])) fn = 'fn' + fn; // fns can't begin with numbers
            if (!$.fn.media[fn]) 
                continue;  // unrecognized media type
            // normalize autoplay settings
            var player = $.fn.media[fn+'_player'];
            if (!o.params) o.params = {};
            if (player) {
                var num = player.autoplayAttr == 'autostart';
                o.params[player.autoplayAttr || 'autoplay'] = num ? (o.autoplay ? 1 : 0) : o.autoplay ? true : false;
            }
            var $div = $.fn.media[fn](this, o);

            $div.css('backgroundColor', o.bgColor).width(o.width);
            
            // post-conversion callback, passes original element, new div element and fully populated options
            if (typeof f2 == 'function') f2(this, $div[0], o);
            break;
        }
    });
};

/**
 * Chainable method for preparing elements to display rich media with
 * a page overlay.
 *
 * @name mediabox
 * @param Object options Options object
 * @param Object css values for the media div
 * @cat Plugins/media
 */
$.fn.mediabox = function(options, css) {
    return this.click(function() {
        if (typeof $.blockUI == 'undefined' || typeof $.fn.displayBox == 'undefined') {
            if (typeof $.fn.mediabox.warning != 'undefined') return this; // one warning is enough
            $.fn.mediabox.warning = 1;
            alert('The mediabox method requires blockUI v1.20 or later.');
            return false;
        }
        var o, div=0, $e = $(this).clone();
        $e.appendTo('body').hide().css({margin: 0});
        options = $.extend({}, options, { autoplay: 1 }); // force autoplay in box mode
        $e.media(options, function(){}, function(origEl, newEl, opts) {
            div = newEl;
            o = opts;
        });
        if (!div) return false;
        // don't pull element from the dom on Safari
        var $div = $.browser.safari ? $(div).hide() : $(div).remove();

        if (o.loadingImage)
            $div.css({
                backgroundImage:    'url('+o.loadingImage+')',
                backgroundPosition: 'center center',
                backgroundRepeat:   'no-repeat'
            });
        if (o.boxTitle)
            $div.prepend('<div style="margin:0;padding:0">' + o.boxTitle + '</div>');
        
        if (css) $div.css(css);

        $div.displayBox( { width: o.width, height: o.height }, function(el) {
            // quirkiness; sometimes media doesn't stop when removed from the DOM (especially in IE)
            $(el).find('object,embed').each(function() {
                try { this.Stop();   } catch(e) {}  // quicktime
                try { this.DoStop(); } catch(e) {}  // real
                try { this.controls.stop(); } catch(e) {} // windows media player
            });
        });
        return false;
    });
};

  
/**
 * Non-chainable method for adding or changing file format / player mapping
 * @name mapFormat
 * @param String format File format extension (ie: mov, wav, mp3)
 * @param String player Player name to use for the format (one of: flash, quicktime, realplayer, winmedia, silverlight or iframe
 */
$.fn.media.mapFormat = function(format, player) {
    if (!format || !player || !$.fn.media.defaults.players[player]) return; // invalid
    format = format.toLowerCase();
    if (isDigit(format[0])) format = 'fn' + format;
    $.fn.media[format] = $.fn.media[player];
};


// global defautls; override as needed
$.fn.media.defaults = {
    width:         400,
    height:        400,
    preferMeta:    1,         // true if markup metadata takes precedence over options object
    autoplay:      0,         // normalized cross-player setting
    bgColor:       '#ffffff', // background color
    params:        {},        // added to object element as param elements; added to embed element as attrs
    attrs:         {},        // added to object and embed elements as attrs
    flashvars:     {},        // added to flash content as flashvars param/attr
    flashVersion:  '7',       // required flash version
    
    // MediaBox options
    boxTitle:      null,      // MediaBox titlebar
    loadingImage:  null,      // MediaBox loading indicator
    
    // default flash video and mp3 player (@see: http://jeroenwijering.com/?item=Flash_Media_Player)
    flvPlayer:     'mediaplayer.swf',
    mp3Player:     'mediaplayer.swf',
    
    // @see http://msdn2.microsoft.com/en-us/library/bb412401.aspx
    silverlight: {
        inplaceInstallPrompt: 'true', // display in-place install prompt?
        isWindowless:         'true', // windowless mode (false for wrapping markup)
        framerate:            '24',   // maximum framerate
        version:              '0.9',  // Silverlight version
        onError:              null,   // onError callback
        onLoad:               null,   // onLoad callback
        initParams:           null,   // object init params
        userContext:          null    // callback arg passed to the load callback
    }
};

// Media Players; think twice before overriding
$.fn.media.defaults.players = {
    flash: {
        types:        'flv,mp3,swf',
        oAttrs:   {
            classid:  'clsid:d27cdb6e-ae6d-11cf-96b8-444553540000',
            type:     'application/x-oleobject',
            codebase: 'http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=' + $.fn.media.defaults.flashVersion
        },
        eAttrs: {
            type:         'application/x-shockwave-flash',
            pluginspage:  'http://www.adobe.com/go/getflashplayer'
        }        
    },
    quicktime: {
        types:        'aif,aiff,aac,au,bmp,gsm,mov,mid,midi,mpg,mpeg,mp4,m4a,psd,qt,qtif,qif,qti,snd,tif,tiff,wav,3g2,3gp',
        oAttrs:   {
            classid:  'clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B',
            codebase: 'http://www.apple.com/qtactivex/qtplugin.cab'
        },
        eAttrs: {
            pluginspage:  'http://www.apple.com/quicktime/download/'
        }
    },
    realplayer: {
        types:        'ra,ram,rm,rpm,rv,smi,smil',
        autoplayAttr: 'autostart',
        oAttrs:   {
            classid:  'clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA'
        },
        eAttrs: {
            type:         'audio/x-pn-realaudio-plugin',
            pluginspage:  'http://www.real.com/player/'
        }
    },
    winmedia: {
        types:        'asf,avi,wma,wmv',
        autoplayAttr: 'autostart',
        oUrl:         'url',
        oAttrs:   {
            classid:  'clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6',
            type:     'application/x-oleobject'
        },
        eAttrs: {
            type:         'application/x-mplayer2',
            pluginspage:  'http://www.microsoft.com/Windows/MediaPlayer/'
        }        
    },
    // special cases
    iframe: {
        types: 'html,pdf'
    },
    silverlight: {
        types: 'xaml'
    }
};

//
//  everything below here is private
//


var counter = 1;

for (var player in $.fn.media.defaults.players) {
    var types = $.fn.media.defaults.players[player].types;
    $.each(types.split(','), function(i,o) {
        if (isDigit(o[0])) o = 'fn' + o;
        $.fn.media[o] = $.fn.media[player] = getGenerator(player);
        $.fn.media[o+'_player'] = $.fn.media.defaults.players[player];
    });
};

function getTypesRegExp() {
    var types = '';
    for (var player in $.fn.media.defaults.players) {
        if (types.length) types += ',';
        types += $.fn.media.defaults.players[player].types;
    };
    return new RegExp('\\.(' + types.replace(/,/g,'|') + ')\\b');
};

function getGenerator(player) {
    return function(el, options) {
        return generate(el, options, player);
    };
};

function isDigit(c) {
    return '0123456789'.indexOf(c) > -1;
};

// flatten all possible options: global defaults, meta, option obj
function getSettings(el, options) {
    options = options || {};
    var $el = $(el);
    
    var cls = el.className || '';
    var meta = $.meta ? $el.data() : {};
    var w = meta.width  || parseInt(((cls.match(/w:(\d+)/)||[])[1]||0));
    var h = meta.height || parseInt(((cls.match(/h:(\d+)/)||[])[1]||0));
    if (w) meta.width  = w;
    if (h) meta.height = h;
    if (cls) meta.cls = cls;

    var a = $.fn.media.defaults;
    var b = $.meta && $.fn.media.defaults.preferMeta ? options : meta;
    var c = b == options ? meta : options;

    var p = { params: { bgColor: options.bgColor || $.fn.media.defaults.bgColor } };
    var opts = $.extend({}, a, b, c);
    $.each(['attrs','params','flashvars','silverlight'], function(i,o) {
        opts[o] = $.extend({}, p[o] || {}, a[o] || {}, b[o] || {}, c[o] || {});
    });

    if (typeof opts.caption == 'undefined') opts.caption = $el.text();

    // make sure we have a source!
    opts.src = opts.src || $el.attr('href') || $el.attr('src') || 'unknown';
    return opts;
};

//
//  Flash Player
//

// generate flash using SWFObject if possible
$.fn.media.swf = function(el, opts) {
    if (typeof SWFObject == 'undefined') {
        // roll our own
        if (opts.flashvars) {
            var a = [];
            for (var f in opts.flashvars)
                a.push(f + '=' + opts.flashvars[f]);
            if (!opts.params) opts.params = {};
            opts.params.flashvars = a.join('&');
        }
        return generate(el, opts, 'flash');
    }

    var id = el.id ? (' id="'+el.id+'"') : '';
    var cls = opts.cls ? (' class="' + opts.cls + '"') : '';
    var $div = $('<div' + id + cls + '>');
    $(el).after($div).remove();

    var so = new SWFObject(opts.src, 'movie_player_' + counter++, opts.width, opts.height, opts.flashVersion, opts.bgColor);
    for (var p in opts.params)
        if (p != 'bgColor') so.addParam(p, opts.params[p]);
    for (var f in opts.flashvars)
        so.addVariable(f, opts.flashvars[f]);
    so.write($div[0]);

    if (opts.caption) $('<div>').appendTo($div).html(opts.caption);
    return $div;
};

// map flv and mp3 files to the swf player by default
$.fn.media.flv = $.fn.media.mp3 = function(el, opts) {
    var src = opts.src;
    var player = /\.mp3\b/i.test(src) ? $.fn.media.defaults.mp3Player : $.fn.media.defaults.flvPlayer;
    opts.src = player;
    opts.src = opts.src + '?file=' + src;
    opts.flashvars = $.extend({}, { file: src }, opts.flashvars );
    return $.fn.media.swf(el, opts);
};

//
//  Silverlight
//
$.fn.media.xaml = function(el, opts) {
    if (!window.Sys || !window.Sys.Silverlight) {
        if ($.fn.media.xaml.warning) return;
        $.fn.media.xaml.warning = 1;
        alert('You must include the Silverlight.js script.');
        return;
    }

    var props = {
        width: opts.width,
        height: opts.height,
        background: opts.bgColor,
        inplaceInstallPrompt: opts.silverlight.inplaceInstallPrompt,
        isWindowless: opts.silverlight.isWindowless,
        framerate: opts.silverlight.framerate,
        version: opts.silverlight.version
    };
    var events = {
        onError: opts.silverlight.onError,
        onLoad: opts.silverlight.onLoad
    };

    var id1 = el.id ? (' id="'+el.id+'"') : '';
    var id2 = opts.id || 'AG' + counter++;
    // convert element to div
    var cls = opts.cls ? (' class="' + opts.cls + '"') : '';
    var $div = $('<div' + id1 + cls + '>');
    $(el).after($div).remove();
    
    Sys.Silverlight.createObjectEx({
        source: opts.src,
        initParams: opts.silverlight.initParams,
        userContext: opts.silverlight.userContext,
        id: id2,
        parentElement: $div[0],
        properties: props,
        events: events
    });

    if (opts.caption) $('<div>').appendTo($div).html(opts.caption);
    return $div;
};

//
// generate object/embed markup
//
function generate(el, opts, player) {
    var $el = $(el);
    var o = $.fn.media.defaults.players[player];
    
    if (player == 'iframe') {
        var o = $('<iframe' + ' width="' + opts.width + '" height="' + opts.height + '" >');
        o.attr('src', opts.src);
        o.css('backgroundColor', o.bgColor);
    }
    else if ($.browser.msie) {
        var a = ['<object width="' + opts.width + '" height="' + opts.height + '" '];
        for (var key in opts.attrs)
            a.push(key + '="'+opts.attrs[key]+'" ');
        for (var key in o.oAttrs || {})
            a.push(key + '="'+o.oAttrs[key]+'" ');
        a.push('></ob'+'ject'+'>');
        var p = ['<param name="' + (o.oUrl || 'src') +'" value="' + opts.src + '">'];
        for (var key in opts.params)
            p.push('<param name="'+ key +'" value="' + opts.params[key] + '">');
        var o = document.createElement(a.join(''));
        for (var i=0; i < p.length; i++)
            o.appendChild(document.createElement(p[i]));
    }
    else {
        var a = ['<embed width="' + opts.width + '" height="' + opts.height + '" style="display:block"'];
        if (opts.src) a.push(' src="' + opts.src + '" ');
        for (var key in opts.attrs)
            a.push(key + '="'+opts.attrs[key]+'" ');
        for (var key in o.eAttrs || {})
            a.push(key + '="'+o.eAttrs[key]+'" ');
        for (var key in opts.params)
            a.push(key + '="'+opts.params[key]+'" ');
        a.push('></em'+'bed'+'>');
    }
    // convert element to div
    var id = el.id ? (' id="'+el.id+'"') : '';
    var cls = opts.cls ? (' class="' + opts.cls + '"') : '';
    var $div = $('<div' + id + cls + '>');
    $el.after($div).remove();
    ($.browser.msie || player == 'iframe') ? $div.append(o) : $div.html(a.join(''));
    if (opts.caption) $('<div>').appendTo($div).html(opts.caption);
    return $div;
};


})(jQuery);

/*
 * jQuery UI @VERSION
 *
 * Copyright (c) 2008 Paul Bakaus (ui.jquery.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://docs.jquery.com/UI
 */
;(function($) {

// This adds a selector to check if data exists.
jQuery.extend(jQuery.expr[':'], { 
	data: "jQuery.data(a, m[3])"
});

$.ui = {
	plugin: {
		add: function(module, option, set) {
			var proto = $.ui[module].prototype;
			for(var i in set) {
				proto.plugins[i] = proto.plugins[i] || [];
				proto.plugins[i].push([option, set[i]]);
			}
		},
		call: function(instance, name, args) {
			var set = instance.plugins[name];
			if(!set) { return; }
			
			for (var i = 0; i < set.length; i++) {
				if (instance.options[set[i][0]]) {
					set[i][1].apply(instance.element, args);
				}
			}
		}	
	},
	cssCache: {},
	css: function(name) {
		if ($.ui.cssCache[name]) { return $.ui.cssCache[name]; }
		var tmp = $('<div class="ui-gen">').addClass(name).css({position:'absolute', top:'-5000px', left:'-5000px', display:'block'}).appendTo('body');
		
		//if (!$.browser.safari)
			//tmp.appendTo('body'); 
		
		//Opera and Safari set width and height to 0px instead of auto
		//Safari returns rgba(0,0,0,0) when bgcolor is not set
		$.ui.cssCache[name] = !!(
			(!(/auto|default/).test(tmp.css('cursor')) || (/^[1-9]/).test(tmp.css('height')) || (/^[1-9]/).test(tmp.css('width')) || 
			!(/none/).test(tmp.css('backgroundImage')) || !(/transparent|rgba\(0, 0, 0, 0\)/).test(tmp.css('backgroundColor')))
		);
		try { $('body').get(0).removeChild(tmp.get(0));	} catch(e){}
		return $.ui.cssCache[name];
	},
	disableSelection: function(el) {
		$(el).attr('unselectable', 'on').css('MozUserSelect', 'none');
	},
	enableSelection: function(el) {
		$(el).attr('unselectable', 'off').css('MozUserSelect', '');
	},
	hasScroll: function(e, a) {
		var scroll = (a && a == 'left') ? 'scrollLeft' : 'scrollTop',
			has = false;
		
		if (e[scroll] > 0) { return true; }
		
		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		e[scroll] = 1;
		has = (e[scroll] > 0);
		e[scroll] = 0;
		return has;
	}
};


/** jQuery core modifications and additions **/

var _remove = $.fn.remove;
$.fn.remove = function() {
	$("*", this).add(this).triggerHandler("remove");
	return _remove.apply(this, arguments );
};

// $.widget is a factory to create jQuery plugins
// taking some boilerplate code out of the plugin code
// created by Scott González and Jörn Zaefferer
function getter(namespace, plugin, method) {
	var methods = $[namespace][plugin].getter || [];
	methods = (typeof methods == "string" ? methods.split(/,?\s+/) : methods);
	return ($.inArray(method, methods) != -1);
}

$.widget = function(name, prototype) {
	var namespace = name.split(".")[0];
	name = name.split(".")[1];
	
	// create plugin method
	$.fn[name] = function(options) {
		var isMethodCall = (typeof options == 'string'),
			args = Array.prototype.slice.call(arguments, 1);
		
		if (isMethodCall && getter(namespace, name, options)) {
			var instance = $.data(this[0], name);
			return (instance ? instance[options].apply(instance, args)
				: undefined);
		}
		
		return this.each(function() {
			var instance = $.data(this, name);
			if (isMethodCall && instance && $.isFunction(instance[options])) {
				instance[options].apply(instance, args);
			} else if (!isMethodCall) {
				$.data(this, name, new $[namespace][name](this, options));
			}
		});
	};
	
	// create widget constructor
	$[namespace][name] = function(element, options) {
		var self = this;
		
		this.widgetName = name;
		this.widgetEventPrefix = $[namespace][name].eventPrefix || name;
		this.widgetBaseClass = namespace + '-' + name;
		
		this.options = $.extend({}, $.widget.defaults, $[namespace][name].defaults, options);
		this.element = $(element)
			.bind('setData.' + name, function(e, key, value) {
				return self.setData(key, value);
			})
			.bind('getData.' + name, function(e, key) {
				return self.getData(key);
			})
			.bind('remove', function() {
				return self.destroy();
			});
		this.init();
	};
	
	// add widget prototype
	$[namespace][name].prototype = $.extend({}, $.widget.prototype, prototype);
};

$.widget.prototype = {
	init: function() {},
	destroy: function() {
		this.element.removeData(this.widgetName);
	},
	
	getData: function(key) {
		return this.options[key];
	},
	setData: function(key, value) {
		this.options[key] = value;
		
		if (key == 'disabled') {
			this.element[value ? 'addClass' : 'removeClass'](
				this.widgetBaseClass + '-disabled');
		}
	},
	
	enable: function() {
		this.setData('disabled', false);
	},
	disable: function() {
		this.setData('disabled', true);
	},
	
	trigger: function(type, e, data) {
		var eventName = (type == this.widgetEventPrefix
			? type : this.widgetEventPrefix + type);
		e = e  || $.event.fix({ type: eventName, target: this.element[0] });
		return this.element.triggerHandler(eventName, [e, data], this.options[type]);
	}
};

$.widget.defaults = {
	disabled: false
};


/** Mouse Interaction Plugin **/

$.ui.mouse = {
	mouseInit: function() {
		var self = this;
	
		this.element.bind('mousedown.'+this.widgetName, function(e) {
			return self.mouseDown(e);
		});
		
		// Prevent text selection in IE
		if ($.browser.msie) {
			this._mouseUnselectable = this.element.attr('unselectable');
			this.element.attr('unselectable', 'on');
		}
		
		this.started = false;
	},
	
	// TODO: make sure destroying one instance of mouse doesn't mess with
	// other instances of mouse
	mouseDestroy: function() {
		this.element.unbind('.'+this.widgetName);
		
		// Restore text selection in IE
		($.browser.msie
			&& this.element.attr('unselectable', this._mouseUnselectable));
	},
	
	mouseDown: function(e) {
		// we may have missed mouseup (out of window)
		(this._mouseStarted && this.mouseUp(e));
		
		this._mouseDownEvent = e;
		
		var self = this,
			btnIsLeft = (e.which == 1),
			elIsCancel = (typeof this.options.cancel == "string" ? $(e.target).parents().add(e.target).filter(this.options.cancel).length : false);
		if (!btnIsLeft || elIsCancel || !this.mouseCapture(e)) {
			return true;
		}
		
		this._mouseDelayMet = !this.options.delay;
		if (!this._mouseDelayMet) {
			this._mouseDelayTimer = setTimeout(function() {
				self._mouseDelayMet = true;
			}, this.options.delay);
		}
		
		if (this.mouseDistanceMet(e) && this.mouseDelayMet(e)) {
			this._mouseStarted = (this.mouseStart(e) !== false);
			if (!this._mouseStarted) {
				e.preventDefault();
				return true;
			}
		}
		
		// these delegates are required to keep context
		this._mouseMoveDelegate = function(e) {
			return self.mouseMove(e);
		};
		this._mouseUpDelegate = function(e) {
			return self.mouseUp(e);
		};
		$(document)
			.bind('mousemove.'+this.widgetName, this._mouseMoveDelegate)
			.bind('mouseup.'+this.widgetName, this._mouseUpDelegate);
		
		return false;
	},
	
	mouseMove: function(e) {
		// IE mouseup check - mouseup happened when mouse was out of window
		if ($.browser.msie && !e.button) {
			return this.mouseUp(e);
		}
		
		if (this._mouseStarted) {
			this.mouseDrag(e);
			return false;
		}
		
		if (this.mouseDistanceMet(e) && this.mouseDelayMet(e)) {
			this._mouseStarted =
				(this.mouseStart(this._mouseDownEvent, e) !== false);
			(this._mouseStarted ? this.mouseDrag(e) : this.mouseUp(e));
		}
		
		return !this._mouseStarted;
	},
	
	mouseUp: function(e) {
		$(document)
			.unbind('mousemove.'+this.widgetName, this._mouseMoveDelegate)
			.unbind('mouseup.'+this.widgetName, this._mouseUpDelegate);
		
		if (this._mouseStarted) {
			this._mouseStarted = false;
			this.mouseStop(e);
		}
		
		return false;
	},
	
	mouseDistanceMet: function(e) {
		return (Math.max(
				Math.abs(this._mouseDownEvent.pageX - e.pageX),
				Math.abs(this._mouseDownEvent.pageY - e.pageY)
			) >= this.options.distance
		);
	},
	
	mouseDelayMet: function(e) {
		return this._mouseDelayMet;
	},
	
	// These are placeholder methods, to be overriden by extending plugin
	mouseStart: function(e) {},
	mouseDrag: function(e) {},
	mouseStop: function(e) {},
	mouseCapture: function(e) { return true; }
};

$.ui.mouse.defaults = {
	cancel: null,
	distance: 1,
	delay: 0
};

})(jQuery);

/*
 * jQuery UI Accordion
 * 
 * Copyright (c) 2007, 2008 Jörn Zaefferer
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://docs.jquery.com/UI/Accordion
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.widget("ui.accordion", {
	init: function() {
		var options = this.options;
		
		if ( options.navigation ) {
			var current = this.element.find("a").filter(options.navigationFilter);
			if ( current.length ) {
				if ( current.filter(options.header).length ) {
					options.active = current;
				} else {
					options.active = current.parent().parent().prev();
					current.addClass("current");
				}
			}
		}
		
		// calculate active if not specified, using the first header
		options.headers = this.element.find(options.header);
		options.active = findActive(options.headers, options.active);
		
		// IE7-/Win - Extra vertical space in Lists fixed
		if ($.browser.msie) {
			this.element.find('a').css('zoom', '1');
		}
		
		if (!this.element.hasClass("ui-accordion")) {
			this.element.addClass("ui-accordion");
			$("<span class='ui-accordion-left'/>").insertBefore(options.headers);
			$("<span class='ui-accordion-right'/>").appendTo(options.headers);
			options.headers.addClass("ui-accordion-header").attr("tabindex", "0");
		}
		
		var maxHeight;
		if ( options.fillSpace ) {
			maxHeight = this.element.parent().height();
			options.headers.each(function() {
				maxHeight -= $(this).outerHeight();
			});
			var maxPadding = 0;
			options.headers.next().each(function() {
				maxPadding = Math.max(maxPadding, $(this).innerHeight() - $(this).height());
			}).height(maxHeight - maxPadding);
		} else if ( options.autoHeight ) {
			maxHeight = 0;
			options.headers.next().each(function() {
				maxHeight = Math.max(maxHeight, $(this).outerHeight());
			}).height(maxHeight);
		}
	
		options.headers
			.not(options.active || "")
			.next()
			.hide();
		options.active.parent().andSelf().addClass(options.selectedClass);
		
		if (options.event) {
			this.element.bind((options.event) + ".accordion", clickHandler);
		}
	},
	activate: function(index) {
		// call clickHandler with custom event
		clickHandler.call(this.element[0], {
			target: findActive( this.options.headers, index )[0]
		});
	},
	destroy: function() {
		this.options.headers.next().css("display", "");
		if ( this.options.fillSpace || this.options.autoHeight ) {
			this.options.headers.next().css("height", "");
		}
		$.removeData(this.element[0], "accordion");
		this.element.removeClass("ui-accordion").unbind(".accordion");
	}
});

function scopeCallback(callback, scope) {
	return function() {
		return callback.apply(scope, arguments);
	};
};

function completed(cancel) {
	// if removed while animated data can be empty
	if (!$.data(this, "accordion")) {
		return;
	}
	
	var instance = $.data(this, "accordion");
	var options = instance.options;
	options.running = cancel ? 0 : --options.running;
	if ( options.running ) {
		return;
	}
	if ( options.clearStyle ) {
		options.toShow.add(options.toHide).css({
			height: "",
			overflow: ""
		});
	}
	instance.trigger('change', null, options.data);
}

function toggle(toShow, toHide, data, clickedActive, down) {
	var options = $.data(this, "accordion").options;
	options.toShow = toShow;
	options.toHide = toHide;
	options.data = data;
	var complete = scopeCallback(completed, this);
	
	// count elements to animate
	options.running = toHide.size() === 0 ? toShow.size() : toHide.size();
	
	if ( options.animated ) {
		if ( !options.alwaysOpen && clickedActive ) {
			$.ui.accordion.animations[options.animated]({
				toShow: jQuery([]),
				toHide: toHide,
				complete: complete,
				down: down,
				autoHeight: options.autoHeight
			});
		} else {
			$.ui.accordion.animations[options.animated]({
				toShow: toShow,
				toHide: toHide,
				complete: complete,
				down: down,
				autoHeight: options.autoHeight
			});
		}
	} else {
		if ( !options.alwaysOpen && clickedActive ) {
			toShow.toggle();
		} else {
			toHide.hide();
			toShow.show();
		}
		complete(true);
	}
}

function clickHandler(event) {
	var options = $.data(this, "accordion").options;
	if (options.disabled) {
		return false;
	}
	
	// called only when using activate(false) to close all parts programmatically
	if ( !event.target && !options.alwaysOpen ) {
		options.active.parent().andSelf().toggleClass(options.selectedClass);
		var toHide = options.active.next(),
			data = {
				options: options,
				newHeader: jQuery([]),
				oldHeader: options.active,
				newContent: jQuery([]),
				oldContent: toHide
			},
			toShow = (options.active = $([]));
		toggle.call(this, toShow, toHide, data );
		return false;
	}
	// get the click target
	var clicked = $(event.target);
	
	// due to the event delegation model, we have to check if one
	// of the parent elements is our actual header, and find that
	// otherwise stick with the initial target
	clicked = $( clicked.parents(options.header)[0] || clicked );
	
	var clickedActive = clicked[0] == options.active[0];
	
	// if animations are still active, or the active header is the target, ignore click
	if (options.running || (options.alwaysOpen && clickedActive)) {
		return false;
	}
	if (!clicked.is(options.header)) {
		return;
	}
	
	// switch classes
	options.active.parent().andSelf().toggleClass(options.selectedClass);
	if ( !clickedActive ) {
		clicked.parent().andSelf().addClass(options.selectedClass);
	}
	
	// find elements to show and hide
	var toShow = clicked.next(),
		toHide = options.active.next(),
		data = {
			options: options,
			newHeader: clickedActive && !options.alwaysOpen ? $([]) : clicked,
			oldHeader: options.active,
			newContent: clickedActive && !options.alwaysOpen ? $([]) : toShow,
			oldContent: toHide
		},
		down = options.headers.index( options.active[0] ) > options.headers.index( clicked[0] );
	
	options.active = clickedActive ? $([]) : clicked;
	toggle.call(this, toShow, toHide, data, clickedActive, down );

	return false;
};

function findActive(headers, selector) {
	return selector
		? typeof selector == "number"
			? headers.filter(":eq(" + selector + ")")
			: headers.not(headers.not(selector))
		: selector === false
			? $([])
			: headers.filter(":eq(0)");
}

$.extend($.ui.accordion, {
	defaults: {
		selectedClass: "selected",
		alwaysOpen: true,
		animated: 'slide',
		event: "click",
		header: "a",
		autoHeight: true,
		running: 0,
		navigationFilter: function() {
			return this.href.toLowerCase() == location.href.toLowerCase();
		}
	},
	animations: {
		slide: function(options, additions) {
			options = $.extend({
				easing: "swing",
				duration: 300
			}, options, additions);
			if ( !options.toHide.size() ) {
				options.toShow.animate({height: "show"}, options);
				return;
			}
			var hideHeight = options.toHide.height(),
				showHeight = options.toShow.height(),
				difference = showHeight / hideHeight;
			options.toShow.css({ height: 0, overflow: 'hidden' }).show();
			options.toHide.filter(":hidden").each(options.complete).end().filter(":visible").animate({height:"hide"},{
				step: function(now) {
					var current = (hideHeight - now) * difference;
					if ($.browser.msie || $.browser.opera) {
						current = Math.ceil(current);
					}
					options.toShow.height( current );
				},
				duration: options.duration,
				easing: options.easing,
				complete: function() {
					if ( !options.autoHeight ) {
						options.toShow.css("height", "auto");
					}
					options.complete();
				}
			});
		},
		bounceslide: function(options) {
			this.slide(options, {
				easing: options.down ? "bounceout" : "swing",
				duration: options.down ? 1000 : 200
			});
		},
		easeslide: function(options) {
			this.slide(options, {
				easing: "easeinout",
				duration: 700
			});
		}
	}
});

// deprecated, use accordion("activate", index) instead
$.fn.activate = function(index) {
	return this.accordion("activate", index);
};

})(jQuery);

/*
 * jQuery UI Datepicker
 *
 * Copyright (c) 2006, 2007, 2008 Marc Grabanski
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Datepicker
 *
 * Depends:
 *	ui.core.js
 *
 * Marc Grabanski (m@marcgrabanski.com) and Keith Wood (kbwood@virginbroadband.com.au).
 */
   
(function($) { // hide the namespace

var PROP_NAME = 'datepicker';

/* Date picker manager.
   Use the singleton instance of this class, $.datepicker, to interact with the date picker.
   Settings for (groups of) date pickers are maintained in an instance object,
   allowing multiple different settings on the same page. */

function Datepicker() {
	this.debug = false; // Change this to true to start debugging
	this._curInst = null; // The current instance in use
	this._disabledInputs = []; // List of date picker inputs that have been disabled
	this._datepickerShowing = false; // True if the popup picker is showing , false if not
	this._inDialog = false; // True if showing within a "dialog", false if not
	this._mainDivId = 'ui-datepicker-div'; // The ID of the main datepicker division
	this._inlineClass = 'ui-datepicker-inline'; // The name of the inline marker class
	this._appendClass = 'ui-datepicker-append'; // The name of the append marker class
	this._triggerClass = 'ui-datepicker-trigger'; // The name of the trigger marker class
	this._dialogClass = 'ui-datepicker-dialog'; // The name of the dialog marker class
	this._promptClass = 'ui-datepicker-prompt'; // The name of the dialog prompt marker class
	this._disableClass = 'ui-datepicker-disabled'; // The name of the disabled covering marker class
	this._unselectableClass = 'ui-datepicker-unselectable'; // The name of the unselectable cell marker class
	this._currentClass = 'ui-datepicker-current-day'; // The name of the current day marker class
	this.regional = []; // Available regional settings, indexed by language code
	this.regional[''] = { // Default regional settings
		clearText: 'Clear', // Display text for clear link
		clearStatus: 'Erase the current date', // Status text for clear link
		closeText: 'Close', // Display text for close link
		closeStatus: 'Close without change', // Status text for close link
		prevText: '&#x3c;Prev', // Display text for previous month link
		prevStatus: 'Show the previous month', // Status text for previous month link
		prevBigText: '&#x3c;&#x3c;', // Display text for previous year link
		prevBigStatus: 'Show the previous year', // Status text for previous year link
		nextText: 'Next&#x3e;', // Display text for next month link
		nextStatus: 'Show the next month', // Status text for next month link
		nextBigText: '&#x3e;&#x3e;', // Display text for next year link
		nextBigStatus: 'Show the next year', // Status text for next year link
		currentText: 'Today', // Display text for current month link
		currentStatus: 'Show the current month', // Status text for current month link
		monthNames: ['January','February','March','April','May','June',
			'July','August','September','October','November','December'], // Names of months for drop-down and formatting
		monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'], // For formatting
		monthStatus: 'Show a different month', // Status text for selecting a month
		yearStatus: 'Show a different year', // Status text for selecting a year
		weekHeader: 'Wk', // Header for the week of the year column
		weekStatus: 'Week of the year', // Status text for the week of the year column
		dayNames: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'], // For formatting
		dayNamesShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'], // For formatting
		dayNamesMin: ['Su','Mo','Tu','We','Th','Fr','Sa'], // Column headings for days starting at Sunday
		dayStatus: 'Set DD as first week day', // Status text for the day of the week selection
		dateStatus: 'Select DD, M d', // Status text for the date selection
		dateFormat: 'mm/dd/yy', // See format options on parseDate
		firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
		initStatus: 'Select a date', // Initial Status text on opening
		isRTL: false // True if right-to-left language, false if left-to-right
	};
	this._defaults = { // Global defaults for all the date picker instances
		showOn: 'focus', // 'focus' for popup on focus,
			// 'button' for trigger button, or 'both' for either
		showAnim: 'show', // Name of jQuery animation for popup
		showOptions: {}, // Options for enhanced animations
		defaultDate: null, // Used when field is blank: actual date,
			// +/-number for offset from today, null for today
		appendText: '', // Display text following the input box, e.g. showing the format
		buttonText: '...', // Text for trigger button
		buttonImage: '', // URL for trigger button image
		buttonImageOnly: false, // True if the image appears alone, false if it appears on a button
		closeAtTop: true, // True to have the clear/close at the top,
			// false to have them at the bottom
		mandatory: false, // True to hide the Clear link, false to include it
		hideIfNoPrevNext: false, // True to hide next/previous month links
			// if not applicable, false to just disable them
		navigationAsDateFormat: false, // True if date formatting applied to prev/today/next links
		showBigPrevNext: false, // True to show big prev/next links
		gotoCurrent: false, // True if today link goes back to current selection instead
		changeMonth: true, // True if month can be selected directly, false if only prev/next
		changeYear: true, // True if year can be selected directly, false if only prev/next
		monthAfterYear: false, // True if the year select precedes month, false for month then year
		yearRange: '-10:+10', // Range of years to display in drop-down,
			// either relative to current year (-nn:+nn) or absolute (nnnn:nnnn)
		changeFirstDay: true, // True to click on day name to change, false to remain as set
		highlightWeek: false, // True to highlight the selected week
		showOtherMonths: false, // True to show dates in other months, false to leave blank
		showWeeks: false, // True to show week of the year, false to omit
		calculateWeek: this.iso8601Week, // How to calculate the week of the year,
			// takes a Date and returns the number of the week for it
		shortYearCutoff: '+10', // Short year values < this are in the current century,
			// > this are in the previous century, 
			// string value starting with '+' for current year + value
		showStatus: false, // True to show status bar at bottom, false to not show it
		statusForDate: this.dateStatus, // Function to provide status text for a date -
			// takes date and instance as parameters, returns display text
		minDate: null, // The earliest selectable date, or null for no limit
		maxDate: null, // The latest selectable date, or null for no limit
		duration: 'normal', // Duration of display/closure
		beforeShowDay: null, // Function that takes a date and returns an array with
			// [0] = true if selectable, false if not, [1] = custom CSS class name(s) or '', 
			// [2] = cell title (optional), e.g. $.datepicker.noWeekends
		beforeShow: null, // Function that takes an input field and
			// returns a set of custom settings for the date picker
		onSelect: null, // Define a callback function when a date is selected
		onChangeMonthYear: null, // Define a callback function when the month or year is changed
		onClose: null, // Define a callback function when the datepicker is closed
		numberOfMonths: 1, // Number of months to show at a time
		showCurrentAtPos: 0, // The position in multipe months at which to show the current month (starting at 0)
		stepMonths: 1, // Number of months to step back/forward
		stepBigMonths: 12, // Number of months to step back/forward for the big links
		rangeSelect: false, // Allows for selecting a date range on one date picker
		rangeSeparator: ' - ', // Text between two dates in a range
		altField: '', // Selector for an alternate field to store selected dates into
		altFormat: '' // The date format to use for the alternate field
	};
	$.extend(this._defaults, this.regional['']);
	this.dpDiv = $('<div id="' + this._mainDivId + '" style="display: none;"></div>');
}

$.extend(Datepicker.prototype, {
	/* Class name added to elements to indicate already configured with a date picker. */
	markerClassName: 'hasDatepicker',

	/* Debug logging (if enabled). */
	log: function () {
		if (this.debug)
			console.log.apply('', arguments);
	},
	
	/* Override the default settings for all instances of the date picker. 
	   @param  settings  object - the new settings to use as defaults (anonymous object)
	   @return the manager object */
	setDefaults: function(settings) {
		extendRemove(this._defaults, settings || {});
		return this;
	},

	/* Attach the date picker to a jQuery selection.
	   @param  target    element - the target input field or division or span
	   @param  settings  object - the new settings to use for this date picker instance (anonymous) */
	_attachDatepicker: function(target, settings) {
		// check for settings on the control itself - in namespace 'date:'
		var inlineSettings = null;
		for (attrName in this._defaults) {
			var attrValue = target.getAttribute('date:' + attrName);
			if (attrValue) {
				inlineSettings = inlineSettings || {};
				try {
					inlineSettings[attrName] = eval(attrValue);
				} catch (err) {
					inlineSettings[attrName] = attrValue;
				}
			}
		}
		var nodeName = target.nodeName.toLowerCase();
		var inline = (nodeName == 'div' || nodeName == 'span');
		if (!target.id)
			target.id = 'dp' + new Date().getTime();
		var inst = this._newInst($(target), inline);
		inst.settings = $.extend({}, settings || {}, inlineSettings || {}); 
		if (nodeName == 'input') {
			this._connectDatepicker(target, inst);
		} else if (inline) {
			this._inlineDatepicker(target, inst);
		}
	},

	/* Create a new instance object. */
	_newInst: function(target, inline) {
		var id = target[0].id.replace(/([:\[\]\.])/g, '\\\\$1'); // escape jQuery meta chars
		return {id: id, input: target, // associated target
			selectedDay: 0, selectedMonth: 0, selectedYear: 0, // current selection
			drawMonth: 0, drawYear: 0, // month being drawn
			inline: inline, // is datepicker inline or not
			dpDiv: (!inline ? this.dpDiv : // presentation div
			$('<div class="' + this._inlineClass + '"></div>'))};
	},

	/* Attach the date picker to an input field. */
	_connectDatepicker: function(target, inst) {
		var input = $(target);
		if (input.hasClass(this.markerClassName))
			return;
		var appendText = this._get(inst, 'appendText');
		var isRTL = this._get(inst, 'isRTL');
		if (appendText)
			input[isRTL ? 'before' : 'after']('<span class="' + this._appendClass + '">' + appendText + '</span>');
		var showOn = this._get(inst, 'showOn');
		if (showOn == 'focus' || showOn == 'both') // pop-up date picker when in the marked field
			input.focus(this._showDatepicker);
		if (showOn == 'button' || showOn == 'both') { // pop-up date picker when button clicked
			var buttonText = this._get(inst, 'buttonText');
			var buttonImage = this._get(inst, 'buttonImage');
			var trigger = $(this._get(inst, 'buttonImageOnly') ? 
				$('<img/>').addClass(this._triggerClass).
					attr({ src: buttonImage, alt: buttonText, title: buttonText }) :
				$('<button type="button"></button>').addClass(this._triggerClass).
					html(buttonImage == '' ? buttonText : $('<img/>').attr(
					{ src:buttonImage, alt:buttonText, title:buttonText })));
			input[isRTL ? 'before' : 'after'](trigger);
			trigger.click(function() {
				if ($.datepicker._datepickerShowing && $.datepicker._lastInput == target)
					$.datepicker._hideDatepicker();
				else
					$.datepicker._showDatepicker(target);
				return false;
			});
		}
		input.addClass(this.markerClassName).keydown(this._doKeyDown).keypress(this._doKeyPress).
			bind("setData.datepicker", function(event, key, value) {
				inst.settings[key] = value;
			}).bind("getData.datepicker", function(event, key) {
				return this._get(inst, key);
			});
		$.data(target, PROP_NAME, inst);
	},

	/* Attach an inline date picker to a div. */
	_inlineDatepicker: function(target, inst) {
		var input = $(target);
		if (input.hasClass(this.markerClassName))
			return;
		input.addClass(this.markerClassName).append(inst.dpDiv).
			bind("setData.datepicker", function(event, key, value){
				inst.settings[key] = value;
			}).bind("getData.datepicker", function(event, key){
				return this._get(inst, key);
			});
		$.data(target, PROP_NAME, inst);
		this._setDate(inst, this._getDefaultDate(inst));
		this._updateDatepicker(inst);
	},

	/* Tidy up after displaying the date picker. */
	_inlineShow: function(inst) {
		var numMonths = this._getNumberOfMonths(inst); // fix width for dynamic number of date pickers
		inst.dpDiv.width(numMonths[1] * $('.ui-datepicker', inst.dpDiv[0]).width());
	}, 

	/* Pop-up the date picker in a "dialog" box.
	   @param  input     element - ignored
	   @param  dateText  string - the initial date to display (in the current format)
	   @param  onSelect  function - the function(dateText) to call when a date is selected
	   @param  settings  object - update the dialog date picker instance's settings (anonymous object)
	   @param  pos       int[2] - coordinates for the dialog's position within the screen or
	                     event - with x/y coordinates or
	                     leave empty for default (screen centre)
	   @return the manager object */
	_dialogDatepicker: function(input, dateText, onSelect, settings, pos) {
		var inst = this._dialogInst; // internal instance
		if (!inst) {
			var id = 'dp' + new Date().getTime();
			this._dialogInput = $('<input type="text" id="' + id +
				'" size="1" style="position: absolute; top: -100px;"/>');
			this._dialogInput.keydown(this._doKeyDown);
			$('body').append(this._dialogInput);
			inst = this._dialogInst = this._newInst(this._dialogInput, false);
			inst.settings = {};
			$.data(this._dialogInput[0], PROP_NAME, inst);
		}
		extendRemove(inst.settings, settings || {});
		this._dialogInput.val(dateText);

		this._pos = (pos ? (pos.length ? pos : [pos.pageX, pos.pageY]) : null);
		if (!this._pos) {
			var browserWidth = window.innerWidth || document.documentElement.clientWidth ||	document.body.clientWidth;
			var browserHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
			var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
			this._pos = // should use actual width/height below
				[(browserWidth / 2) - 100 + scrollX, (browserHeight / 2) - 150 + scrollY];
		}

		// move input on screen for focus, but hidden behind dialog
		this._dialogInput.css('left', this._pos[0] + 'px').css('top', this._pos[1] + 'px');
		inst.settings.onSelect = onSelect;
		this._inDialog = true;
		this.dpDiv.addClass(this._dialogClass);
		this._showDatepicker(this._dialogInput[0]);
		if ($.blockUI)
			$.blockUI(this.dpDiv);
		$.data(this._dialogInput[0], PROP_NAME, inst);
		return this;
	},

	/* Detach a datepicker from its control.
	   @param  target    element - the target input field or division or span */
	_destroyDatepicker: function(target) {
		var $target = $(target);
		if (!$target.hasClass(this.markerClassName)) {
			return;
		}
		var nodeName = target.nodeName.toLowerCase();
		$.removeData(target, PROP_NAME);
		if (nodeName == 'input') {
			$target.siblings('.' + this._appendClass).remove().end().
				siblings('.' + this._triggerClass).remove().end().
				removeClass(this.markerClassName).
				unbind('focus', this._showDatepicker).
				unbind('keydown', this._doKeyDown).
				unbind('keypress', this._doKeyPress);
		} else if (nodeName == 'div' || nodeName == 'span')
			$target.removeClass(this.markerClassName).empty();
	},

	/* Enable the date picker to a jQuery selection.
	   @param  target    element - the target input field or division or span */
	_enableDatepicker: function(target) {
		var $target = $(target);
		if (!$target.hasClass(this.markerClassName)) {
			return;
		}
		var nodeName = target.nodeName.toLowerCase();
		if (nodeName == 'input') {
		target.disabled = false;
			$target.siblings('button.' + this._triggerClass).
			each(function() { this.disabled = false; }).end().
				siblings('img.' + this._triggerClass).
				css({opacity: '1.0', cursor: ''});
		}
		else if (nodeName == 'div' || nodeName == 'span') {
			$target.children('.' + this._disableClass).remove();
		}
		this._disabledInputs = $.map(this._disabledInputs,
			function(value) { return (value == target ? null : value); }); // delete entry
	},

	/* Disable the date picker to a jQuery selection.
	   @param  target    element - the target input field or division or span */
	_disableDatepicker: function(target) {
		var $target = $(target);
		if (!$target.hasClass(this.markerClassName)) {
			return;
		}
		var nodeName = target.nodeName.toLowerCase();
		if (nodeName == 'input') {
		target.disabled = true;
			$target.siblings('button.' + this._triggerClass).
			each(function() { this.disabled = true; }).end().
				siblings('img.' + this._triggerClass).
				css({opacity: '0.5', cursor: 'default'});
		}
		else if (nodeName == 'div' || nodeName == 'span') {
			var inline = $target.children('.' + this._inlineClass);
			var offset = inline.offset();
			var relOffset = {left: 0, top: 0};
			inline.parents().each(function() {
				if ($(this).css('position') == 'relative') {
					relOffset = $(this).offset();
					return false;
				}
			});
			$target.prepend('<div class="' + this._disableClass + '" style="' +
				($.browser.msie ? 'background-color: transparent; ' : '') +
				'width: ' + inline.width() + 'px; height: ' + inline.height() +
				'px; left: ' + (offset.left - relOffset.left) +
				'px; top: ' + (offset.top - relOffset.top) + 'px;"></div>');
		}
		this._disabledInputs = $.map(this._disabledInputs,
			function(value) { return (value == target ? null : value); }); // delete entry
		this._disabledInputs[this._disabledInputs.length] = target;
	},

	/* Is the first field in a jQuery collection disabled as a datepicker?
	   @param  target    element - the target input field or division or span
	   @return boolean - true if disabled, false if enabled */
	_isDisabledDatepicker: function(target) {
		if (!target)
			return false;
		for (var i = 0; i < this._disabledInputs.length; i++) {
			if (this._disabledInputs[i] == target)
				return true;
		}
		return false;
	},

	/* Update the settings for a date picker attached to an input field or division.
	   @param  target  element - the target input field or division or span
	   @param  name    object - the new settings to update or
	                   string - the name of the setting to change or
	   @param  value   any - the new value for the setting (omit if above is an object) */
	_changeDatepicker: function(target, name, value) {
		var settings = name || {};
		if (typeof name == 'string') {
			settings = {};
			settings[name] = value;
		}
		if (inst = $.data(target, PROP_NAME)) {
			extendRemove(inst.settings, settings);
			this._updateDatepicker(inst);
		}
	},

	/* Set the dates for a jQuery selection.
	   @param  target   element - the target input field or division or span
	   @param  date     Date - the new date
	   @param  endDate  Date - the new end date for a range (optional) */
	_setDateDatepicker: function(target, date, endDate) {
		var inst = $.data(target, PROP_NAME);
		if (inst) {
			this._setDate(inst, date, endDate);
			this._updateDatepicker(inst);
		}
	},

	/* Get the date(s) for the first entry in a jQuery selection.
	   @param  target  element - the target input field or division or span
	   @return Date - the current date or
	           Date[2] - the current dates for a range */
	_getDateDatepicker: function(target) {
		var inst = $.data(target, PROP_NAME);
		if (inst && !inst.inline)
			this._setDateFromField(inst); 
		return (inst ? this._getDate(inst) : null);
	},

	/* Handle keystrokes. */
	_doKeyDown: function(e) {
		var inst = $.data(e.target, PROP_NAME);
		var handled = true;
		if ($.datepicker._datepickerShowing)
			switch (e.keyCode) {
				case 9:  $.datepicker._hideDatepicker(null, '');
						break; // hide on tab out
				case 13: $.datepicker._selectDay(e.target, inst.selectedMonth, inst.selectedYear,
							$('td.ui-datepicker-days-cell-over', inst.dpDiv)[0]);
						return false; // don't submit the form
						break; // select the value on enter
				case 27: $.datepicker._hideDatepicker(null, $.datepicker._get(inst, 'duration'));
						break; // hide on escape
				case 33: $.datepicker._adjustDate(e.target, (e.ctrlKey ? -1 :
							-$.datepicker._get(inst, 'stepMonths')), (e.ctrlKey ? 'Y' : 'M'));
						break; // previous month/year on page up/+ ctrl
				case 34: $.datepicker._adjustDate(e.target, (e.ctrlKey ? +1 :
							+$.datepicker._get(inst, 'stepMonths')), (e.ctrlKey ? 'Y' : 'M'));
						break; // next month/year on page down/+ ctrl
				case 35: if (e.ctrlKey) $.datepicker._clearDate(e.target);
						handled = e.ctrlKey;
						break; // clear on ctrl+end
				case 36: if (e.ctrlKey) $.datepicker._gotoToday(e.target);
						handled = e.ctrlKey;
						break; // current on ctrl+home
				case 37: if (e.ctrlKey) $.datepicker._adjustDate(e.target, -1, 'D');
						handled = e.ctrlKey;
						break; // -1 day on ctrl+left
				case 38: if (e.ctrlKey) $.datepicker._adjustDate(e.target, -7, 'D');
						handled = e.ctrlKey;
						break; // -1 week on ctrl+up
				case 39: if (e.ctrlKey) $.datepicker._adjustDate(e.target, +1, 'D');
						handled = e.ctrlKey;
						break; // +1 day on ctrl+right
				case 40: if (e.ctrlKey) $.datepicker._adjustDate(e.target, +7, 'D');
						handled = e.ctrlKey;
						break; // +1 week on ctrl+down
				default: handled = false;
			}
		else if (e.keyCode == 36 && e.ctrlKey) // display the date picker on ctrl+home
			$.datepicker._showDatepicker(this);
		else
			handled = false;
		if (handled) {
			e.preventDefault();
			e.stopPropagation();
		}
	},

	/* Filter entered characters - based on date format. */
	_doKeyPress: function(e) {
		var inst = $.data(e.target, PROP_NAME);
		var chars = $.datepicker._possibleChars($.datepicker._get(inst, 'dateFormat'));
		var chr = String.fromCharCode(e.charCode == undefined ? e.keyCode : e.charCode);
		return e.ctrlKey || (chr < ' ' || !chars || chars.indexOf(chr) > -1);
	},

	/* Pop-up the date picker for a given input field.
	   @param  input  element - the input field attached to the date picker or
	                  event - if triggered by focus */
	_showDatepicker: function(input) {
		input = input.target || input;
		if (input.nodeName.toLowerCase() != 'input') // find from button/image trigger
			input = $('input', input.parentNode)[0];
		if ($.datepicker._isDisabledDatepicker(input) || $.datepicker._lastInput == input) // already here
			return;
		var inst = $.data(input, PROP_NAME);
		var beforeShow = $.datepicker._get(inst, 'beforeShow');
		extendRemove(inst.settings, (beforeShow ? beforeShow.apply(input, [input, inst]) : {}));
		$.datepicker._hideDatepicker(null, '');
		$.datepicker._lastInput = input;
		$.datepicker._setDateFromField(inst);
		if ($.datepicker._inDialog) // hide cursor
			input.value = '';
		if (!$.datepicker._pos) { // position below input
			$.datepicker._pos = $.datepicker._findPos(input);
			$.datepicker._pos[1] += input.offsetHeight; // add the height
		}
		var isFixed = false;
		$(input).parents().each(function() {
			isFixed |= $(this).css('position') == 'fixed';
			return !isFixed;
		});
		if (isFixed && $.browser.opera) { // correction for Opera when fixed and scrolled
			$.datepicker._pos[0] -= document.documentElement.scrollLeft;
			$.datepicker._pos[1] -= document.documentElement.scrollTop;
		}
		var offset = {left: $.datepicker._pos[0], top: $.datepicker._pos[1]};
		$.datepicker._pos = null;
		inst.rangeStart = null;
		// determine sizing offscreen
		inst.dpDiv.css({position: 'absolute', display: 'block', top: '-1000px'});
		$.datepicker._updateDatepicker(inst);
		// fix width for dynamic number of date pickers
		inst.dpDiv.width($.datepicker._getNumberOfMonths(inst)[1] *
			$('.ui-datepicker', inst.dpDiv[0])[0].offsetWidth);
		// and adjust position before showing
		offset = $.datepicker._checkOffset(inst, offset, isFixed);
		inst.dpDiv.css({position: ($.datepicker._inDialog && $.blockUI ?
			'static' : (isFixed ? 'fixed' : 'absolute')), display: 'none',
			left: offset.left + 'px', top: offset.top + 'px'});
		if (!inst.inline) {
			var showAnim = $.datepicker._get(inst, 'showAnim') || 'show';
			var duration = $.datepicker._get(inst, 'duration');
			var postProcess = function() {
				$.datepicker._datepickerShowing = true;
				if ($.browser.msie && parseInt($.browser.version) < 7) // fix IE < 7 select problems
					$('iframe.ui-datepicker-cover').css({width: inst.dpDiv.width() + 4,
						height: inst.dpDiv.height() + 4});
			};
			if ($.effects && $.effects[showAnim])
				inst.dpDiv.show(showAnim, $.datepicker._get(inst, 'showOptions'), duration, postProcess);
			else
				inst.dpDiv[showAnim](duration, postProcess);
			if (duration == '')
				postProcess();
			if (inst.input[0].type != 'hidden')
				inst.input[0].focus();
			$.datepicker._curInst = inst;
		}
	},

	/* Generate the date picker content. */
	_updateDatepicker: function(inst) {
		var dims = {width: inst.dpDiv.width() + 4,
			height: inst.dpDiv.height() + 4};
		inst.dpDiv.empty().append(this._generateHTML(inst)).
			find('iframe.ui-datepicker-cover').
			css({width: dims.width, height: dims.height});
		var numMonths = this._getNumberOfMonths(inst);
		inst.dpDiv[(numMonths[0] != 1 || numMonths[1] != 1 ? 'add' : 'remove') +
			'Class']('ui-datepicker-multi');
		inst.dpDiv[(this._get(inst, 'isRTL') ? 'add' : 'remove') +
			'Class']('ui-datepicker-rtl');
		if (inst.input && inst.input[0].type != 'hidden')
			$(inst.input[0]).focus();
	},

	/* Check positioning to remain on screen. */
	_checkOffset: function(inst, offset, isFixed) {
		var pos = inst.input ? this._findPos(inst.input[0]) : null;
		var browserWidth = window.innerWidth || document.documentElement.clientWidth;
		var browserHeight = window.innerHeight || document.documentElement.clientHeight;
		var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
		var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
		// reposition date picker horizontally if outside the browser window
		if (this._get(inst, 'isRTL') || (offset.left + inst.dpDiv.width() - scrollX) > browserWidth)
			offset.left = Math.max((isFixed ? 0 : scrollX),
				pos[0] + (inst.input ? inst.input.width() : 0) - (isFixed ? scrollX : 0) - inst.dpDiv.width() -
				(isFixed && $.browser.opera ? document.documentElement.scrollLeft : 0));
		else
			offset.left -= (isFixed ? scrollX : 0);
		// reposition date picker vertically if outside the browser window
		if ((offset.top + inst.dpDiv.height() - scrollY) > browserHeight)
			offset.top = Math.max((isFixed ? 0 : scrollY),
				pos[1] - (isFixed ? scrollY : 0) - (this._inDialog ? 0 : inst.dpDiv.height()) -
				(isFixed && $.browser.opera ? document.documentElement.scrollTop : 0));
		else
			offset.top -= (isFixed ? scrollY : 0);
		return offset;
	},
	
	/* Find an object's position on the screen. */
	_findPos: function(obj) {
        while (obj && (obj.type == 'hidden' || obj.nodeType != 1)) {
            obj = obj.nextSibling;
        }
        var position = $(obj).offset();
	    return [position.left, position.top];
	},

	/* Hide the date picker from view.
	   @param  input  element - the input field attached to the date picker
	   @param  duration  string - the duration over which to close the date picker */
	_hideDatepicker: function(input, duration) {
		var inst = this._curInst;
		if (!inst)
			return;
		var rangeSelect = this._get(inst, 'rangeSelect');
		if (rangeSelect && this._stayOpen)
			this._selectDate('#' + inst.id, this._formatDate(inst,
				inst.currentDay, inst.currentMonth, inst.currentYear));
		this._stayOpen = false;
		if (this._datepickerShowing) {
			duration = (duration != null ? duration : this._get(inst, 'duration'));
			var showAnim = this._get(inst, 'showAnim');
			var postProcess = function() {
				$.datepicker._tidyDialog(inst);
			};
			if (duration != '' && $.effects && $.effects[showAnim])
				inst.dpDiv.hide(showAnim, $.datepicker._get(inst, 'showOptions'),
					duration, postProcess);
			else
				inst.dpDiv[(duration == '' ? 'hide' : (showAnim == 'slideDown' ? 'slideUp' :
					(showAnim == 'fadeIn' ? 'fadeOut' : 'hide')))](duration, postProcess);
			if (duration == '')
				this._tidyDialog(inst);
			var onClose = this._get(inst, 'onClose');
			if (onClose)
				onClose.apply((inst.input ? inst.input[0] : null),
					[this._getDate(inst), inst]);  // trigger custom callback
			this._datepickerShowing = false;
			this._lastInput = null;
			inst.settings.prompt = null;
			if (this._inDialog) {
				this._dialogInput.css({ position: 'absolute', left: '0', top: '-100px' });
				if ($.blockUI) {
					$.unblockUI();
					$('body').append(this.dpDiv);
				}
			}
			this._inDialog = false;
		}
		this._curInst = null;
	},

	/* Tidy up after a dialog display. */
	_tidyDialog: function(inst) {
		inst.dpDiv.removeClass(this._dialogClass).unbind('.ui-datepicker');
		$('.' + this._promptClass, inst.dpDiv).remove();
	},

	/* Close date picker if clicked elsewhere. */
	_checkExternalClick: function(event) {
		if (!$.datepicker._curInst)
			return;
		var $target = $(event.target);
		if (($target.parents('#' + $.datepicker._mainDivId).length == 0) &&
				!$target.hasClass($.datepicker.markerClassName) &&
				!$target.hasClass($.datepicker._triggerClass) &&
				$.datepicker._datepickerShowing && !($.datepicker._inDialog && $.blockUI))
			$.datepicker._hideDatepicker(null, '');
	},

	/* Adjust one of the date sub-fields. */
	_adjustDate: function(id, offset, period) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		this._adjustInstDate(inst, offset, period);
		this._updateDatepicker(inst);
	},

	/* Action for current link. */
	_gotoToday: function(id) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		if (this._get(inst, 'gotoCurrent') && inst.currentDay) {
			inst.selectedDay = inst.currentDay;
			inst.drawMonth = inst.selectedMonth = inst.currentMonth;
			inst.drawYear = inst.selectedYear = inst.currentYear;
		}
		else {
		var date = new Date();
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		}
		this._adjustDate(target);
		this._notifyChange(inst);
	},

	/* Action for selecting a new month/year. */
	_selectMonthYear: function(id, select, period) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		inst._selectingMonthYear = false;
		inst[period == 'M' ? 'drawMonth' : 'drawYear'] =
			select.options[select.selectedIndex].value - 0;
		this._adjustDate(target);
		this._notifyChange(inst);
	},

	/* Restore input focus after not changing month/year. */
	_clickMonthYear: function(id) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		if (inst.input && inst._selectingMonthYear && !$.browser.msie)
			inst.input[0].focus();
		inst._selectingMonthYear = !inst._selectingMonthYear;
	},

	/* Action for changing the first week day. */
	_changeFirstDay: function(id, day) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		inst.settings.firstDay = day;
		this._updateDatepicker(inst);
	},

	/* Action for selecting a day. */
	_selectDay: function(id, month, year, td) {
		if ($(td).hasClass(this._unselectableClass))
			return;
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		var rangeSelect = this._get(inst, 'rangeSelect');
		if (rangeSelect) {
			this._stayOpen = !this._stayOpen;
			if (this._stayOpen) {
				$('.ui-datepicker td').removeClass(this._currentClass);
				$(td).addClass(this._currentClass);
			} 
		}
		inst.selectedDay = inst.currentDay = $('a', td).html();
		inst.selectedMonth = inst.currentMonth = month;
		inst.selectedYear = inst.currentYear = year;
		if (this._stayOpen) {
			inst.endDay = inst.endMonth = inst.endYear = null;
		}
		else if (rangeSelect) {
			inst.endDay = inst.currentDay;
			inst.endMonth = inst.currentMonth;
			inst.endYear = inst.currentYear;
		}
		this._selectDate(id, this._formatDate(inst,
			inst.currentDay, inst.currentMonth, inst.currentYear));
		if (this._stayOpen) {
			inst.rangeStart = new Date(inst.currentYear, inst.currentMonth, inst.currentDay);
			this._updateDatepicker(inst);
		}
		else if (rangeSelect) {
			inst.selectedDay = inst.currentDay = inst.rangeStart.getDate();
			inst.selectedMonth = inst.currentMonth = inst.rangeStart.getMonth();
			inst.selectedYear = inst.currentYear = inst.rangeStart.getFullYear();
			inst.rangeStart = null;
			if (inst.inline)
				this._updateDatepicker(inst);
		}
	},

	/* Erase the input field and hide the date picker. */
	_clearDate: function(id) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		if (this._get(inst, 'mandatory'))
			return;
		this._stayOpen = false;
		inst.endDay = inst.endMonth = inst.endYear = inst.rangeStart = null;
		this._selectDate(target, '');
	},

	/* Update the input field with the selected date. */
	_selectDate: function(id, dateStr) {
		var target = $(id);
		var inst = $.data(target[0], PROP_NAME);
		dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
		if (this._get(inst, 'rangeSelect') && dateStr)
			dateStr = (inst.rangeStart ? this._formatDate(inst, inst.rangeStart) :
				dateStr) + this._get(inst, 'rangeSeparator') + dateStr;
		if (inst.input)
			inst.input.val(dateStr);
		this._updateAlternate(inst);
		var onSelect = this._get(inst, 'onSelect');
		if (onSelect)
			onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
		else if (inst.input)
			inst.input.trigger('change'); // fire the change event
		if (inst.inline)
			this._updateDatepicker(inst);
		else if (!this._stayOpen) {
			this._hideDatepicker(null, this._get(inst, 'duration'));
			this._lastInput = inst.input[0];
			if (typeof(inst.input[0]) != 'object')
				inst.input[0].focus(); // restore focus
			this._lastInput = null;
		}
	},
	
	/* Update any alternate field to synchronise with the main field. */
	_updateAlternate: function(inst) {
		var altField = this._get(inst, 'altField');
		if (altField) { // update alternate field too
			var altFormat = this._get(inst, 'altFormat');
			var date = this._getDate(inst);
			dateStr = (isArray(date) ? (!date[0] && !date[1] ? '' :
				this.formatDate(altFormat, date[0], this._getFormatConfig(inst)) +
				this._get(inst, 'rangeSeparator') + this.formatDate(
				altFormat, date[1] || date[0], this._getFormatConfig(inst))) :
				this.formatDate(altFormat, date, this._getFormatConfig(inst)));
			$(altField).each(function() { $(this).val(dateStr); });
		}
	},

	/* Set as beforeShowDay function to prevent selection of weekends.
	   @param  date  Date - the date to customise
	   @return [boolean, string] - is this date selectable?, what is its CSS class? */
	noWeekends: function(date) {
		var day = date.getDay();
		return [(day > 0 && day < 6), ''];
	},
	
	/* Set as calculateWeek to determine the week of the year based on the ISO 8601 definition.
	   @param  date  Date - the date to get the week for
	   @return  number - the number of the week within the year that contains this date */
	iso8601Week: function(date) {
		var checkDate = new Date(date.getFullYear(), date.getMonth(), date.getDate(), (date.getTimezoneOffset() / -60));
		var firstMon = new Date(checkDate.getFullYear(), 1 - 1, 4); // First week always contains 4 Jan
		var firstDay = firstMon.getDay() || 7; // Day of week: Mon = 1, ..., Sun = 7
		firstMon.setDate(firstMon.getDate() + 1 - firstDay); // Preceding Monday
		if (firstDay < 4 && checkDate < firstMon) { // Adjust first three days in year if necessary
			checkDate.setDate(checkDate.getDate() - 3); // Generate for previous year
			return $.datepicker.iso8601Week(checkDate);
		} else if (checkDate > new Date(checkDate.getFullYear(), 12 - 1, 28)) { // Check last three days in year
			firstDay = new Date(checkDate.getFullYear() + 1, 1 - 1, 4).getDay() || 7;
			if (firstDay > 4 && (checkDate.getDay() || 7) < firstDay - 3) { // Adjust if necessary
				checkDate.setDate(checkDate.getDate() + 3); // Generate for next year
				return $.datepicker.iso8601Week(checkDate);
			}
		}
		return Math.floor(((checkDate - firstMon) / 86400000) / 7) + 1; // Weeks to given date
	},
	
	/* Provide status text for a particular date.
	   @param  date  the date to get the status for
	   @param  inst  the current datepicker instance
	   @return  the status display text for this date */
	dateStatus: function(date, inst) {
		return $.datepicker.formatDate($.datepicker._get(inst, 'dateStatus'),
			date, $.datepicker._getFormatConfig(inst));
	},

	/* Parse a string value into a date object.
	   See formatDate below for the possible formats.

	   @param  format    string - the expected format of the date
	   @param  value     string - the date in the above format
	   @param  settings  Object - attributes include:
	                     shortYearCutoff  number - the cutoff year for determining the century (optional)
	                     dayNamesShort    string[7] - abbreviated names of the days from Sunday (optional)
	                     dayNames         string[7] - names of the days from Sunday (optional)
	                     monthNamesShort  string[12] - abbreviated names of the months (optional)
	                     monthNames       string[12] - names of the months (optional)
	   @return  Date - the extracted date value or null if value is blank */
	parseDate: function (format, value, settings) {
		if (format == null || value == null)
			throw 'Invalid arguments';
		value = (typeof value == 'object' ? value.toString() : value + '');
		if (value == '')
			return null;
		var shortYearCutoff = (settings ? settings.shortYearCutoff : null) || this._defaults.shortYearCutoff;
		var dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort;
		var dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames;
		var monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort;
		var monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames;
		var year = -1;
		var month = -1;
		var day = -1;
		var doy = -1;
		var literal = false;
		// Check whether a format character is doubled
		var lookAhead = function(match) {
			var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
			if (matches)
				iFormat++;
			return matches;	
		};
		// Extract a number from the string value
		var getNumber = function(match) {
			lookAhead(match);
			var origSize = (match == '@' ? 14 : (match == 'y' ? 4 : (match == 'o' ? 3 : 2)));
			var size = origSize;
			var num = 0;
			while (size > 0 && iValue < value.length &&
					value.charAt(iValue) >= '0' && value.charAt(iValue) <= '9') {
				num = num * 10 + (value.charAt(iValue++) - 0);
				size--;
			}
			if (size == origSize)
				throw 'Missing number at position ' + iValue;
			return num;
		};
		// Extract a name from the string value and convert to an index
		var getName = function(match, shortNames, longNames) {
			var names = (lookAhead(match) ? longNames : shortNames);
			var size = 0;
			for (var j = 0; j < names.length; j++)
				size = Math.max(size, names[j].length);
			var name = '';
			var iInit = iValue;
			while (size > 0 && iValue < value.length) {
				name += value.charAt(iValue++);
				for (var i = 0; i < names.length; i++)
					if (name == names[i])
						return i + 1;
				size--;
			}
			throw 'Unknown name at position ' + iInit;
		};
		// Confirm that a literal character matches the string value
		var checkLiteral = function() {
			if (value.charAt(iValue) != format.charAt(iFormat))
				throw 'Unexpected literal at position ' + iValue;
			iValue++;
		};
		var iValue = 0;
		for (var iFormat = 0; iFormat < format.length; iFormat++) {
			if (literal)
				if (format.charAt(iFormat) == "'" && !lookAhead("'"))
					literal = false;
				else
					checkLiteral();
			else
				switch (format.charAt(iFormat)) {
					case 'd':
						day = getNumber('d');
						break;
					case 'D': 
						getName('D', dayNamesShort, dayNames);
						break;
					case 'o':
						doy = getNumber('o');
						break;
					case 'm': 
						month = getNumber('m');
						break;
					case 'M':
						month = getName('M', monthNamesShort, monthNames); 
						break;
					case 'y':
						year = getNumber('y');
						break;
					case '@':
						var date = new Date(getNumber('@'));
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "'":
						if (lookAhead("'"))
							checkLiteral();
						else
							literal = true;
						break;
					default:
						checkLiteral();
				}
		}
		if (year < 100)
			year += new Date().getFullYear() - new Date().getFullYear() % 100 +
				(year <= shortYearCutoff ? 0 : -100);
		if (doy > -1) {
			month = 1;
			day = doy;
			do {
				var dim = this._getDaysInMonth(year, month - 1);
				if (day <= dim)
					break;
				month++;
				day -= dim;
			} while (true);
		}
		var date = new Date(year, month - 1, day);
		if (date.getFullYear() != year || date.getMonth() + 1 != month || date.getDate() != day)
			throw 'Invalid date'; // E.g. 31/02/*
		return date;
	},

	/* Standard date formats. */
	ATOM: 'yy-mm-dd', // RFC 3339 (ISO 8601)
	COOKIE: 'D, dd M yy',
	ISO_8601: 'yy-mm-dd',
	RFC_822: 'D, d M y',
	RFC_850: 'DD, dd-M-y',
	RFC_1036: 'D, d M y',
	RFC_1123: 'D, d M yy',
	RFC_2822: 'D, d M yy',
	RSS: 'D, d M y', // RFC 822
	TIMESTAMP: '@',
	W3C: 'yy-mm-dd', // ISO 8601

	/* Format a date object into a string value.
	   The format can be combinations of the following:
	   d  - day of month (no leading zero)
	   dd - day of month (two digit)
	   o  - day of year (no leading zeros)
	   oo - day of year (three digit)
	   D  - day name short
	   DD - day name long
	   m  - month of year (no leading zero)
	   mm - month of year (two digit)
	   M  - month name short
	   MM - month name long
	   y  - year (two digit)
	   yy - year (four digit)
	   @ - Unix timestamp (ms since 01/01/1970)
	   '...' - literal text
	   '' - single quote

	   @param  format    string - the desired format of the date
	   @param  date      Date - the date value to format
	   @param  settings  Object - attributes include:
	                     dayNamesShort    string[7] - abbreviated names of the days from Sunday (optional)
	                     dayNames         string[7] - names of the days from Sunday (optional)
	                     monthNamesShort  string[12] - abbreviated names of the months (optional)
	                     monthNames       string[12] - names of the months (optional)
	   @return  string - the date in the above format */
	formatDate: function (format, date, settings) {
		if (!date)
			return '';
		var dayNamesShort = (settings ? settings.dayNamesShort : null) || this._defaults.dayNamesShort;
		var dayNames = (settings ? settings.dayNames : null) || this._defaults.dayNames;
		var monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort;
		var monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames;
		// Check whether a format character is doubled
		var lookAhead = function(match) {
			var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
			if (matches)
				iFormat++;
			return matches;	
		};
		// Format a number, with leading zero if necessary
		var formatNumber = function(match, value, len) {
			var num = '' + value;
			if (lookAhead(match))
				while (num.length < len)
					num = '0' + num;
			return num;
		};
		// Format a name, short or long as requested
		var formatName = function(match, value, shortNames, longNames) {
			return (lookAhead(match) ? longNames[value] : shortNames[value]);
		};
		var output = '';
		var literal = false;
		if (date)
			for (var iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal)
					if (format.charAt(iFormat) == "'" && !lookAhead("'"))
						literal = false;
					else
						output += format.charAt(iFormat);
				else
					switch (format.charAt(iFormat)) {
						case 'd':
							output += formatNumber('d', date.getDate(), 2);
							break;
						case 'D': 
							output += formatName('D', date.getDay(), dayNamesShort, dayNames);
							break;
						case 'o':
							var doy = date.getDate();
							for (var m = date.getMonth() - 1; m >= 0; m--)
								doy += this._getDaysInMonth(date.getFullYear(), m);
							output += formatNumber('o', doy, 3);
							break;
						case 'm': 
							output += formatNumber('m', date.getMonth() + 1, 2);
							break;
						case 'M':
							output += formatName('M', date.getMonth(), monthNamesShort, monthNames); 
							break;
						case 'y':
							output += (lookAhead('y') ? date.getFullYear() : 
								(date.getYear() % 100 < 10 ? '0' : '') + date.getYear() % 100);
							break;
						case '@':
							output += date.getTime(); 
							break;
						case "'":
							if (lookAhead("'"))
								output += "'";
							else
								literal = true;
							break;
						default:
							output += format.charAt(iFormat);
					}
			}
		return output;
	},

	/* Extract all possible characters from the date format. */
	_possibleChars: function (format) {
		var chars = '';
		var literal = false;
		for (var iFormat = 0; iFormat < format.length; iFormat++)
			if (literal)
				if (format.charAt(iFormat) == "'" && !lookAhead("'"))
					literal = false;
				else
					chars += format.charAt(iFormat);
			else
				switch (format.charAt(iFormat)) {
					case 'd': case 'm': case 'y': case '@':
						chars += '0123456789'; 
						break;
					case 'D': case 'M':
						return null; // Accept anything
					case "'":
						if (lookAhead("'"))
							chars += "'";
						else
							literal = true;
						break;
					default:
						chars += format.charAt(iFormat);
				}
		return chars;
	},

	/* Get a setting value, defaulting if necessary. */
	_get: function(inst, name) {
		return inst.settings[name] !== undefined ?
			inst.settings[name] : this._defaults[name];
	},

	/* Parse existing date and initialise date picker. */
	_setDateFromField: function(inst) {
		var dateFormat = this._get(inst, 'dateFormat');
		var dates = inst.input ? inst.input.val().split(this._get(inst, 'rangeSeparator')) : null; 
		inst.endDay = inst.endMonth = inst.endYear = null;
		var date = defaultDate = this._getDefaultDate(inst);
		if (dates.length > 0) {
			var settings = this._getFormatConfig(inst);
			if (dates.length > 1) {
				date = this.parseDate(dateFormat, dates[1], settings) || defaultDate;
				inst.endDay = date.getDate();
				inst.endMonth = date.getMonth();
				inst.endYear = date.getFullYear();
			}
			try {
				date = this.parseDate(dateFormat, dates[0], settings) || defaultDate;
			} catch (e) {
				this.log(e);
				date = defaultDate;
			}
		}
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		inst.currentDay = (dates[0] ? date.getDate() : 0);
		inst.currentMonth = (dates[0] ? date.getMonth() : 0);
		inst.currentYear = (dates[0] ? date.getFullYear() : 0);
		this._adjustInstDate(inst);
	},
	
	/* Retrieve the default date shown on opening. */
	_getDefaultDate: function(inst) {
		var date = this._determineDate(this._get(inst, 'defaultDate'), new Date());
		var minDate = this._getMinMaxDate(inst, 'min', true);
		var maxDate = this._getMinMaxDate(inst, 'max');
		date = (minDate && date < minDate ? minDate : date);
		date = (maxDate && date > maxDate ? maxDate : date);
		return date;
	},

	/* A date may be specified as an exact value or a relative one. */
	_determineDate: function(date, defaultDate) {
		var offsetNumeric = function(offset) {
			var date = new Date();
			date.setUTCDate(date.getUTCDate() + offset);
			return date;
		};
		var offsetString = function(offset, getDaysInMonth) {
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth();
			var day = date.getDate();
			var pattern = /([+-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g;
			var matches = pattern.exec(offset);
			while (matches) {
				switch (matches[2] || 'd') {
					case 'd' : case 'D' :
						day += (matches[1] - 0); break;
					case 'w' : case 'W' :
						day += (matches[1] * 7); break;
					case 'm' : case 'M' :
						month += (matches[1] - 0); 
						day = Math.min(day, getDaysInMonth(year, month));
						break;
					case 'y': case 'Y' :
						year += (matches[1] - 0);
						day = Math.min(day, getDaysInMonth(year, month));
						break;
				}
				matches = pattern.exec(offset);
			}
			return new Date(year, month, day);
		};
		return (date == null ? defaultDate :
			(typeof date == 'string' ? offsetString(date, this._getDaysInMonth) :
			(typeof date == 'number' ? offsetNumeric(date) : date)));
	},

	/* Set the date(s) directly. */
	_setDate: function(inst, date, endDate) {
		var clear = !(date);
		date = this._determineDate(date, new Date());
		inst.selectedDay = inst.currentDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = inst.currentMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = inst.currentYear = date.getFullYear();
		if (this._get(inst, 'rangeSelect')) {
			if (endDate) {
				endDate = this._determineDate(endDate, null);
				inst.endDay = endDate.getDate();
				inst.endMonth = endDate.getMonth();
				inst.endYear = endDate.getFullYear();
			} else {
				inst.endDay = inst.currentDay;
				inst.endMonth = inst.currentMonth;
				inst.endYear = inst.currentYear;
			}
		}
		this._adjustInstDate(inst);
		if (inst.input)
			inst.input.val(clear ? '' : this._formatDate(inst) +
				(!this._get(inst, 'rangeSelect') ? '' : this._get(inst, 'rangeSeparator') +
				this._formatDate(inst, inst.endDay, inst.endMonth, inst.endYear)));
	},

	/* Retrieve the date(s) directly. */
	_getDate: function(inst) {
		var startDate = (!inst.currentYear || (inst.input && inst.input.val() == '') ? null :
			new Date(inst.currentYear, inst.currentMonth, inst.currentDay));
		if (this._get(inst, 'rangeSelect')) {
			return [inst.rangeStart || startDate,
				(!inst.endYear ? inst.rangeStart || startDate :
				new Date(inst.endYear, inst.endMonth, inst.endDay))];
		} else
			return startDate;
	},

	/* Generate the HTML for the current state of the date picker. */
	_generateHTML: function(inst) {
		var today = new Date();
		today = new Date(today.getFullYear(), today.getMonth(), today.getDate()); // clear time
		var showStatus = this._get(inst, 'showStatus');
		var initStatus = this._get(inst, 'initStatus') || '&#xa0;';
		var isRTL = this._get(inst, 'isRTL');
		// build the date picker HTML
		var clear = (this._get(inst, 'mandatory') ? '' :
			'<div class="ui-datepicker-clear"><a onclick="jQuery.datepicker._clearDate(\'#' + inst.id + '\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'clearStatus'), initStatus) + '>' +
			this._get(inst, 'clearText') + '</a></div>');
		var controls = '<div class="ui-datepicker-control">' + (isRTL ? '' : clear) +
			'<div class="ui-datepicker-close"><a onclick="jQuery.datepicker._hideDatepicker();"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'closeStatus'), initStatus) + '>' +
			this._get(inst, 'closeText') + '</a></div>' + (isRTL ? clear : '')  + '</div>';
		var prompt = this._get(inst, 'prompt');
		var closeAtTop = this._get(inst, 'closeAtTop');
		var hideIfNoPrevNext = this._get(inst, 'hideIfNoPrevNext');
		var navigationAsDateFormat = this._get(inst, 'navigationAsDateFormat');
		var showBigPrevNext = this._get(inst, 'showBigPrevNext');
		var numMonths = this._getNumberOfMonths(inst);
		var showCurrentAtPos = this._get(inst, 'showCurrentAtPos');
		var stepMonths = this._get(inst, 'stepMonths');
		var stepBigMonths = this._get(inst, 'stepBigMonths');
		var isMultiMonth = (numMonths[0] != 1 || numMonths[1] != 1);
		var currentDate = (!inst.currentDay ? new Date(9999, 9, 9) :
			new Date(inst.currentYear, inst.currentMonth, inst.currentDay));
		var minDate = this._getMinMaxDate(inst, 'min', true);
		var maxDate = this._getMinMaxDate(inst, 'max');
		var drawMonth = inst.drawMonth - showCurrentAtPos;
		var drawYear = inst.drawYear;
		if (drawMonth < 0) {
			drawMonth += 12;
			drawYear--;
		}
		if (maxDate) {
			var maxDraw = new Date(maxDate.getFullYear(),
				maxDate.getMonth() - numMonths[1] + 1, maxDate.getDate());
			maxDraw = (minDate && maxDraw < minDate ? minDate : maxDraw);
			while (new Date(drawYear, drawMonth, 1) > maxDraw) {
				drawMonth--;
				if (drawMonth < 0) {
					drawMonth = 11;
					drawYear--;
				}
			}
		}
		// controls and links
		var prevText = this._get(inst, 'prevText');
		prevText = (!navigationAsDateFormat ? prevText : this.formatDate(
			prevText, new Date(drawYear, drawMonth - stepMonths, 1), this._getFormatConfig(inst)));
		var prevBigText = (showBigPrevNext ? this._get(inst, 'prevBigText') : '');
		prevBigText = (!navigationAsDateFormat ? prevBigText : this.formatDate(
			prevBigText, new Date(drawYear, drawMonth - stepBigMonths, 1), this._getFormatConfig(inst)));
		var prev = '<div class="ui-datepicker-prev">' + (this._canAdjustMonth(inst, -1, drawYear, drawMonth) ? 
			(showBigPrevNext ? '<a onclick="jQuery.datepicker._adjustDate(\'#' + inst.id + '\', -' + stepBigMonths + ', \'M\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'prevBigStatus'), initStatus) + '>' + prevBigText + '</a>' : '') +
			'<a onclick="jQuery.datepicker._adjustDate(\'#' + inst.id + '\', -' + stepMonths + ', \'M\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'prevStatus'), initStatus) + '>' + prevText + '</a>' :
			(hideIfNoPrevNext ? '' : '<label>' + prevBigText + '</label><label>' + prevText + '</label>')) + '</div>';
		var nextText = this._get(inst, 'nextText');
		nextText = (!navigationAsDateFormat ? nextText : this.formatDate(
			nextText, new Date(drawYear, drawMonth + stepMonths, 1), this._getFormatConfig(inst)));
		var nextBigText = (showBigPrevNext ? this._get(inst, 'nextBigText') : '');
		nextBigText = (!navigationAsDateFormat ? nextBigText : this.formatDate(
			nextBigText, new Date(drawYear, drawMonth + stepBigMonths, 1), this._getFormatConfig(inst)));
		var next = '<div class="ui-datepicker-next">' + (this._canAdjustMonth(inst, +1, drawYear, drawMonth) ?
			'<a onclick="jQuery.datepicker._adjustDate(\'#' + inst.id + '\', +' + stepMonths + ', \'M\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'nextStatus'), initStatus) + '>' + nextText + '</a>' +
			(showBigPrevNext ? '<a onclick="jQuery.datepicker._adjustDate(\'#' + inst.id + '\', +' + stepBigMonths + ', \'M\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'nextBigStatus'), initStatus) + '>' + nextBigText + '</a>' : '') :
			(hideIfNoPrevNext ? '' : '<label>' + nextText + '</label><label>' + nextBigText + '</label>')) + '</div>';
		var currentText = this._get(inst, 'currentText');
		var gotoDate = (this._get(inst, 'gotoCurrent') && inst.currentDay ? currentDate : today); 
		currentText = (!navigationAsDateFormat ? currentText :
			this.formatDate(currentText, gotoDate, this._getFormatConfig(inst)));
		var html = (prompt ? '<div class="' + this._promptClass + '">' + prompt + '</div>' : '') +
			(closeAtTop && !inst.inline ? controls : '') +
			'<div class="ui-datepicker-links">' + (isRTL ? next : prev) +
			(this._isInRange(inst, gotoDate) ? '<div class="ui-datepicker-current">' +
			'<a onclick="jQuery.datepicker._gotoToday(\'#' + inst.id + '\');"' +
			this._addStatus(showStatus, inst.id, this._get(inst, 'currentStatus'), initStatus) + '>' +
			currentText + '</a></div>' : '') + (isRTL ? prev : next) + '</div>';
		var firstDay = this._get(inst, 'firstDay');
		var changeFirstDay = this._get(inst, 'changeFirstDay');
		var dayNames = this._get(inst, 'dayNames');
		var dayNamesShort = this._get(inst, 'dayNamesShort');
		var dayNamesMin = this._get(inst, 'dayNamesMin');
		var monthNames = this._get(inst, 'monthNames');
		var beforeShowDay = this._get(inst, 'beforeShowDay');
		var highlightWeek = this._get(inst, 'highlightWeek');
		var showOtherMonths = this._get(inst, 'showOtherMonths');
		var showWeeks = this._get(inst, 'showWeeks');
		var calculateWeek = this._get(inst, 'calculateWeek') || this.iso8601Week;
		var weekStatus = this._get(inst, 'weekStatus');
		var status = (showStatus ? this._get(inst, 'dayStatus') || initStatus : '');
		var dateStatus = this._get(inst, 'statusForDate') || this.dateStatus;
		var endDate = inst.endDay ? new Date(inst.endYear, inst.endMonth, inst.endDay) : currentDate;
		for (var row = 0; row < numMonths[0]; row++)
			for (var col = 0; col < numMonths[1]; col++) {
				var selectedDate = new Date(drawYear, drawMonth, inst.selectedDay);
				html += '<div class="ui-datepicker-one-month' + (col == 0 ? ' ui-datepicker-new-row' : '') + '">' +
					this._generateMonthYearHeader(inst, drawMonth, drawYear, minDate, maxDate,
					selectedDate, row > 0 || col > 0, showStatus, initStatus, monthNames) + // draw month headers
					'<table class="ui-datepicker" cellpadding="0" cellspacing="0"><thead>' + 
					'<tr class="ui-datepicker-title-row">' +
					(showWeeks ? '<td' + this._addStatus(showStatus, inst.id, weekStatus, initStatus) + '>' +
					this._get(inst, 'weekHeader') + '</td>' : '');
				for (var dow = 0; dow < 7; dow++) { // days of the week
					var day = (dow + firstDay) % 7;
					var dayStatus = (status.indexOf('DD') > -1 ? status.replace(/DD/, dayNames[day]) :
						status.replace(/D/, dayNamesShort[day]));
					html += '<td' + ((dow + firstDay + 6) % 7 >= 5 ? ' class="ui-datepicker-week-end-cell"' : '') + '>' +
						(!changeFirstDay ? '<span' :
						'<a onclick="jQuery.datepicker._changeFirstDay(\'#' + inst.id + '\', ' + day + ');"') + 
						this._addStatus(showStatus, inst.id, dayStatus, initStatus) + ' title="' + dayNames[day] + '">' +
						dayNamesMin[day] + (changeFirstDay ? '</a>' : '</span>') + '</td>';
				}
				html += '</tr></thead><tbody>';
				var daysInMonth = this._getDaysInMonth(drawYear, drawMonth);
				if (drawYear == inst.selectedYear && drawMonth == inst.selectedMonth)
					inst.selectedDay = Math.min(inst.selectedDay, daysInMonth);
				var leadDays = (this._getFirstDayOfMonth(drawYear, drawMonth) - firstDay + 7) % 7;
				var tzDate = new Date(drawYear, drawMonth, 1 - leadDays);
				var utcDate = new Date(drawYear, drawMonth, 1 - leadDays);
				var printDate = utcDate;
				var numRows = (isMultiMonth ? 6 : Math.ceil((leadDays + daysInMonth) / 7)); // calculate the number of rows to generate
				for (var dRow = 0; dRow < numRows; dRow++) { // create date picker rows
					html += '<tr class="ui-datepicker-days-row">' +
						(showWeeks ? '<td class="ui-datepicker-week-col"' +
						this._addStatus(showStatus, inst.id, weekStatus, initStatus) + '>' +
						calculateWeek(printDate) + '</td>' : '');
					for (var dow = 0; dow < 7; dow++) { // create date picker days
						var daySettings = (beforeShowDay ?
							beforeShowDay.apply((inst.input ? inst.input[0] : null), [printDate]) : [true, '']);
						var otherMonth = (printDate.getMonth() != drawMonth);
						var unselectable = otherMonth || !daySettings[0] ||
							(minDate && printDate < minDate) || (maxDate && printDate > maxDate);
						html += '<td class="ui-datepicker-days-cell' +
							((dow + firstDay + 6) % 7 >= 5 ? ' ui-datepicker-week-end-cell' : '') + // highlight weekends
							(otherMonth ? ' ui-datepicker-other-month' : '') + // highlight days from other months
							(printDate.getTime() == selectedDate.getTime() && drawMonth == inst.selectedMonth ?
							' ui-datepicker-days-cell-over' : '') + // highlight selected day
							(unselectable ? ' ' + this._unselectableClass : '') +  // highlight unselectable days
							(otherMonth && !showOtherMonths ? '' : ' ' + daySettings[1] + // highlight custom dates
							(printDate.getTime() >= currentDate.getTime() && printDate.getTime() <= endDate.getTime() ?  // in current range
							' ' + this._currentClass : '') + // highlight selected day
							(printDate.getTime() == today.getTime() ? ' ui-datepicker-today' : '')) + '"' + // highlight today (if different)
							((!otherMonth || showOtherMonths) && daySettings[2] ? ' title="' + daySettings[2] + '"' : '') + // cell title
							(unselectable ? (highlightWeek ? ' onmouseover="jQuery(this).parent().addClass(\'ui-datepicker-week-over\');"' + // highlight selection week
							' onmouseout="jQuery(this).parent().removeClass(\'ui-datepicker-week-over\');"' : '') : // unhighlight selection week
							' onmouseover="jQuery(this).addClass(\'ui-datepicker-days-cell-over\')' + // highlight selection
							(highlightWeek ? '.parent().addClass(\'ui-datepicker-week-over\')' : '') + ';' + // highlight selection week
							(!showStatus || (otherMonth && !showOtherMonths) ? '' : 'jQuery(\'#ui-datepicker-status-' +
							inst.id + '\').html(\'' + (dateStatus.apply((inst.input ? inst.input[0] : null),
							[printDate, inst]) || initStatus) +'\');') + '"' +
							' onmouseout="jQuery(this).removeClass(\'ui-datepicker-days-cell-over\')' + // unhighlight selection
							(highlightWeek ? '.parent().removeClass(\'ui-datepicker-week-over\')' : '') + ';' + // unhighlight selection week
							(!showStatus || (otherMonth && !showOtherMonths) ? '' : 'jQuery(\'#ui-datepicker-status-' +
							inst.id + '\').html(\'' + initStatus + '\');') + '" onclick="jQuery.datepicker._selectDay(\'#' +
							inst.id + '\',' + drawMonth + ',' + drawYear + ', this);"') + '>' + // actions
							(otherMonth ? (showOtherMonths ? printDate.getDate() : '&#xa0;') : // display for other months
							(unselectable ? printDate.getDate() : '<a>' + printDate.getDate() + '</a>')) + '</td>'; // display for this month
						tzDate.setDate(tzDate.getDate() + 1);
						utcDate.setUTCDate(utcDate.getUTCDate() + 1);
						printDate = (tzDate > utcDate ? tzDate : utcDate);
					}
					html += '</tr>';
				}
				drawMonth++;
				if (drawMonth > 11) {
					drawMonth = 0;
					drawYear++;
				}
				html += '</tbody></table></div>';
			}
		html += (showStatus ? '<div style="clear: both;"></div><div id="ui-datepicker-status-' + inst.id + 
			'" class="ui-datepicker-status">' + initStatus + '</div>' : '') +
			(!closeAtTop && !inst.inline ? controls : '') +
			'<div style="clear: both;"></div>' + 
			($.browser.msie && parseInt($.browser.version) < 7 && !inst.inline ? 
			'<iframe src="javascript:false;" class="ui-datepicker-cover"></iframe>' : '');
		return html;
	},
	
	/* Generate the month and year header. */
	_generateMonthYearHeader: function(inst, drawMonth, drawYear, minDate, maxDate,
			selectedDate, secondary, showStatus, initStatus, monthNames) {
		minDate = (inst.rangeStart && minDate && selectedDate < minDate ? selectedDate : minDate);
		var monthAfterYear = this._get(inst, 'monthAfterYear');
		var html = '<div class="ui-datepicker-header">';
		var monthHtml = '';
		// month selection
		if (secondary || !this._get(inst, 'changeMonth'))
			monthHtml += monthNames[drawMonth] + '&#xa0;';
		else {
			var inMinYear = (minDate && minDate.getFullYear() == drawYear);
			var inMaxYear = (maxDate && maxDate.getFullYear() == drawYear);
			monthHtml += '<select class="ui-datepicker-new-month" ' +
				'onchange="jQuery.datepicker._selectMonthYear(\'#' + inst.id + '\', this, \'M\');" ' +
				'onclick="jQuery.datepicker._clickMonthYear(\'#' + inst.id + '\');"' +
				this._addStatus(showStatus, inst.id, this._get(inst, 'monthStatus'), initStatus) + '>';
			for (var month = 0; month < 12; month++) {
				if ((!inMinYear || month >= minDate.getMonth()) &&
						(!inMaxYear || month <= maxDate.getMonth()))
					monthHtml += '<option value="' + month + '"' +
						(month == drawMonth ? ' selected="selected"' : '') +
						'>' + monthNames[month] + '</option>';
			}
			monthHtml += '</select>';
		}
		if (!monthAfterYear)
			html += monthHtml;
		// year selection
		if (secondary || !this._get(inst, 'changeYear'))
			html += drawYear;
		else {
			// determine range of years to display
			var years = this._get(inst, 'yearRange').split(':');
			var year = 0;
			var endYear = 0;
			if (years.length != 2) {
				year = drawYear - 10;
				endYear = drawYear + 10;
			} else if (years[0].charAt(0) == '+' || years[0].charAt(0) == '-') {
				year = endYear = new Date().getFullYear();
				year += parseInt(years[0], 10);
				endYear += parseInt(years[1], 10);
			} else {
				year = parseInt(years[0], 10);
				endYear = parseInt(years[1], 10);
			}
			year = (minDate ? Math.max(year, minDate.getFullYear()) : year);
			endYear = (maxDate ? Math.min(endYear, maxDate.getFullYear()) : endYear);
			html += '<select class="ui-datepicker-new-year" ' +
				'onchange="jQuery.datepicker._selectMonthYear(\'#' + inst.id + '\', this, \'Y\');" ' +
				'onclick="jQuery.datepicker._clickMonthYear(\'#' + inst.id + '\');"' +
				this._addStatus(showStatus, inst.id, this._get(inst, 'yearStatus'), initStatus) + '>';
			for (; year <= endYear; year++) {
				html += '<option value="' + year + '"' +
					(year == drawYear ? ' selected="selected"' : '') +
					'>' + year + '</option>';
			}
			html += '</select>';
		}
		if (monthAfterYear)
			html += monthHtml;
		html += '</div>'; // Close datepicker_header
		return html;
	},

	/* Provide code to set and clear the status panel. */
	_addStatus: function(showStatus, id, text, initStatus) {
		return (showStatus ? ' onmouseover="jQuery(\'#ui-datepicker-status-' + id +
			'\').html(\'' + (text || initStatus) + '\');" ' +
			'onmouseout="jQuery(\'#ui-datepicker-status-' + id +
			'\').html(\'' + initStatus + '\');"' : '');
	},

	/* Adjust one of the date sub-fields. */
	_adjustInstDate: function(inst, offset, period) {
		var year = inst.drawYear + (period == 'Y' ? offset : 0);
		var month = inst.drawMonth + (period == 'M' ? offset : 0);
		var day = Math.min(inst.selectedDay, this._getDaysInMonth(year, month)) +
			(period == 'D' ? offset : 0);
		var date = new Date(year, month, day);
		// ensure it is within the bounds set
		var minDate = this._getMinMaxDate(inst, 'min', true);
		var maxDate = this._getMinMaxDate(inst, 'max');
		date = (minDate && date < minDate ? minDate : date);
		date = (maxDate && date > maxDate ? maxDate : date);
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		if (period == 'M' || period == 'Y')
			this._notifyChange(inst);
	},

	/* Notify change of month/year. */
	_notifyChange: function(inst) {
		var onChange = this._get(inst, 'onChangeMonthYear');
		if (onChange)
			onChange.apply((inst.input ? inst.input[0] : null),
				[new Date(inst.selectedYear, inst.selectedMonth, 1), inst]);
	},
	
	/* Determine the number of months to show. */
	_getNumberOfMonths: function(inst) {
		var numMonths = this._get(inst, 'numberOfMonths');
		return (numMonths == null ? [1, 1] : (typeof numMonths == 'number' ? [1, numMonths] : numMonths));
	},

	/* Determine the current maximum date - ensure no time components are set - may be overridden for a range. */
	_getMinMaxDate: function(inst, minMax, checkRange) {
		var date = this._determineDate(this._get(inst, minMax + 'Date'), null);
		if (date) {
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			date.setMilliseconds(0);
		}
		return (!checkRange || !inst.rangeStart ? date :
			(!date || inst.rangeStart > date ? inst.rangeStart : date));
	},

	/* Find the number of days in a given month. */
	_getDaysInMonth: function(year, month) {
		return 32 - new Date(year, month, 32).getDate();
	},

	/* Find the day of the week of the first of a month. */
	_getFirstDayOfMonth: function(year, month) {
		return new Date(year, month, 1).getDay();
	},

	/* Determines if we should allow a "next/prev" month display change. */
	_canAdjustMonth: function(inst, offset, curYear, curMonth) {
		var numMonths = this._getNumberOfMonths(inst);
		var date = new Date(curYear, curMonth + (offset < 0 ? offset : numMonths[1]), 1);
		if (offset < 0)
			date.setDate(this._getDaysInMonth(date.getFullYear(), date.getMonth()));
		return this._isInRange(inst, date);
	},

	/* Is the given date in the accepted range? */
	_isInRange: function(inst, date) {
		// during range selection, use minimum of selected date and range start
		var newMinDate = (!inst.rangeStart ? null :
			new Date(inst.selectedYear, inst.selectedMonth, inst.selectedDay));
		newMinDate = (newMinDate && inst.rangeStart < newMinDate ? inst.rangeStart : newMinDate);
		var minDate = newMinDate || this._getMinMaxDate(inst, 'min');
		var maxDate = this._getMinMaxDate(inst, 'max');
		return ((!minDate || date >= minDate) && (!maxDate || date <= maxDate));
	},
	
	/* Provide the configuration settings for formatting/parsing. */
	_getFormatConfig: function(inst) {
		var shortYearCutoff = this._get(inst, 'shortYearCutoff');
		shortYearCutoff = (typeof shortYearCutoff != 'string' ? shortYearCutoff :
			new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
		return {shortYearCutoff: shortYearCutoff,
			dayNamesShort: this._get(inst, 'dayNamesShort'), dayNames: this._get(inst, 'dayNames'),
			monthNamesShort: this._get(inst, 'monthNamesShort'), monthNames: this._get(inst, 'monthNames')};
	},

	/* Format the given date for display. */
	_formatDate: function(inst, day, month, year) {
		if (!day) {
			inst.currentDay = inst.selectedDay;
			inst.currentMonth = inst.selectedMonth;
			inst.currentYear = inst.selectedYear;
		}
		var date = (day ? (typeof day == 'object' ? day : new Date(year, month, day)) :
			new Date(inst.currentYear, inst.currentMonth, inst.currentDay));
		return this.formatDate(this._get(inst, 'dateFormat'), date, this._getFormatConfig(inst));
	}
});

/* jQuery extend now ignores nulls! */
function extendRemove(target, props) {
	$.extend(target, props);
	for (var name in props)
		if (props[name] == null || props[name] == undefined)
			target[name] = props[name];
	return target;
};

/* Determine whether an object is an array. */
function isArray(a) {
	return (a && (($.browser.safari && typeof a == 'object' && a.length) ||
		(a.constructor && a.constructor.toString().match(/\Array\(\)/))));
};

/* Invoke the datepicker functionality.
   @param  options  string - a command, optionally followed by additional parameters or
                    Object - settings for attaching new datepicker functionality
   @return  jQuery object */
$.fn.datepicker = function(options){
	
	/* Initialise the date picker. */
	if (!$.datepicker.initialized) {
		$(document.body)
			.append($.datepicker.dpDiv)
			.mousedown($.datepicker._checkExternalClick);
		$.datepicker.initialized = true;
	}
	
	var otherArgs = Array.prototype.slice.call(arguments, 1);
	if (typeof options == 'string' && (options == 'isDisabled' || options == 'getDate'))
		return $.datepicker['_' + options + 'Datepicker'].
			apply($.datepicker, [this[0]].concat(otherArgs));
	return this.each(function() {
		typeof options == 'string' ?
			$.datepicker['_' + options + 'Datepicker'].
				apply($.datepicker, [this].concat(otherArgs)) :
			$.datepicker._attachDatepicker(this, options);
	});
};

$.datepicker = new Datepicker(); // singleton instance
$.datepicker.initialized = false;

})(jQuery);

/*
 * jQuery UI Dialog
 *
 * Copyright (c) 2008 Richard D. Worth (rdworth.org)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Dialog
 *
 * Depends:
 *	ui.core.js
 *	ui.draggable.js
 *	ui.resizable.js
 */
(function($) {

var setDataSwitch = {
	dragStart: "start.draggable",
	drag: "drag.draggable",
	dragStop: "stop.draggable",
	maxHeight: "maxHeight.resizable",
	minHeight: "minHeight.resizable",
	maxWidth: "maxWidth.resizable",
	minWidth: "minWidth.resizable",
	resizeStart: "start.resizable",
	resize: "drag.resizable",
	resizeStop: "stop.resizable"
};

$.widget("ui.dialog", {
	init: function() {
		this.options.title = this.options.title || this.element.attr('title');
		
		var self = this,
			options = this.options,
			resizeHandles = typeof options.resizable == 'string'
				? options.resizable
				: 'n,e,s,w,se,sw,ne,nw',
			
			uiDialogContent = this.element
				.addClass('ui-dialog-content')
				.wrap('<div/>')
				.wrap('<div/>'),
			
			uiDialogContainer = (this.uiDialogContainer = uiDialogContent.parent())
				.addClass('ui-dialog-container')
				.css({
					position: 'relative',
					width: '100%',
					height: '100%'
				}),
			
			title = options.title || '&nbsp;',
			uiDialogTitlebar = (this.uiDialogTitlebar =
				$('<div class="ui-dialog-titlebar"/>'))
				.append('<span class="ui-dialog-title">' + title + '</span>')
				.append('<a href="#" class="ui-dialog-titlebar-close"><span>X</span></a>')
				.prependTo(uiDialogContainer),
			
			uiDialog = (this.uiDialog = uiDialogContainer.parent())
				.appendTo(document.body)
				.hide()
				.addClass('ui-dialog')
				.addClass(options.dialogClass)
				// add content classes to dialog
				// to inherit theme at top level of element
				.addClass(uiDialogContent.attr('className'))
					.removeClass('ui-dialog-content')
				.css({
					position: 'absolute',
					width: options.width,
					height: options.height,
					overflow: 'hidden',
					zIndex: options.zIndex
				})
				// setting tabIndex makes the div focusable
				// setting outline to 0 prevents a border on focus in Mozilla
				.attr('tabIndex', -1).css('outline', 0).keydown(function(ev) {
					if (options.closeOnEscape) {
						var ESC = 27;
						(ev.keyCode && ev.keyCode == ESC && self.close());
					}
				})
				.mousedown(function() {
					self.moveToTop();
				}),
			
			uiDialogButtonPane = (this.uiDialogButtonPane = $('<div/>'))
				.addClass('ui-dialog-buttonpane')
				.css({
					position: 'absolute',
					bottom: 0
				})
				.appendTo(uiDialog);
		
		this.uiDialogTitlebarClose = $('.ui-dialog-titlebar-close', uiDialogTitlebar)
			.hover(
				function() {
					$(this).addClass('ui-dialog-titlebar-close-hover');
				},
				function() {
					$(this).removeClass('ui-dialog-titlebar-close-hover');
				}
			)
			.mousedown(function(ev) {
				ev.stopPropagation();
			})
			.click(function() {
				self.close();
				return false;
			});
		
		uiDialogTitlebar.find("*").add(uiDialogTitlebar).each(function() {
			$.ui.disableSelection(this);
		});
		
		if ($.fn.draggable) {
			uiDialog.draggable({
				cancel: '.ui-dialog-content',
				helper: options.dragHelper,
				handle: '.ui-dialog-titlebar',
				start: function() {
					self.moveToTop();
					(options.dragStart && options.dragStart.apply(self.element[0], arguments));
				},
				drag: function() {
					(options.drag && options.drag.apply(self.element[0], arguments));
				},
				stop: function() {
					(options.dragStop && options.dragStop.apply(self.element[0], arguments));
					$.ui.dialog.overlay.resize();
				}
			});
			(options.draggable || uiDialog.draggable('disable'));
		}
		
		if ($.fn.resizable) {
			uiDialog.resizable({
				cancel: '.ui-dialog-content',
				helper: options.resizeHelper,
				maxWidth: options.maxWidth,
				maxHeight: options.maxHeight,
				minWidth: options.minWidth,
				minHeight: options.minHeight,
				start: function() {
					(options.resizeStart && options.resizeStart.apply(self.element[0], arguments));
				},
				resize: function() {
					(options.autoResize && self.size.apply(self));
					(options.resize && options.resize.apply(self.element[0], arguments));
				},
				handles: resizeHandles,
				stop: function() {
					(options.autoResize && self.size.apply(self));
					(options.resizeStop && options.resizeStop.apply(self.element[0], arguments));
					$.ui.dialog.overlay.resize();
				}
			});
			(options.resizable || uiDialog.resizable('disable'));
		}
		
		this.createButtons(options.buttons);
		this.isOpen = false;
		
		(options.bgiframe && $.fn.bgiframe && uiDialog.bgiframe());
		(options.autoOpen && this.open());
	},
	
	setData: function(key, value){
		(setDataSwitch[key] && this.uiDialog.data(setDataSwitch[key], value));
		switch (key) {
			case "buttons":
				this.createButtons(value);
				break;
			case "draggable":
				this.uiDialog.draggable(value ? 'enable' : 'disable');
				break;
			case "height":
				this.uiDialog.height(value);
				break;
			case "position":
				this.position(value);
				break;
			case "resizable":
				(typeof value == 'string' && this.uiDialog.data('handles.resizable', value));
				this.uiDialog.resizable(value ? 'enable' : 'disable');
				break;
			case "title":
				$(".ui-dialog-title", this.uiDialogTitlebar).html(value || '&nbsp;');
				break;
			case "width":
				this.uiDialog.width(value);
				break;
		}
		
		$.widget.prototype.setData.apply(this, arguments);
	},
	
	position: function(pos) {
		var wnd = $(window), doc = $(document),
			pTop = doc.scrollTop(), pLeft = doc.scrollLeft(),
			minTop = pTop;
		
		if ($.inArray(pos, ['center','top','right','bottom','left']) >= 0) {
			pos = [
				pos == 'right' || pos == 'left' ? pos : 'center',
				pos == 'top' || pos == 'bottom' ? pos : 'middle'
			];
		}
		if (pos.constructor != Array) {
			pos = ['center', 'middle'];
		}
		if (pos[0].constructor == Number) {
			pLeft += pos[0];
		} else {
			switch (pos[0]) {
				case 'left':
					pLeft += 0;
					break;
				case 'right':
					pLeft += wnd.width() - this.uiDialog.width();
					break;
				default:
				case 'center':
					pLeft += (wnd.width() - this.uiDialog.width()) / 2;
			}
		}
		if (pos[1].constructor == Number) {
			pTop += pos[1];
		} else {
			switch (pos[1]) {
				case 'top':
					pTop += 0;
					break;
				case 'bottom':
					pTop += wnd.height() - this.uiDialog.height();
					break;
				default:
				case 'middle':
					pTop += (wnd.height() - this.uiDialog.height()) / 2;
			}
		}
		
		// prevent the dialog from being too high (make sure the titlebar
		// is accessible)
		pTop = Math.max(pTop, minTop);
		this.uiDialog.css({top: pTop, left: pLeft});
	},
	
	size: function() {
		var container = this.uiDialogContainer,
			titlebar = this.uiDialogTitlebar,
			content = this.element,
			tbMargin = (parseInt(content.css('margin-top'), 10) || 0)
				+ (parseInt(content.css('margin-bottom'), 10) || 0),
			lrMargin = (parseInt(content.css('margin-left'), 10) || 0)
				+ (parseInt(content.css('margin-right'), 10) || 0);
		content.height(container.height() - titlebar.outerHeight() - tbMargin);
		content.width(container.width() - lrMargin);
	},
	
	open: function() {
		if (this.isOpen) { return; }
		
		this.overlay = this.options.modal ? new $.ui.dialog.overlay(this) : null;
		(this.uiDialog.next().length && this.uiDialog.appendTo('body'));
		this.position(this.options.position);
		this.uiDialog.show(this.options.show);
		(this.options.autoResize && this.size());
		this.moveToTop(true);
		
		this.trigger('open', null, { options: this.options });
		this.isOpen = true;
	},
	
	// the force parameter allows us to move modal dialogs to their correct
	// position on open
	moveToTop: function(force) {
		
		if ((this.options.modal && !force)
			|| (!this.options.stack && !this.options.modal)) {
			return this.trigger('focus', null, { options: this.options });
		}
		
		var maxZ = this.options.zIndex, options = this.options;
		$('.ui-dialog:visible').each(function() {
			maxZ = Math.max(maxZ, parseInt($(this).css('z-index'), 10) || options.zIndex);
		});
		(this.overlay && this.overlay.$el.css('z-index', ++maxZ));
		this.uiDialog.css('z-index', ++maxZ);
		
		this.trigger('focus', null, { options: this.options });
	},
	
	close: function() {
		(this.overlay && this.overlay.destroy());
		this.uiDialog.hide(this.options.hide);
		
		this.trigger('close', null, { options: this.options });
		$.ui.dialog.overlay.resize();
		
		this.isOpen = false;
	},
	
	destroy: function() {
		(this.overlay && this.overlay.destroy());
		this.uiDialog.hide();
		this.element
			.unbind('.dialog')
			.removeData('dialog')
			.removeClass('ui-dialog-content')
			.hide().appendTo('body');
		this.uiDialog.remove();
	},
	
	createButtons: function(buttons) {
		var self = this,
			hasButtons = false,
			uiDialogButtonPane = this.uiDialogButtonPane;
		
		// remove any existing buttons
		uiDialogButtonPane.empty().hide();
		
		$.each(buttons, function() { return !(hasButtons = true); });
		if (hasButtons) {
			uiDialogButtonPane.show();
			$.each(buttons, function(name, fn) {
				$('<button/>')
					.text(name)
					.click(function() { fn.apply(self.element[0], arguments); })
					.appendTo(uiDialogButtonPane);
			});
		}
	},
	
	fakeEvent: function(type) {
		return $.event.fix({
			type: type,
			target: this.element[0]
		});
	}
});

$.extend($.ui.dialog, {
	defaults: {
		autoOpen: true,
		autoResize: true,
		bgiframe: false,
		buttons: {},
		closeOnEscape: true,
		draggable: true,
		height: 200,
		minHeight: 100,
		minWidth: 150,
		modal: false,
		overlay: {},
		position: 'center',
		resizable: true,
		stack: true,
		width: 300,
		zIndex: 1000
	},
	
	overlay: function(dialog) {
		this.$el = $.ui.dialog.overlay.create(dialog);
	}
});

$.extend($.ui.dialog.overlay, {
	instances: [],
	events: $.map('focus,mousedown,mouseup,keydown,keypress,click'.split(','),
		function(e) { return e + '.dialog-overlay'; }).join(' '),
	create: function(dialog) {
		if (this.instances.length === 0) {
			// prevent use of anchors and inputs
			// we use a setTimeout in case the overlay is created from an
			// event that we're going to be cancelling (see #2804)
			setTimeout(function() {
				$('a, :input').bind($.ui.dialog.overlay.events, function() {
					// allow use of the element if inside a dialog and
					// - there are no modal dialogs
					// - there are modal dialogs, but we are in front of the topmost modal
					var allow = false;
					var $dialog = $(this).parents('.ui-dialog');
					if ($dialog.length) {
						var $overlays = $('.ui-dialog-overlay');
						if ($overlays.length) {
							var maxZ = parseInt($overlays.css('z-index'), 10);
							$overlays.each(function() {
								maxZ = Math.max(maxZ, parseInt($(this).css('z-index'), 10));
							});
							allow = parseInt($dialog.css('z-index'), 10) > maxZ;
						} else {
							allow = true;
						}
					}
					return allow;
				});
			}, 1);
			
			// allow closing by pressing the escape key
			$(document).bind('keydown.dialog-overlay', function(e) {
				var ESC = 27;
				(e.keyCode && e.keyCode == ESC && dialog.close()); 
			});
			
			// handle window resize
			$(window).bind('resize.dialog-overlay', $.ui.dialog.overlay.resize);
		}
		
		var $el = $('<div/>').appendTo(document.body)
			.addClass('ui-dialog-overlay').css($.extend({
				borderWidth: 0, margin: 0, padding: 0,
				position: 'absolute', top: 0, left: 0,
				width: this.width(),
				height: this.height()
			}, dialog.options.overlay));
		
		(dialog.options.bgiframe && $.fn.bgiframe && $el.bgiframe());
		
		this.instances.push($el);
		return $el;
	},
	
	destroy: function($el) {
		this.instances.splice($.inArray(this.instances, $el), 1);
		
		if (this.instances.length === 0) {
			$('a, :input').add([document, window]).unbind('.dialog-overlay');
		}
		
		$el.remove();
	},
	
	height: function() {
		// handle IE 6
		if ($.browser.msie && $.browser.version < 7) {
			var scrollHeight = Math.max(
				document.documentElement.scrollHeight,
				document.body.scrollHeight
			);
			var offsetHeight = Math.max(
				document.documentElement.offsetHeight,
				document.body.offsetHeight
			);
			
			if (scrollHeight < offsetHeight) {
				return $(window).height() + 'px';
			} else {
				return scrollHeight + 'px';
			}
		// handle Opera
		} else if ($.browser.opera) {
			return Math.max(
				window.innerHeight,
				$(document).height()
			) + 'px';
		// handle "good" browsers
		} else {
			return $(document).height() + 'px';
		}
	},
	
	width: function() {
		// handle IE 6
		if ($.browser.msie && $.browser.version < 7) {
			var scrollWidth = Math.max(
				document.documentElement.scrollWidth,
				document.body.scrollWidth
			);
			var offsetWidth = Math.max(
				document.documentElement.offsetWidth,
				document.body.offsetWidth
			);
			
			if (scrollWidth < offsetWidth) {
				return $(window).width() + 'px';
			} else {
				return scrollWidth + 'px';
			}
		// handle Opera
		} else if ($.browser.opera) {
			return Math.max(
				window.innerWidth,
				$(document).width()
			) + 'px';
		// handle "good" browsers
		} else {
			return $(document).width() + 'px';
		}
	},
	
	resize: function() {
		/* If the dialog is draggable and the user drags it past the
		 * right edge of the window, the document becomes wider so we
		 * need to stretch the overlay. If the user then drags the
		 * dialog back to the left, the document will become narrower,
		 * so we need to shrink the overlay to the appropriate size.
		 * This is handled by shrinking the overlay before setting it
		 * to the full document size.
		 */
		var $overlays = $([]);
		$.each($.ui.dialog.overlay.instances, function() {
			$overlays = $overlays.add(this);
		});
		
		$overlays.css({
			width: 0,
			height: 0
		}).css({
			width: $.ui.dialog.overlay.width(),
			height: $.ui.dialog.overlay.height()
		});
	}
});

$.extend($.ui.dialog.overlay.prototype, {
	destroy: function() {
		$.ui.dialog.overlay.destroy(this.$el);
	}
});

})(jQuery);

/*
 * jQuery UI Draggable
 *
 * Copyright (c) 2008 Paul Bakaus
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Draggables
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.widget("ui.draggable", $.extend({}, $.ui.mouse, {
	init: function() {
		
		if (this.options.helper == 'original' && !(/^(?:r|a|f)/).test(this.element.css("position")))
			this.element[0].style.position = 'relative';
		
		(this.options.cssNamespace && this.element.addClass(this.options.cssNamespace+"-draggable"));
		(this.options.disabled && this.element.addClass('ui-draggable-disabled'));
		
		this.mouseInit();
		
	},
	mouseStart: function(e) {
		
		var o = this.options;
		
		if (this.helper || o.disabled || $(e.target).is('.ui-resizable-handle'))
			return false;
		
		//Check if we have a valid handle
		var handle = !this.options.handle || !$(this.options.handle, this.element).length ? true : false;
		$(this.options.handle, this.element).find("*").andSelf().each(function() {
			if(this == e.target) handle = true;
		});
		if (!handle) return false;
		
		if($.ui.ddmanager)
			$.ui.ddmanager.current = this;
		
		//Create and append the visible helper
		this.helper = $.isFunction(o.helper) ? $(o.helper.apply(this.element[0], [e])) : (o.helper == 'clone' ? this.element.clone() : this.element);
		if(!this.helper.parents('body').length) this.helper.appendTo((o.appendTo == 'parent' ? this.element[0].parentNode : o.appendTo));
		if(this.helper[0] != this.element[0] && !(/(fixed|absolute)/).test(this.helper.css("position"))) this.helper.css("position", "absolute");
		
		/*
		 * - Position generation -
		 * This block generates everything position related - it's the core of draggables.
		 */
		
		this.margins = {																				//Cache the margins
			left: (parseInt(this.element.css("marginLeft"),10) || 0),
			top: (parseInt(this.element.css("marginTop"),10) || 0)
		};		
		
		this.cssPosition = this.helper.css("position");													//Store the helper's css position
		this.offset = this.element.offset();															//The element's absolute position on the page
		this.offset = {																					//Substract the margins from the element's absolute offset
			top: this.offset.top - this.margins.top,
			left: this.offset.left - this.margins.left
		};
		
		this.offset.click = {																			//Where the click happened, relative to the element
			left: e.pageX - this.offset.left,
			top: e.pageY - this.offset.top
		};

		this.scrollTopParent = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-y'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(this.helper);
		this.scrollLeftParent = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-x'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(this.helper);
		
		this.offsetParent = this.helper.offsetParent(); var po = this.offsetParent.offset();			//Get the offsetParent and cache its position
		if(this.offsetParent[0] == document.body && $.browser.mozilla) po = { top: 0, left: 0 };		//Ugly FF3 fix
		this.offset.parent = {																			//Store its position plus border
			top: po.top + (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
			left: po.left + (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
		};
		
		var p = this.element.position();																//This is a relative to absolute position minus the actual position calculation - only used for relative positioned helpers
		this.offset.relative = this.cssPosition == "relative" ? {
			top: p.top - (parseInt(this.helper.css("top"),10) || 0) + (this.scrollTopParent[0].scrollTop || 0),
			left: p.left - (parseInt(this.helper.css("left"),10) || 0) + (this.scrollLeftParent[0].scrollLeft || 0)
		} : { top: 0, left: 0 };
		
		this.originalPosition = this.generatePosition(e);												//Generate the original position
		this.helperProportions = { width: this.helper.outerWidth(), height: this.helper.outerHeight() };//Cache the helper size
		
		if(o.cursorAt) {
			if(o.cursorAt.left != undefined) this.offset.click.left = o.cursorAt.left + this.margins.left;
			if(o.cursorAt.right != undefined) this.offset.click.left = this.helperProportions.width - o.cursorAt.right + this.margins.left;
			if(o.cursorAt.top != undefined) this.offset.click.top = o.cursorAt.top + this.margins.top;
			if(o.cursorAt.bottom != undefined) this.offset.click.top = this.helperProportions.height - o.cursorAt.bottom + this.margins.top;
		}
		
		
		/*
		 * - Position constraining -
		 * Here we prepare position constraining like grid and containment.
		 */	
		
		if(o.containment) {
			if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
			if(o.containment == 'document' || o.containment == 'window') this.containment = [
				0 - this.offset.relative.left - this.offset.parent.left,
				0 - this.offset.relative.top - this.offset.parent.top,
				$(o.containment == 'document' ? document : window).width() - this.offset.relative.left - this.offset.parent.left - this.helperProportions.width - this.margins.left - (parseInt(this.element.css("marginRight"),10) || 0),
				($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.offset.relative.top - this.offset.parent.top - this.helperProportions.height - this.margins.top - (parseInt(this.element.css("marginBottom"),10) || 0)
			];
			
			if(!(/^(document|window|parent)$/).test(o.containment)) {
				var ce = $(o.containment)[0];
				var co = $(o.containment).offset();
				
				this.containment = [
					co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) - this.offset.relative.left - this.offset.parent.left,
					co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) - this.offset.relative.top - this.offset.parent.top,
					co.left+Math.max(ce.scrollWidth,ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - this.offset.relative.left - this.offset.parent.left - this.helperProportions.width - this.margins.left - (parseInt(this.element.css("marginRight"),10) || 0),
					co.top+Math.max(ce.scrollHeight,ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - this.offset.relative.top - this.offset.parent.top - this.helperProportions.height - this.margins.top - (parseInt(this.element.css("marginBottom"),10) || 0)
				];
			}
		}
		
		//Call plugins and callbacks
		this.propagate("start", e);
		
		this.helperProportions = { width: this.helper.outerWidth(), height: this.helper.outerHeight() };//Recache the helper size
		if ($.ui.ddmanager && !o.dropBehaviour) $.ui.ddmanager.prepareOffsets(this, e);
		
		this.helper.addClass("ui-draggable-dragging");
		this.mouseDrag(e); //Execute the drag once - this causes the helper not to be visible before getting its correct position
		return true;
	},
	convertPositionTo: function(d, pos) {
		if(!pos) pos = this.position;
		var mod = d == "absolute" ? 1 : -1;
		return {
			top: (
				pos.top																	// the calculated relative position
				+ this.offset.relative.top	* mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.top * mod											// The offsetParent's offset without borders (offset + border)
				- (this.cssPosition == "fixed" || (this.cssPosition == "absolute" && this.offsetParent[0] == document.body) ? 0 : this.scrollTopParent[0].scrollTop) * mod	// The offsetParent's scroll position, not if the element is fixed
				+ (this.cssPosition == "fixed" ? $(document).scrollTop() : 0) * mod
				+ this.margins.top * mod												//Add the margin (you don't want the margin counting in intersection methods)
			),
			left: (
				pos.left																// the calculated relative position
				+ this.offset.relative.left	* mod										// Only for relative positioned nodes: Relative offset from element to offset parent
				+ this.offset.parent.left * mod											// The offsetParent's offset without borders (offset + border)
				- (this.cssPosition == "fixed" || (this.cssPosition == "absolute" && this.offsetParent[0] == document.body) ? 0 : this.scrollLeftParent[0].scrollLeft) * mod	// The offsetParent's scroll position, not if the element is fixed
				+ (this.cssPosition == "fixed" ? $(document).scrollLeft() : 0) * mod
				+ this.margins.left * mod												//Add the margin (you don't want the margin counting in intersection methods)
			)
		};
	},
	generatePosition: function(e) {

		var o = this.options;
		var position = {
			top: (
				e.pageY																	// The absolute mouse position
				- this.offset.click.top													// Click offset (relative to the element)
				- this.offset.relative.top												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.top												// The offsetParent's offset without borders (offset + border)
				+ (this.cssPosition == "fixed" || (this.cssPosition == "absolute" && this.offsetParent[0] == document.body) ? 0 : (this.scrollTopParent[0].scrollTop || 0))	// The offsetParent's scroll position, not if the element is fixed
				- (this.cssPosition == "fixed" ? $(document).scrollTop() : 0)
			),
			left: (
				e.pageX																	// The absolute mouse position
				- this.offset.click.left												// Click offset (relative to the element)
				- this.offset.relative.left												// Only for relative positioned nodes: Relative offset from element to offset parent
				- this.offset.parent.left												// The offsetParent's offset without borders (offset + border)
				+ (this.cssPosition == "fixed" || (this.cssPosition == "absolute" && this.offsetParent[0] == document.body) ? 0 : (this.scrollLeftParent[0].scrollLeft || 0))	// The offsetParent's scroll position, not if the element is fixed
				- (this.cssPosition == "fixed" ? $(document).scrollLeft() : 0)
			)
		};
	
		if(!this.originalPosition) return position;										//If we are not dragging yet, we won't check for options
		
		/*
		 * - Position constraining -
		 * Constrain the position to a mix of grid, containment.
		 */
		if(this.containment) {
			if(position.left < this.containment[0]) position.left = this.containment[0];
			if(position.top < this.containment[1]) position.top = this.containment[1];
			if(position.left > this.containment[2]) position.left = this.containment[2];
			if(position.top > this.containment[3]) position.top = this.containment[3];
		}
		
		if(o.grid) {
			var top = this.originalPosition.top + Math.round((position.top - this.originalPosition.top) / o.grid[1]) * o.grid[1];
			position.top = this.containment ? (!(top < this.containment[1] || top > this.containment[3]) ? top : (!(top < this.containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;
			
			var left = this.originalPosition.left + Math.round((position.left - this.originalPosition.left) / o.grid[0]) * o.grid[0];
			position.left = this.containment ? (!(left < this.containment[0] || left > this.containment[2]) ? left : (!(left < this.containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
		}
		
		return position;
	},
	mouseDrag: function(e) {
	
		//Compute the helpers position
		this.position = this.generatePosition(e);
		this.positionAbs = this.convertPositionTo("absolute");
		
		//Call plugins and callbacks and use the resulting position if something is returned		
		this.position = this.propagate("drag", e) || this.position;
	
		if(!this.options.axis || this.options.axis != "y") this.helper[0].style.left = this.position.left+'px';
		if(!this.options.axis || this.options.axis != "x") this.helper[0].style.top = this.position.top+'px';
		if($.ui.ddmanager) $.ui.ddmanager.drag(this, e);
		
		return false;
	},
	mouseStop: function(e) {
		
		//If we are using droppables, inform the manager about the drop
		var dropped = false;
		if ($.ui.ddmanager && !this.options.dropBehaviour)
			var dropped = $.ui.ddmanager.drop(this, e);		
		
		if((this.options.revert == "invalid" && !dropped) || (this.options.revert == "valid" && dropped) || this.options.revert === true) {
			var self = this;
			$(this.helper).animate(this.originalPosition, parseInt(this.options.revertDuration, 10) || 500, function() {
				self.propagate("stop", e);
				self.clear();
			});
		} else {
			this.propagate("stop", e);
			this.clear();
		}
		
		return false;
	},
	clear: function() {
		this.helper.removeClass("ui-draggable-dragging");
		if(this.options.helper != 'original' && !this.cancelHelperRemoval) this.helper.remove();
		//if($.ui.ddmanager) $.ui.ddmanager.current = null;
		this.helper = null;
		this.cancelHelperRemoval = false;
	},
	
	// From now on bulk stuff - mainly helpers
	plugins: {},
	uiHash: function(e) {
		return {
			helper: this.helper,
			position: this.position,
			absolutePosition: this.positionAbs,
			options: this.options			
		};
	},
	propagate: function(n,e) {
		$.ui.plugin.call(this, n, [e, this.uiHash()]);
		if(n == "drag") this.positionAbs = this.convertPositionTo("absolute"); //The absolute position has to be recalculated after plugins
		return this.element.triggerHandler(n == "drag" ? n : "drag"+n, [e, this.uiHash()], this.options[n]);
	},
	destroy: function() {
		if(!this.element.data('draggable')) return;
		this.element.removeData("draggable").unbind(".draggable").removeClass('ui-draggable-dragging ui-draggable-disabled');
		this.mouseDestroy();
	}
}));

$.extend($.ui.draggable, {
	defaults: {
		appendTo: "parent",
		axis: false,
		cancel: ":input",
		delay: 0,
		distance: 1,
		helper: "original",
		scope: "default",
		cssNamespace: "ui"
	}
});

$.ui.plugin.add("draggable", "cursor", {
	start: function(e, ui) {
		var t = $('body');
		if (t.css("cursor")) ui.options._cursor = t.css("cursor");
		t.css("cursor", ui.options.cursor);
	},
	stop: function(e, ui) {
		if (ui.options._cursor) $('body').css("cursor", ui.options._cursor);
	}
});

$.ui.plugin.add("draggable", "zIndex", {
	start: function(e, ui) {
		var t = $(ui.helper);
		if(t.css("zIndex")) ui.options._zIndex = t.css("zIndex");
		t.css('zIndex', ui.options.zIndex);
	},
	stop: function(e, ui) {
		if(ui.options._zIndex) $(ui.helper).css('zIndex', ui.options._zIndex);
	}
});

$.ui.plugin.add("draggable", "opacity", {
	start: function(e, ui) {
		var t = $(ui.helper);
		if(t.css("opacity")) ui.options._opacity = t.css("opacity");
		t.css('opacity', ui.options.opacity);
	},
	stop: function(e, ui) {
		if(ui.options._opacity) $(ui.helper).css('opacity', ui.options._opacity);
	}
});

$.ui.plugin.add("draggable", "iframeFix", {
	start: function(e, ui) {
		$(ui.options.iframeFix === true ? "iframe" : ui.options.iframeFix).each(function() {					
			$('<div class="ui-draggable-iframeFix" style="background: #fff;"></div>')
			.css({
				width: this.offsetWidth+"px", height: this.offsetHeight+"px",
				position: "absolute", opacity: "0.001", zIndex: 1000
			})
			.css($(this).offset())
			.appendTo("body");
		});
	},
	stop: function(e, ui) {
		$("div.DragDropIframeFix").each(function() { this.parentNode.removeChild(this); }); //Remove frame helpers	
	}
});



$.ui.plugin.add("draggable", "scroll", {
	start: function(e, ui) {
		var o = ui.options;
		var i = $(this).data("draggable");
		o.scrollSensitivity	= o.scrollSensitivity || 20;
		o.scrollSpeed		= o.scrollSpeed || 20;
		
		i.overflowY = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-y'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(this);
		i.overflowX = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-x'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(this);
		
		if(i.overflowY[0] != document && i.overflowY[0].tagName != 'HTML') i.overflowYOffset = i.overflowY.offset();
		if(i.overflowX[0] != document && i.overflowX[0].tagName != 'HTML') i.overflowXOffset = i.overflowX.offset();
		
	},
	drag: function(e, ui) {
		
		var o = ui.options, scrolled = false;
		var i = $(this).data("draggable");
		
		if(i.overflowY[0] != document && i.overflowY[0].tagName != 'HTML') {
			if((i.overflowYOffset.top + i.overflowY[0].offsetHeight) - e.pageY < o.scrollSensitivity)
				i.overflowY[0].scrollTop = scrolled = i.overflowY[0].scrollTop + o.scrollSpeed;
			if(e.pageY - i.overflowYOffset.top < o.scrollSensitivity)
				i.overflowY[0].scrollTop = scrolled = i.overflowY[0].scrollTop - o.scrollSpeed;
							
		} else {
			if(e.pageY - $(document).scrollTop() < o.scrollSensitivity)
				scrolled = $(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
			if($(window).height() - (e.pageY - $(document).scrollTop()) < o.scrollSensitivity)
				scrolled = $(document).scrollTop($(document).scrollTop() + o.scrollSpeed);
		}
		
		if(i.overflowX[0] != document && i.overflowX[0].tagName != 'HTML') {
			if((i.overflowXOffset.left + i.overflowX[0].offsetWidth) - e.pageX < o.scrollSensitivity)
				i.overflowX[0].scrollLeft = scrolled = i.overflowX[0].scrollLeft + o.scrollSpeed;
			if(e.pageX - i.overflowXOffset.left < o.scrollSensitivity)
				i.overflowX[0].scrollLeft = scrolled = i.overflowX[0].scrollLeft - o.scrollSpeed;
		} else {
			if(e.pageX - $(document).scrollLeft() < o.scrollSensitivity)
				scrolled = $(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
			if($(window).width() - (e.pageX - $(document).scrollLeft()) < o.scrollSensitivity)
				scrolled = $(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);
		}
		
		if(scrolled !== false)
			$.ui.ddmanager.prepareOffsets(i, e);
		
	}
});


$.ui.plugin.add("draggable", "snap", {
	start: function(e, ui) {
		
		var inst = $(this).data("draggable");
		inst.snapElements = [];

		$(ui.options.snap.constructor != String ? ( ui.options.snap.items || ':data(draggable)' ) : ui.options.snap).each(function() {
			var $t = $(this); var $o = $t.offset();
			if(this != inst.element[0]) inst.snapElements.push({
				item: this,
				width: $t.outerWidth(), height: $t.outerHeight(),
				top: $o.top, left: $o.left
			});
		});
		
	},
	drag: function(e, ui) {
	
		var inst = $(this).data("draggable");
		var d = ui.options.snapTolerance || 20;
		var x1 = ui.absolutePosition.left, x2 = x1 + inst.helperProportions.width,
			y1 = ui.absolutePosition.top, y2 = y1 + inst.helperProportions.height;
		
		for (var i = inst.snapElements.length - 1; i >= 0; i--){
			
			var l = inst.snapElements[i].left, r = l + inst.snapElements[i].width, 
				t = inst.snapElements[i].top, b = t + inst.snapElements[i].height;
			
			//Yes, I know, this is insane ;)
			if(!((l-d < x1 && x1 < r+d && t-d < y1 && y1 < b+d) || (l-d < x1 && x1 < r+d && t-d < y2 && y2 < b+d) || (l-d < x2 && x2 < r+d && t-d < y1 && y1 < b+d) || (l-d < x2 && x2 < r+d && t-d < y2 && y2 < b+d))) {
				if(inst.snapElements[i].snapping) (inst.options.snap.release && inst.options.snap.release.call(inst.element, null, $.extend(inst.uiHash(), { snapItem: inst.snapElements[i].item })));
				inst.snapElements[i].snapping = false;
				continue;
			}
			
			if(ui.options.snapMode != 'inner') {
				var ts = Math.abs(t - y2) <= 20;
				var bs = Math.abs(b - y1) <= 20;
				var ls = Math.abs(l - x2) <= 20;
				var rs = Math.abs(r - x1) <= 20;
				if(ts) ui.position.top = inst.convertPositionTo("relative", { top: t - inst.helperProportions.height, left: 0 }).top;
				if(bs) ui.position.top = inst.convertPositionTo("relative", { top: b, left: 0 }).top;
				if(ls) ui.position.left = inst.convertPositionTo("relative", { top: 0, left: l - inst.helperProportions.width }).left;
				if(rs) ui.position.left = inst.convertPositionTo("relative", { top: 0, left: r }).left;
			}
			
			var first = (ts || bs || ls || rs);
			
			if(ui.options.snapMode != 'outer') {
				var ts = Math.abs(t - y1) <= 20;
				var bs = Math.abs(b - y2) <= 20;
				var ls = Math.abs(l - x1) <= 20;
				var rs = Math.abs(r - x2) <= 20;
				if(ts) ui.position.top = inst.convertPositionTo("relative", { top: t, left: 0 }).top;
				if(bs) ui.position.top = inst.convertPositionTo("relative", { top: b - inst.helperProportions.height, left: 0 }).top;
				if(ls) ui.position.left = inst.convertPositionTo("relative", { top: 0, left: l }).left;
				if(rs) ui.position.left = inst.convertPositionTo("relative", { top: 0, left: r - inst.helperProportions.width }).left;
			}
			
			if(!inst.snapElements[i].snapping && (ts || bs || ls || rs || first))
				(inst.options.snap.snap && inst.options.snap.snap.call(inst.element, null, $.extend(inst.uiHash(), { snapItem: inst.snapElements[i].item })));
			inst.snapElements[i].snapping = (ts || bs || ls || rs || first);
			
		};

	}
});

$.ui.plugin.add("draggable", "connectToSortable", {
	start: function(e,ui) {
	
		var inst = $(this).data("draggable");
		inst.sortables = [];
		$(ui.options.connectToSortable).each(function() {
			if($.data(this, 'sortable')) {
				var sortable = $.data(this, 'sortable');
				inst.sortables.push({
					instance: sortable,
					shouldRevert: sortable.options.revert
				});
				sortable.refreshItems();	//Do a one-time refresh at start to refresh the containerCache	
				sortable.propagate("activate", e, inst);
			}
		});

	},
	stop: function(e,ui) {
		
		//If we are still over the sortable, we fake the stop event of the sortable, but also remove helper
		var inst = $(this).data("draggable");
		
		$.each(inst.sortables, function() {
			if(this.instance.isOver) {
				this.instance.isOver = 0;
				inst.cancelHelperRemoval = true; //Don't remove the helper in the draggable instance
				this.instance.cancelHelperRemoval = false; //Remove it in the sortable instance (so sortable plugins like revert still work)
				if(this.shouldRevert) this.instance.options.revert = true; //revert here
				this.instance.mouseStop(e);
				
				//Also propagate receive event, since the sortable is actually receiving a element
				this.instance.element.triggerHandler("sortreceive", [e, $.extend(this.instance.ui(), { sender: inst.element })], this.instance.options["receive"]);

				this.instance.options.helper = this.instance.options._helper;
			} else {
				this.instance.propagate("deactivate", e, inst);
			}

		});
		
	},
	drag: function(e,ui) {

		var inst = $(this).data("draggable"), self = this;
		
		var checkPos = function(o) {
				
			var l = o.left, r = l + o.width,
				t = o.top, b = t + o.height;

			return (l < (this.positionAbs.left + this.offset.click.left) && (this.positionAbs.left + this.offset.click.left) < r
					&& t < (this.positionAbs.top + this.offset.click.top) && (this.positionAbs.top + this.offset.click.top) < b);				
		};
		
		$.each(inst.sortables, function(i) {

			if(checkPos.call(inst, this.instance.containerCache)) {

				//If it intersects, we use a little isOver variable and set it once, so our move-in stuff gets fired only once
				if(!this.instance.isOver) {
					this.instance.isOver = 1;

					//Now we fake the start of dragging for the sortable instance,
					//by cloning the list group item, appending it to the sortable and using it as inst.currentItem
					//We can then fire the start event of the sortable with our passed browser event, and our own helper (so it doesn't create a new one)
					this.instance.currentItem = $(self).clone().appendTo(this.instance.element).data("sortable-item", true);
					this.instance.options._helper = this.instance.options.helper; //Store helper option to later restore it
					this.instance.options.helper = function() { return ui.helper[0]; };
				
					e.target = this.instance.currentItem[0];
					this.instance.mouseCapture(e, true);
					this.instance.mouseStart(e, true, true);

					//Because the browser event is way off the new appended portlet, we modify a couple of variables to reflect the changes
					this.instance.offset.click.top = inst.offset.click.top;
					this.instance.offset.click.left = inst.offset.click.left;
					this.instance.offset.parent.left -= inst.offset.parent.left - this.instance.offset.parent.left;
					this.instance.offset.parent.top -= inst.offset.parent.top - this.instance.offset.parent.top;
					
					inst.propagate("toSortable", e);
				
				}
				
				//Provided we did all the previous steps, we can fire the drag event of the sortable on every draggable drag, when it intersects with the sortable
				if(this.instance.currentItem) this.instance.mouseDrag(e);
				
			} else {
				
				//If it doesn't intersect with the sortable, and it intersected before,
				//we fake the drag stop of the sortable, but make sure it doesn't remove the helper by using cancelHelperRemoval
				if(this.instance.isOver) {
					this.instance.isOver = 0;
					this.instance.cancelHelperRemoval = true;
					this.instance.options.revert = false; //No revert here
					this.instance.mouseStop(e, true);
					this.instance.options.helper = this.instance.options._helper;
					
					//Now we remove our currentItem, the list group clone again, and the placeholder, and animate the helper back to it's original size
					this.instance.currentItem.remove();
					if(this.instance.placeholder) this.instance.placeholder.remove();
					
					inst.propagate("fromSortable", e);
				}
				
			};

		});

	}
});

$.ui.plugin.add("draggable", "stack", {
	start: function(e,ui) {
		var group = $.makeArray($(ui.options.stack.group)).sort(function(a,b) {
			return (parseInt($(a).css("zIndex"),10) || ui.options.stack.min) - (parseInt($(b).css("zIndex"),10) || ui.options.stack.min);
		});
		
		$(group).each(function(i) {
			this.style.zIndex = ui.options.stack.min + i;
		});
		
		this[0].style.zIndex = ui.options.stack.min + group.length;
	}
});

})(jQuery);

/*
 * jQuery UI Droppable
 *
 * Copyright (c) 2008 Paul Bakaus
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Droppables
 *
 * Depends:
 *	ui.core.js
 *	ui.draggable.js
 */
(function($) {

$.widget("ui.droppable", {
	init: function() {
		
		var o = this.options, accept = o.accept;
		this.isover = 0; this.isout = 1;

		this.options.accept = this.options.accept && this.options.accept.constructor == Function ? this.options.accept : function(d) {
			return d.is(accept);
		};

		//Store the droppable's proportions
		this.proportions = { width: this.element[0].offsetWidth, height: this.element[0].offsetHeight };
		
		// Add the reference and positions to the manager
		$.ui.ddmanager.droppables[this.options.scope] = $.ui.ddmanager.droppables[this.options.scope] || [];
		$.ui.ddmanager.droppables[this.options.scope].push(this);
		
		(this.options.cssNamespace && this.element.addClass(this.options.cssNamespace+"-droppable"));
		
	},
	plugins: {},
	ui: function(c) {
		return {
			draggable: (c.currentItem || c.element),
			helper: c.helper,
			position: c.position,
			absolutePosition: c.positionAbs,
			options: this.options,
			element: this.element
		};
	},
	destroy: function() {
		var drop = $.ui.ddmanager.droppables[this.options.scope];
		for ( var i = 0; i < drop.length; i++ )
			if ( drop[i] == this )
				drop.splice(i, 1);
		
		this.element
			.removeClass("ui-droppable-disabled")
			.removeData("droppable")
			.unbind(".droppable");
	},
	over: function(e) {
		
		var draggable = $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element
		
		if (this.options.accept.call(this.element,(draggable.currentItem || draggable.element))) {
			$.ui.plugin.call(this, 'over', [e, this.ui(draggable)]);
			this.element.triggerHandler("dropover", [e, this.ui(draggable)], this.options.over);
		}
		
	},
	out: function(e) {
		
		var draggable = $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return; // Bail if draggable and droppable are same element
		
		if (this.options.accept.call(this.element,(draggable.currentItem || draggable.element))) {
			$.ui.plugin.call(this, 'out', [e, this.ui(draggable)]);
			this.element.triggerHandler("dropout", [e, this.ui(draggable)], this.options.out);
		}
		
	},
	drop: function(e,custom) {
		
		var draggable = custom || $.ui.ddmanager.current;
		if (!draggable || (draggable.currentItem || draggable.element)[0] == this.element[0]) return false; // Bail if draggable and droppable are same element
		
		var childrenIntersection = false;
		this.element.find(":data(droppable)").not(".ui-draggable-dragging").each(function() {
			var inst = $.data(this, 'droppable');
			if(inst.options.greedy && $.ui.intersect(draggable, $.extend(inst, { offset: inst.element.offset() }), inst.options.tolerance)) {
				childrenIntersection = true; return false;
			}
		});
		if(childrenIntersection) return false;
		
		if(this.options.accept.call(this.element,(draggable.currentItem || draggable.element))) {
			$.ui.plugin.call(this, 'drop', [e, this.ui(draggable)]);
			this.element.triggerHandler("drop", [e, this.ui(draggable)], this.options.drop);
			return true;
		}
		
		return false;
		
	},
	activate: function(e) {
		
		var draggable = $.ui.ddmanager.current;
		$.ui.plugin.call(this, 'activate', [e, this.ui(draggable)]);
		if(draggable) this.element.triggerHandler("dropactivate", [e, this.ui(draggable)], this.options.activate);
		
	},
	deactivate: function(e) {
		
		var draggable = $.ui.ddmanager.current;
		$.ui.plugin.call(this, 'deactivate', [e, this.ui(draggable)]);
		if(draggable) this.element.triggerHandler("dropdeactivate", [e, this.ui(draggable)], this.options.deactivate);
		
	}
});

$.extend($.ui.droppable, {
	defaults: {
		disabled: false,
		tolerance: 'intersect',
		scope: 'default',
		cssNamespace: 'ui'
	}
});

$.ui.intersect = function(draggable, droppable, toleranceMode) {
	
	if (!droppable.offset) return false;
	
	var x1 = (draggable.positionAbs || draggable.position.absolute).left, x2 = x1 + draggable.helperProportions.width,
		y1 = (draggable.positionAbs || draggable.position.absolute).top, y2 = y1 + draggable.helperProportions.height;
	var l = droppable.offset.left, r = l + droppable.proportions.width,
		t = droppable.offset.top, b = t + droppable.proportions.height;
	
	switch (toleranceMode) {
		case 'fit':
			return (l < x1 && x2 < r
				&& t < y1 && y2 < b);
			break;
		case 'intersect':
			return (l < x1 + (draggable.helperProportions.width / 2) // Right Half
				&& x2 - (draggable.helperProportions.width / 2) < r // Left Half
				&& t < y1 + (draggable.helperProportions.height / 2) // Bottom Half
				&& y2 - (draggable.helperProportions.height / 2) < b ); // Top Half
			break;
		case 'pointer':
			return (l < ((draggable.positionAbs || draggable.position.absolute).left + (draggable.clickOffset || draggable.offset.click).left) && ((draggable.positionAbs || draggable.position.absolute).left + (draggable.clickOffset || draggable.offset.click).left) < r
				&& t < ((draggable.positionAbs || draggable.position.absolute).top + (draggable.clickOffset || draggable.offset.click).top) && ((draggable.positionAbs || draggable.position.absolute).top + (draggable.clickOffset || draggable.offset.click).top) < b);
			break;
		case 'touch':
			return (
					(y1 >= t && y1 <= b) ||	// Top edge touching
					(y2 >= t && y2 <= b) ||	// Bottom edge touching
					(y1 < t && y2 > b)		// Surrounded vertically
				) && (
					(x1 >= l && x1 <= r) ||	// Left edge touching
					(x2 >= l && x2 <= r) ||	// Right edge touching
					(x1 < l && x2 > r)		// Surrounded horizontally
				);
			break;
		default:
			return false;
			break;
		}
	
};

/*
	This manager tracks offsets of draggables and droppables
*/
$.ui.ddmanager = {
	current: null,
	droppables: { 'default': [] },
	prepareOffsets: function(t, e) {
		
		var m = $.ui.ddmanager.droppables[t.options.scope];
		var type = e ? e.type : null; // workaround for #2317
		var list = (t.currentItem || t.element).find(":data(droppable)").andSelf();	

		droppablesLoop: for (var i = 0; i < m.length; i++) {
			
			if(m[i].options.disabled || (t && !m[i].options.accept.call(m[i].element,(t.currentItem || t.element)))) continue;	//No disabled and non-accepted
			for (var j=0; j < list.length; j++) { if(list[j] == m[i].element[0]) { m[i].proportions.height = 0; continue droppablesLoop; } }; //Filter out elements in the current dragged item
			m[i].visible = m[i].element.css("display") != "none"; if(!m[i].visible) continue; 									//If the element is not visible, continue
			
			m[i].offset = m[i].element.offset();
			m[i].proportions = { width: m[i].element[0].offsetWidth, height: m[i].element[0].offsetHeight };
			
			if(type == "dragstart" || type == "sortactivate") m[i].activate.call(m[i], e); 										//Activate the droppable if used directly from draggables
			
		}
		
	},
	drop: function(draggable, e) {
		
		var dropped = false;
		$.each($.ui.ddmanager.droppables[draggable.options.scope], function() {
			
			if(!this.options) return;
			if (!this.options.disabled && this.visible && $.ui.intersect(draggable, this, this.options.tolerance))
				dropped = this.drop.call(this, e);
			
			if (!this.options.disabled && this.visible && this.options.accept.call(this.element,(draggable.currentItem || draggable.element))) {
				this.isout = 1; this.isover = 0;
				this.deactivate.call(this, e);
			}
			
		});
		return dropped;
		
	},
	drag: function(draggable, e) {
		
		//If you have a highly dynamic page, you might try this option. It renders positions every time you move the mouse.
		if(draggable.options.refreshPositions) $.ui.ddmanager.prepareOffsets(draggable, e);
		
		//Run through all droppables and check their positions based on specific tolerance options

		$.each($.ui.ddmanager.droppables[draggable.options.scope], function() {
			
			if(this.options.disabled || this.greedyChild || !this.visible) return;
			var intersects = $.ui.intersect(draggable, this, this.options.tolerance);
			
			var c = !intersects && this.isover == 1 ? 'isout' : (intersects && this.isover == 0 ? 'isover' : null);
			if(!c) return;
			
			var parentInstance;
			if (this.options.greedy) {
				var parent = this.element.parents(':data(droppable):eq(0)');
				if (parent.length) {
					parentInstance = $.data(parent[0], 'droppable');
					parentInstance.greedyChild = (c == 'isover' ? 1 : 0);
				}
			}
			
			// we just moved into a greedy child
			if (parentInstance && c == 'isover') {
				parentInstance['isover'] = 0;
				parentInstance['isout'] = 1;
				parentInstance.out.call(parentInstance, e);
			}
			
			this[c] = 1; this[c == 'isout' ? 'isover' : 'isout'] = 0;
			this[c == "isover" ? "over" : "out"].call(this, e);
			
			// we just moved out of a greedy child
			if (parentInstance && c == 'isout') {
				parentInstance['isout'] = 0;
				parentInstance['isover'] = 1;
				parentInstance.over.call(parentInstance, e);
			}
		});
		
	}
};

/*
 * Droppable Extensions
 */

$.ui.plugin.add("droppable", "activeClass", {
	activate: function(e, ui) {
		$(this).addClass(ui.options.activeClass);
	},
	deactivate: function(e, ui) {
		$(this).removeClass(ui.options.activeClass);
	},
	drop: function(e, ui) {
		$(this).removeClass(ui.options.activeClass);
	}
});

$.ui.plugin.add("droppable", "hoverClass", {
	over: function(e, ui) {
		$(this).addClass(ui.options.hoverClass);
	},
	out: function(e, ui) {
		$(this).removeClass(ui.options.hoverClass);
	},
	drop: function(e, ui) {
		$(this).removeClass(ui.options.hoverClass);
	}
});

})(jQuery);

/*
 * jQuery UI Resizable
 *
 * Copyright (c) 2008 Paul Bakaus
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Resizables
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.widget("ui.resizable", $.extend({}, $.ui.mouse, {
	init: function() {

		var self = this, o = this.options;

		var elpos = this.element.css('position');
		
		this.originalElement = this.element;
		
		// simulate .ui-resizable { position: relative; }
		this.element.addClass("ui-resizable").css({ position: /static/.test(elpos) ? 'relative' : elpos });
		
		$.extend(o, {
			_aspectRatio: !!(o.aspectRatio),
			helper: o.helper || o.ghost || o.animate ? o.helper || 'proxy' : null,
			knobHandles: o.knobHandles === true ? 'ui-resizable-knob-handle' : o.knobHandles
		});
		
		//Default Theme
		var aBorder = '1px solid #DEDEDE';
		
		o.defaultTheme = {
			'ui-resizable': { display: 'block' },
			'ui-resizable-handle': { position: 'absolute', background: '#F2F2F2', fontSize: '0.1px' },
			'ui-resizable-n': { cursor: 'n-resize', height: '4px', left: '0px', right: '0px', borderTop: aBorder },
			'ui-resizable-s': { cursor: 's-resize', height: '4px', left: '0px', right: '0px', borderBottom: aBorder },
			'ui-resizable-e': { cursor: 'e-resize', width: '4px', top: '0px', bottom: '0px', borderRight: aBorder },
			'ui-resizable-w': { cursor: 'w-resize', width: '4px', top: '0px', bottom: '0px', borderLeft: aBorder },
			'ui-resizable-se': { cursor: 'se-resize', width: '4px', height: '4px', borderRight: aBorder, borderBottom: aBorder },
			'ui-resizable-sw': { cursor: 'sw-resize', width: '4px', height: '4px', borderBottom: aBorder, borderLeft: aBorder },
			'ui-resizable-ne': { cursor: 'ne-resize', width: '4px', height: '4px', borderRight: aBorder, borderTop: aBorder },
			'ui-resizable-nw': { cursor: 'nw-resize', width: '4px', height: '4px', borderLeft: aBorder, borderTop: aBorder }
		};
		
		o.knobTheme = {
			'ui-resizable-handle': { background: '#F2F2F2', border: '1px solid #808080', height: '8px', width: '8px' },
			'ui-resizable-n': { cursor: 'n-resize', top: '0px', left: '45%' },
			'ui-resizable-s': { cursor: 's-resize', bottom: '0px', left: '45%' },
			'ui-resizable-e': { cursor: 'e-resize', right: '0px', top: '45%' },
			'ui-resizable-w': { cursor: 'w-resize', left: '0px', top: '45%' },
			'ui-resizable-se': { cursor: 'se-resize', right: '0px', bottom: '0px' },
			'ui-resizable-sw': { cursor: 'sw-resize', left: '0px', bottom: '0px' },
			'ui-resizable-nw': { cursor: 'nw-resize', left: '0px', top: '0px' },
			'ui-resizable-ne': { cursor: 'ne-resize', right: '0px', top: '0px' }
		};
		
		o._nodeName = this.element[0].nodeName;
		
		//Wrap the element if it cannot hold child nodes
		if(o._nodeName.match(/canvas|textarea|input|select|button|img/i)) {
			var el = this.element;
			
			//Opera fixing relative position
			if (/relative/.test(el.css('position')) && $.browser.opera)
				el.css({ position: 'relative', top: 'auto', left: 'auto' });
			
			//Create a wrapper element and set the wrapper to the new current internal element
			el.wrap(
				$('<div class="ui-wrapper"	style="overflow: hidden;"></div>').css( {
					position: el.css('position'),
					width: el.outerWidth(),
					height: el.outerHeight(),
					top: el.css('top'),
					left: el.css('left')
				})
			);
			
			var oel = this.element; this.element = this.element.parent();
			
			// store instance on wrapper
			this.element.data('resizable', this); 
			
			//Move margins to the wrapper
			this.element.css({ marginLeft: oel.css("marginLeft"), marginTop: oel.css("marginTop"),
				marginRight: oel.css("marginRight"), marginBottom: oel.css("marginBottom")
			});
			
			oel.css({ marginLeft: 0, marginTop: 0, marginRight: 0, marginBottom: 0});
			
			//Prevent Safari textarea resize
			if ($.browser.safari && o.preventDefault) oel.css('resize', 'none');
			
			o.proportionallyResize = oel.css({ position: 'static', zoom: 1, display: 'block' });
			
			// avoid IE jump
			this.element.css({ margin: oel.css('margin') });
			
			// fix handlers offset
			this._proportionallyResize();
		}
		
		if(!o.handles) o.handles = !$('.ui-resizable-handle', this.element).length ? "e,s,se" : { n: '.ui-resizable-n', e: '.ui-resizable-e', s: '.ui-resizable-s', w: '.ui-resizable-w', se: '.ui-resizable-se', sw: '.ui-resizable-sw', ne: '.ui-resizable-ne', nw: '.ui-resizable-nw' };
		if(o.handles.constructor == String) {
			
			o.zIndex = o.zIndex || 1000;
			
			if(o.handles == 'all') o.handles = 'n,e,s,w,se,sw,ne,nw';
			
			var n = o.handles.split(","); o.handles = {};
			
			// insertions are applied when don't have theme loaded
			var insertionsDefault = {
				handle: 'position: absolute; display: none; overflow:hidden;',
				n: 'top: 0pt; width:100%;',
				e: 'right: 0pt; height:100%;',
				s: 'bottom: 0pt; width:100%;',
				w: 'left: 0pt; height:100%;',
				se: 'bottom: 0pt; right: 0px;',
				sw: 'bottom: 0pt; left: 0px;',
				ne: 'top: 0pt; right: 0px;',
				nw: 'top: 0pt; left: 0px;'
			};
			
			for(var i = 0; i < n.length; i++) {
				var handle = $.trim(n[i]), dt = o.defaultTheme, hname = 'ui-resizable-'+handle, loadDefault = !$.ui.css(hname) && !o.knobHandles, userKnobClass = $.ui.css('ui-resizable-knob-handle'), 
							allDefTheme = $.extend(dt[hname], dt['ui-resizable-handle']), allKnobTheme = $.extend(o.knobTheme[hname], !userKnobClass ? o.knobTheme['ui-resizable-handle'] : {});
				
				// increase zIndex of sw, se, ne, nw axis
				var applyZIndex = /sw|se|ne|nw/.test(handle) ? { zIndex: ++o.zIndex } : {};
				
				var defCss = (loadDefault ? insertionsDefault[handle] : ''), 
					axis = $(['<div class="ui-resizable-handle ', hname, '" style="', defCss, insertionsDefault.handle, '"></div>'].join('')).css( applyZIndex );
				o.handles[handle] = '.ui-resizable-'+handle;
				
				this.element.append(
					//Theme detection, if not loaded, load o.defaultTheme
					axis.css( loadDefault ? allDefTheme : {} )
						// Load the knobHandle css, fix width, height, top, left...
						.css( o.knobHandles ? allKnobTheme : {} ).addClass(o.knobHandles ? 'ui-resizable-knob-handle' : '').addClass(o.knobHandles)
				);
			}
			
			if (o.knobHandles) this.element.addClass('ui-resizable-knob').css( !$.ui.css('ui-resizable-knob') ? { /*border: '1px #fff dashed'*/ } : {} );
		}
		
		this._renderAxis = function(target) {
			target = target || this.element;
			
			for(var i in o.handles) {
				if(o.handles[i].constructor == String) 
					o.handles[i] = $(o.handles[i], this.element).show();
				
				if (o.transparent)
					o.handles[i].css({opacity:0});
				
				//Apply pad to wrapper element, needed to fix axis position (textarea, inputs, scrolls)
				if (this.element.is('.ui-wrapper') && 
					o._nodeName.match(/textarea|input|select|button/i)) {
					
					var axis = $(o.handles[i], this.element), padWrapper = 0;
					
					//Checking the correct pad and border
					padWrapper = /sw|ne|nw|se|n|s/.test(i) ? axis.outerHeight() : axis.outerWidth();
					
					//The padding type i have to apply...
					var padPos = [ 'padding', 
						/ne|nw|n/.test(i) ? 'Top' :
						/se|sw|s/.test(i) ? 'Bottom' : 
						/^e$/.test(i) ? 'Right' : 'Left' ].join(""); 
					
					if (!o.transparent)
						target.css(padPos, padWrapper);
					
					this._proportionallyResize();
				}
				if(!$(o.handles[i]).length) continue;
			}
		};
		
		this._renderAxis(this.element);
		o._handles = $('.ui-resizable-handle', self.element);
		
		if (o.disableSelection)
			o._handles.each(function(i, e) { $.ui.disableSelection(e); });
		
		//Matching axis name
		o._handles.mouseover(function() {
			if (!o.resizing) {
				if (this.className) 
					var axis = this.className.match(/ui-resizable-(se|sw|ne|nw|n|e|s|w)/i);
				//Axis, default = se
				self.axis = o.axis = axis && axis[1] ? axis[1] : 'se';
			}
		});
		
		//If we want to auto hide the elements
		if (o.autoHide) {
			o._handles.hide();
			$(self.element).addClass("ui-resizable-autohide").hover(function() {
				$(this).removeClass("ui-resizable-autohide");
				o._handles.show();
			},
			function(){
				if (!o.resizing) {
					$(this).addClass("ui-resizable-autohide");
					o._handles.hide();
				}
			});
		}
		
		this.mouseInit();
	},
	plugins: {},
	ui: function() {
		return {
			originalElement: this.originalElement,
			element: this.element,
			helper: this.helper,
			position: this.position,
			size: this.size,
			options: this.options,
			originalSize: this.originalSize,
			originalPosition: this.originalPosition
		};
	},
	propagate: function(n,e) {
		$.ui.plugin.call(this, n, [e, this.ui()]);
		if (n != "resize") this.element.triggerHandler(["resize", n].join(""), [e, this.ui()], this.options[n]);
	},
	destroy: function() {
		var el = this.element, wrapped = el.children(".ui-resizable").get(0);
		
		this.mouseDestroy();
		
		var _destroy = function(exp) {
			$(exp).removeClass("ui-resizable ui-resizable-disabled")
				.removeData("resizable").unbind(".resizable").find('.ui-resizable-handle').remove();
		};
		
		_destroy(el);
		
		if (el.is('.ui-wrapper') && wrapped) {
			el.parent().append(
				$(wrapped).css({
					position: el.css('position'),
					width: el.outerWidth(),
					height: el.outerHeight(),
					top: el.css('top'),
					left: el.css('left')
				})
			).end().remove();
			
			_destroy(wrapped);
		}
	},
	mouseStart: function(e) {
		if(this.options.disabled) return false;
		
		var handle = false;
		for(var i in this.options.handles) {
			if($(this.options.handles[i])[0] == e.target) handle = true;
		}
		if (!handle) return false;
		
		var o = this.options, iniPos = this.element.position(), el = this.element, 
			num = function(v) { return parseInt(v, 10) || 0; }, ie6 = $.browser.msie && $.browser.version < 7;
		o.resizing = true;
		o.documentScroll = { top: $(document).scrollTop(), left: $(document).scrollLeft() };
		
		// bugfix #1749
		if (el.is('.ui-draggable') || (/absolute/).test(el.css('position'))) {
			
			// sOffset decides if document scrollOffset will be added to the top/left of the resizable element
			var sOffset = $.browser.msie && !o.containment && (/absolute/).test(el.css('position')) && !(/relative/).test(el.parent().css('position'));
			var dscrollt = sOffset ? o.documentScroll.top : 0, dscrolll = sOffset ? o.documentScroll.left : 0;
			
			el.css({ position: 'absolute', top: (iniPos.top + dscrollt), left: (iniPos.left + dscrolll) });
		}
		
		//Opera fixing relative position
		if ($.browser.opera && /relative/.test(el.css('position')))
			el.css({ position: 'relative', top: 'auto', left: 'auto' });
		
		this._renderProxy();
		
		var curleft = num(this.helper.css('left')), curtop = num(this.helper.css('top'));
		
		if (o.containment) {
			curleft += $(o.containment).scrollLeft()||0;
			curtop += $(o.containment).scrollTop()||0;
		}
		
		//Store needed variables
		this.offset = this.helper.offset();
		this.position = { left: curleft, top: curtop };
		this.size = o.helper || ie6 ? { width: el.outerWidth(), height: el.outerHeight() } : { width: el.width(), height: el.height() };
		this.originalSize = o.helper || ie6 ? { width: el.outerWidth(), height: el.outerHeight() } : { width: el.width(), height: el.height() };
		this.originalPosition = { left: curleft, top: curtop };
		this.sizeDiff = { width: el.outerWidth() - el.width(), height: el.outerHeight() - el.height() };
		this.originalMousePosition = { left: e.pageX, top: e.pageY };
		
		//Aspect Ratio
		o.aspectRatio = (typeof o.aspectRatio == 'number') ? o.aspectRatio : ((this.originalSize.height / this.originalSize.width)||1);
		
		if (o.preserveCursor)
			$('body').css('cursor', this.axis + '-resize');
			
		this.propagate("start", e);
		return true;
	},
	mouseDrag: function(e) {
		
		//Increase performance, avoid regex
		var el = this.helper, o = this.options, props = {},
			self = this, smp = this.originalMousePosition, a = this.axis;
		
		var dx = (e.pageX-smp.left)||0, dy = (e.pageY-smp.top)||0;
		var trigger = this._change[a];
		if (!trigger) return false;
		
		// Calculate the attrs that will be change
		var data = trigger.apply(this, [e, dx, dy]), ie6 = $.browser.msie && $.browser.version < 7, csdif = this.sizeDiff;
		
		if (o._aspectRatio || e.shiftKey)
			data = this._updateRatio(data, e);
		
		data = this._respectSize(data, e);
		
		// plugins callbacks need to be called first
		this.propagate("resize", e);
		
		el.css({
			top: this.position.top + "px", left: this.position.left + "px", 
			width: this.size.width + "px", height: this.size.height + "px"
		});
		
		if (!o.helper && o.proportionallyResize)
			this._proportionallyResize();
		
		this._updateCache(data);
		
		// calling the user callback at the end
		this.element.triggerHandler("resize", [e, this.ui()], this.options["resize"]);
		
		return false;
	},
	mouseStop: function(e) {
		
		this.options.resizing = false;
		var o = this.options, num = function(v) { return parseInt(v, 10) || 0; }, self = this;
		
		if(o.helper) {
			var pr = o.proportionallyResize, ista = pr && (/textarea/i).test(pr.get(0).nodeName), 
						soffseth = ista && $.ui.hasScroll(pr.get(0), 'left') /* TODO - jump height */ ? 0 : self.sizeDiff.height,
							soffsetw = ista ? 0 : self.sizeDiff.width;
			
			var s = { width: (self.size.width - soffsetw), height: (self.size.height - soffseth) },
				left = (parseInt(self.element.css('left'), 10) + (self.position.left - self.originalPosition.left)) || null, 
				top = (parseInt(self.element.css('top'), 10) + (self.position.top - self.originalPosition.top)) || null;
			
			if (!o.animate)
				this.element.css($.extend(s, { top: top, left: left }));
			
			if (o.helper && !o.animate) this._proportionallyResize();
		}
		
		if (o.preserveCursor)
			$('body').css('cursor', 'auto');
		
		this.propagate("stop", e);
		
		if (o.helper) this.helper.remove();
		
		return false;
	},
	_updateCache: function(data) {
		var o = this.options;
		this.offset = this.helper.offset();
		if (data.left) this.position.left = data.left;
		if (data.top) this.position.top = data.top;
		if (data.height) this.size.height = data.height;
		if (data.width) this.size.width = data.width;
	},
	_updateRatio: function(data, e) {
		var o = this.options, cpos = this.position, csize = this.size, a = this.axis;
		
		if (data.height) data.width = (csize.height / o.aspectRatio);
		else if (data.width) data.height = (csize.width * o.aspectRatio);
		
		if (a == 'sw') {
			data.left = cpos.left + (csize.width - data.width);
			data.top = null;
		}
		if (a == 'nw') { 
			data.top = cpos.top + (csize.height - data.height);
			data.left = cpos.left + (csize.width - data.width);
		}
		
		return data;
	},
	_respectSize: function(data, e) {
		
		var el = this.helper, o = this.options, pRatio = o._aspectRatio || e.shiftKey, a = this.axis, 
				ismaxw = data.width && o.maxWidth && o.maxWidth < data.width, ismaxh = data.height && o.maxHeight && o.maxHeight < data.height,
					isminw = data.width && o.minWidth && o.minWidth > data.width, isminh = data.height && o.minHeight && o.minHeight > data.height;
		
		if (isminw) data.width = o.minWidth;
		if (isminh) data.height = o.minHeight;
		if (ismaxw) data.width = o.maxWidth;
		if (ismaxh) data.height = o.maxHeight;
		
		var dw = this.originalPosition.left + this.originalSize.width, dh = this.position.top + this.size.height;
		var cw = /sw|nw|w/.test(a), ch = /nw|ne|n/.test(a);
		
		if (isminw && cw) data.left = dw - o.minWidth;
		if (ismaxw && cw) data.left = dw - o.maxWidth;
		if (isminh && ch)	data.top = dh - o.minHeight;
		if (ismaxh && ch)	data.top = dh - o.maxHeight;
		
		// fixing jump error on top/left - bug #2330
		var isNotwh = !data.width && !data.height;
		if (isNotwh && !data.left && data.top) data.top = null;
		else if (isNotwh && !data.top && data.left) data.left = null;
		
		return data;
	},
	_proportionallyResize: function() {
		var o = this.options;
		if (!o.proportionallyResize) return;
		var prel = o.proportionallyResize, el = this.helper || this.element;
		
		if (!o.borderDif) {
			var b = [prel.css('borderTopWidth'), prel.css('borderRightWidth'), prel.css('borderBottomWidth'), prel.css('borderLeftWidth')],
				p = [prel.css('paddingTop'), prel.css('paddingRight'), prel.css('paddingBottom'), prel.css('paddingLeft')];
			
			o.borderDif = $.map(b, function(v, i) {
				var border = parseInt(v,10)||0, padding = parseInt(p[i],10)||0;
				return border + padding; 
			});
		}
		prel.css({
			height: (el.height() - o.borderDif[0] - o.borderDif[2]) + "px",
			width: (el.width() - o.borderDif[1] - o.borderDif[3]) + "px"
		});
	},
	_renderProxy: function() {
		var el = this.element, o = this.options;
		this.elementOffset = el.offset();
		
		if(o.helper) {
			this.helper = this.helper || $('<div style="overflow:hidden;"></div>');
			
			// fix ie6 offset
			var ie6 = $.browser.msie && $.browser.version < 7, ie6offset = (ie6 ? 1 : 0),
			pxyoffset = ( ie6 ? 2 : -1 );
			
			this.helper.addClass(o.helper).css({
				width: el.outerWidth() + pxyoffset,
				height: el.outerHeight() + pxyoffset,
				position: 'absolute',
				left: this.elementOffset.left - ie6offset +'px',
				top: this.elementOffset.top - ie6offset +'px',
				zIndex: ++o.zIndex
			});
			
			this.helper.appendTo("body");
			
			if (o.disableSelection)
				$.ui.disableSelection(this.helper.get(0));
			
		} else {
			this.helper = el; 
		}
	},
	_change: {
		e: function(e, dx, dy) {
			return { width: this.originalSize.width + dx };
		},
		w: function(e, dx, dy) {
			var o = this.options, cs = this.originalSize, sp = this.originalPosition;
			return { left: sp.left + dx, width: cs.width - dx };
		},
		n: function(e, dx, dy) {
			var o = this.options, cs = this.originalSize, sp = this.originalPosition;
			return { top: sp.top + dy, height: cs.height - dy };
		},
		s: function(e, dx, dy) {
			return { height: this.originalSize.height + dy };
		},
		se: function(e, dx, dy) {
			return $.extend(this._change.s.apply(this, arguments), this._change.e.apply(this, [e, dx, dy]));
		},
		sw: function(e, dx, dy) {
			return $.extend(this._change.s.apply(this, arguments), this._change.w.apply(this, [e, dx, dy]));
		},
		ne: function(e, dx, dy) {
			return $.extend(this._change.n.apply(this, arguments), this._change.e.apply(this, [e, dx, dy]));
		},
		nw: function(e, dx, dy) {
			return $.extend(this._change.n.apply(this, arguments), this._change.w.apply(this, [e, dx, dy]));
		}
	}
}));

$.extend($.ui.resizable, {
	defaults: {
		cancel: ":input",
		distance: 1,
		delay: 0,
		preventDefault: true,
		transparent: false,
		minWidth: 10,
		minHeight: 10,
		aspectRatio: false,
		disableSelection: true,
		preserveCursor: true,
		autoHide: false,
		knobHandles: false
	}
});

/*
 * Resizable Extensions
 */

$.ui.plugin.add("resizable", "containment", {
	
	start: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable"), el = self.element;
		var oc = o.containment,	ce = (oc instanceof $) ? oc.get(0) : (/parent/.test(oc)) ? el.parent().get(0) : oc;
		if (!ce) return;
		
		self.containerElement = $(ce);
		
		if (/document/.test(oc) || oc == document) {
			self.containerOffset = { left: 0, top: 0 };
			self.containerPosition = { left: 0, top: 0 };
			
			self.parentData = { 
				element: $(document), left: 0, top: 0, 
				width: $(document).width(), height: $(document).height() || document.body.parentNode.scrollHeight
			};
		}
		
				
		// i'm a node, so compute top, left, right, bottom
		else{
			self.containerOffset = $(ce).offset();
			self.containerPosition = $(ce).position();
			self.containerSize = { height: $(ce).innerHeight(), width: $(ce).innerWidth() };
		
			var co = self.containerOffset, ch = self.containerSize.height,	cw = self.containerSize.width, 
						width = ($.ui.hasScroll(ce, "left") ? ce.scrollWidth : cw ), height = ($.ui.hasScroll(ce) ? ce.scrollHeight : ch);
		
			self.parentData = { 
				element: ce, left: co.left, top: co.top, width: width, height: height
			};
		}
	},
	
	resize: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable"), 
				ps = self.containerSize, co = self.containerOffset, cs = self.size, cp = self.position,
				pRatio = o._aspectRatio || e.shiftKey, cop = { top:0, left:0 }, ce = self.containerElement;
		
		if (ce[0] != document && /static/.test(ce.css('position')))
			cop = self.containerPosition;
		
		if (cp.left < (o.helper ? co.left : cop.left)) {
			self.size.width = self.size.width + (o.helper ? (self.position.left - co.left) : (self.position.left - cop.left));
			if (pRatio) self.size.height = self.size.width * o.aspectRatio;
			self.position.left = o.helper ? co.left : cop.left;
		}
		
		if (cp.top < (o.helper ? co.top : 0)) {
			self.size.height = self.size.height + (o.helper ? (self.position.top - co.top) : self.position.top);
			if (pRatio) self.size.width = self.size.height / o.aspectRatio;
			self.position.top = o.helper ? co.top : 0;
		}
		
		var woset = (o.helper ? self.offset.left - co.left : (self.position.left - cop.left)) + self.sizeDiff.width, 
					hoset = (o.helper ? self.offset.top - co.top : self.position.top) + self.sizeDiff.height;
		
		if (woset + self.size.width >= self.parentData.width) {
			self.size.width = self.parentData.width - woset;
			if (pRatio) self.size.height = self.size.width * o.aspectRatio;
		}
		
		if (hoset + self.size.height >= self.parentData.height) {
			self.size.height = self.parentData.height - hoset;
			if (pRatio) self.size.width = self.size.height / o.aspectRatio;
		}
	},
	
	stop: function(e, ui){
		var o = ui.options, self = $(this).data("resizable"), cp = self.position,
				co = self.containerOffset, cop = self.containerPosition, ce = self.containerElement;
		
		var helper = $(self.helper), ho = helper.offset(), w = helper.innerWidth(), h = helper.innerHeight();
		
		
		if (o.helper && !o.animate && /relative/.test(ce.css('position')))
			$(this).css({ left: (ho.left - co.left), top: (ho.top - co.top), width: w, height: h });
		
		if (o.helper && !o.animate && /static/.test(ce.css('position')))
			$(this).css({ left: cop.left + (ho.left - co.left), top: cop.top + (ho.top - co.top), width: w, height: h });
		
	}
});

$.ui.plugin.add("resizable", "grid", {
	
	resize: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable"), cs = self.size, os = self.originalSize, op = self.originalPosition, a = self.axis, ratio = o._aspectRatio || e.shiftKey;
		o.grid = typeof o.grid == "number" ? [o.grid, o.grid] : o.grid;
		var ox = Math.round((cs.width - os.width) / (o.grid[0]||1)) * (o.grid[0]||1), oy = Math.round((cs.height - os.height) / (o.grid[1]||1)) * (o.grid[1]||1);
		
		if (/^(se|s|e)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
		}
		else if (/^(ne)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.top = op.top - oy;
		}
		else if (/^(sw)$/.test(a)) {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.left = op.left - ox;
		}
		else {
			self.size.width = os.width + ox;
			self.size.height = os.height + oy;
			self.position.top = op.top - oy;
			self.position.left = op.left - ox;
		}
	}
	
});

$.ui.plugin.add("resizable", "animate", {
	
	stop: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable");
		
		var pr = o.proportionallyResize, ista = pr && (/textarea/i).test(pr.get(0).nodeName), 
						soffseth = ista && $.ui.hasScroll(pr.get(0), 'left') /* TODO - jump height */ ? 0 : self.sizeDiff.height,
							soffsetw = ista ? 0 : self.sizeDiff.width;
		
		var style = { width: (self.size.width - soffsetw), height: (self.size.height - soffseth) },
					left = (parseInt(self.element.css('left'), 10) + (self.position.left - self.originalPosition.left)) || null, 
						top = (parseInt(self.element.css('top'), 10) + (self.position.top - self.originalPosition.top)) || null; 
		
		self.element.animate(
			$.extend(style, top && left ? { top: top, left: left } : {}), { 
				duration: o.animateDuration || "slow", easing: o.animateEasing || "swing", 
				step: function() {
					
					var data = {
						width: parseInt(self.element.css('width'), 10),
						height: parseInt(self.element.css('height'), 10),
						top: parseInt(self.element.css('top'), 10),
						left: parseInt(self.element.css('left'), 10)
					};
					
					if (pr) pr.css({ width: data.width, height: data.height });
					
					// propagating resize, and updating values for each animation step
					self._updateCache(data);
					self.propagate("animate", e);
					
				}
			}
		);
	}
	
});

$.ui.plugin.add("resizable", "ghost", {
	
	start: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable"), pr = o.proportionallyResize, cs = self.size;
		
		if (!pr) self.ghost = self.element.clone();
		else self.ghost = pr.clone();
		
		self.ghost.css(
			{ opacity: .25, display: 'block', position: 'relative', height: cs.height, width: cs.width, margin: 0, left: 0, top: 0 }
		)
		.addClass('ui-resizable-ghost').addClass(typeof o.ghost == 'string' ? o.ghost : '');
		
		self.ghost.appendTo(self.helper);
		
	},
	
	resize: function(e, ui){
		var o = ui.options, self = $(this).data("resizable"), pr = o.proportionallyResize;
		
		if (self.ghost) self.ghost.css({ position: 'relative', height: self.size.height, width: self.size.width });
		
	},
	
	stop: function(e, ui){
		var o = ui.options, self = $(this).data("resizable"), pr = o.proportionallyResize;
		if (self.ghost && self.helper) self.helper.get(0).removeChild(self.ghost.get(0));
	}
	
});

$.ui.plugin.add("resizable", "alsoResize", {
	
	start: function(e, ui) {
		var o = ui.options, self = $(this).data("resizable"), 
		
		_store = function(exp) {
			$(exp).each(function() {
				$(this).data("resizable-alsoresize", {
					width: parseInt($(this).width(), 10), height: parseInt($(this).height(), 10),
					left: parseInt($(this).css('left'), 10), top: parseInt($(this).css('top'), 10)
				});
			});
		};
		
		if (typeof(o.alsoResize) == 'object') {
			if (o.alsoResize.length) { o.alsoResize = o.alsoResize[0];	_store(o.alsoResize); }
			else { $.each(o.alsoResize, function(exp, c) { _store(exp); }); }
		}else{
			_store(o.alsoResize);
		} 
	},
	
	resize: function(e, ui){
		var o = ui.options, self = $(this).data("resizable"), os = self.originalSize, op = self.originalPosition;
		
		var delta = { 
			height: (self.size.height - os.height) || 0, width: (self.size.width - os.width) || 0,
			top: (self.position.top - op.top) || 0, left: (self.position.left - op.left) || 0
		},
		
		_alsoResize = function(exp, c) {
			$(exp).each(function() {
				var start = $(this).data("resizable-alsoresize"), style = {}, css = c && c.length ? c : ['width', 'height', 'top', 'left'];
				
				$.each(css || ['width', 'height', 'top', 'left'], function(i, prop) {
					var sum = (start[prop]||0) + (delta[prop]||0);
					if (sum && sum >= 0)
						style[prop] = sum || null;
				});
				$(this).css(style);
			});
		};
		
		if (typeof(o.alsoResize) == 'object') {
			$.each(o.alsoResize, function(exp, c) { _alsoResize(exp, c); });
		}else{
			_alsoResize(o.alsoResize);
		}
	},
	
	stop: function(e, ui){
		$(this).removeData("resizable-alsoresize-start");
	}
});

})(jQuery);

/*
 * jQuery UI Slider
 *
 * Copyright (c) 2008 Paul Bakaus
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Slider
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.fn.unwrap = $.fn.unwrap || function(expr) {
  return this.each(function(){
     $(this).parents(expr).eq(0).after(this).remove();
  });
};

$.widget("ui.slider", {
	plugins: {},
	ui: function(e) {
		return {
			options: this.options,
			handle: this.currentHandle,
			value: this.options.axis != "both" || !this.options.axis ? Math.round(this.value(null,this.options.axis == "vertical" ? "y" : "x")) : {
				x: Math.round(this.value(null,"x")),
				y: Math.round(this.value(null,"y"))
			},
			range: this.getRange()
		};
	},
	propagate: function(n,e) {
		$.ui.plugin.call(this, n, [e, this.ui()]);
		this.element.triggerHandler(n == "slide" ? n : "slide"+n, [e, this.ui()], this.options[n]);
	},
	destroy: function() {
		
		this.element
			.removeClass("ui-slider ui-slider-disabled")
			.removeData("slider")
			.unbind(".slider");
		
		if(this.handle && this.handle.length) {
			this.handle
				.unwrap("a");
			this.handle.each(function() {
				$(this).data("mouse").mouseDestroy();
			});
		}
		
		this.generated && this.generated.remove();
		
	},
	setData: function(key, value) {
		$.widget.prototype.setData.apply(this, arguments);
		if (/min|max|steps/.test(key)) {
			this.initBoundaries();
		}
		
		if(key == "range") {
			value ? this.handle.length == 2 && this.createRange() : this.removeRange();
		}
		
	},

	init: function() {
		
		var self = this;
		this.element.addClass("ui-slider");
		this.initBoundaries();
		
		// Initialize mouse and key events for interaction
		this.handle = $(this.options.handle, this.element);
		if (!this.handle.length) {
			self.handle = self.generated = $(self.options.handles || [0]).map(function() {
				var handle = $("<div/>").addClass("ui-slider-handle").appendTo(self.element);
				if (this.id)
					handle.attr("id", this.id);
				return handle[0];
			});
		}
		
		
		var handleclass = function(el) {
			this.element = $(el);
			this.element.data("mouse", this);
			this.options = self.options;
			
			this.element.bind("mousedown", function() {
				if(self.currentHandle) this.blur(self.currentHandle);
				self.focus(this,1);
			});
			
			this.mouseInit();
		};
		
		$.extend(handleclass.prototype, $.ui.mouse, {
			mouseStart: function(e) { return self.start.call(self, e, this.element[0]); },
			mouseStop: function(e) { return self.stop.call(self, e, this.element[0]); },
			mouseDrag: function(e) { return self.drag.call(self, e, this.element[0]); },
			mouseCapture: function() { return true; },
			trigger: function(e) { this.mouseDown(e); }
		});
		
		
		$(this.handle)
			.each(function() {
				new handleclass(this);
			})
			.wrap('<a href="javascript:void(0)" style="outline:none;border:none;"></a>')
			.parent()
				.bind('focus', function(e) { self.focus(this.firstChild); })
				.bind('blur', function(e) { self.blur(this.firstChild); })
				.bind('keydown', function(e) { if(!self.options.noKeyboard) self.keydown(e.keyCode, this.firstChild); })
		;
		
		// Bind the click to the slider itself
		this.element.bind('mousedown.slider', function(e) {
			self.click.apply(self, [e]);
			self.currentHandle.data("mouse").trigger(e);
			self.firstValue = self.firstValue + 1; //This is for always triggering the change event
		});
		
		// Move the first handle to the startValue
		$.each(this.options.handles || [], function(index, handle) {
			self.moveTo(handle.start, index, true);
		});
		if (!isNaN(this.options.startValue))
			this.moveTo(this.options.startValue, 0, true);

		this.previousHandle = $(this.handle[0]); //set the previous handle to the first to allow clicking before selecting the handle
		if(this.handle.length == 2 && this.options.range) this.createRange();
	},
	initBoundaries: function() {
		
		var element = this.element[0], o = this.options;
		this.actualSize = { width: this.element.outerWidth() , height: this.element.outerHeight() };			
		
		$.extend(o, {
			axis: o.axis || (element.offsetWidth < element.offsetHeight ? 'vertical' : 'horizontal'),
			max: !isNaN(parseInt(o.max,10)) ? { x: parseInt(o.max, 10), y: parseInt(o.max, 10) } : ({ x: o.max && o.max.x || 100, y: o.max && o.max.y || 100 }),
			min: !isNaN(parseInt(o.min,10)) ? { x: parseInt(o.min, 10), y: parseInt(o.min, 10) } : ({ x: o.min && o.min.x || 0, y: o.min && o.min.y || 0 })
		});
		//Prepare the real maxValue
		o.realMax = {
			x: o.max.x - o.min.x,
			y: o.max.y - o.min.y
		};
		//Calculate stepping based on steps
		o.stepping = {
			x: o.stepping && o.stepping.x || parseInt(o.stepping, 10) || (o.steps ? o.realMax.x/(o.steps.x || parseInt(o.steps, 10) || o.realMax.x) : 0),
			y: o.stepping && o.stepping.y || parseInt(o.stepping, 10) || (o.steps ? o.realMax.y/(o.steps.y || parseInt(o.steps, 10) || o.realMax.y) : 0)
		};
	},

	
	keydown: function(keyCode, handle) {
		var k = keyCode;
		if(/(33|34|35|36|37|38|39|40)/.test(k)) {
			var o = this.options, xpos, ypos;
			if (/(35|36)/.test(k)) {
				xpos = (k == 35) ? o.max.x : o.min.x;
				ypos = (k == 35) ? o.max.y : o.min.y;
			} else {
				var oper = /(34|37|40)/.test(k) ? "-=" : "+=";
				var step = /(37|38|39|40)/.test(k) ? "oneStep" : "pageStep";
				xpos = oper + this[step]("x");
				ypos = oper + this[step]("y");
			}
			this.moveTo({
				x: xpos,
				y: ypos
			}, handle);
		}
	},
	focus: function(handle,hard) {
		this.currentHandle = $(handle).addClass('ui-slider-handle-active');
		if (hard)
			this.currentHandle.parent()[0].focus();
	},
	blur: function(handle) {
		$(handle).removeClass('ui-slider-handle-active');
		if(this.currentHandle && this.currentHandle[0] == handle) { this.previousHandle = this.currentHandle; this.currentHandle = null; };
	},
	click: function(e) {
		// This method is only used if:
		// - The user didn't click a handle
		// - The Slider is not disabled
		// - There is a current, or previous selected handle (otherwise we wouldn't know which one to move)
		
		var pointer = [e.pageX,e.pageY];
		
		var clickedHandle = false;
		this.handle.each(function() {
			if(this == e.target)
				clickedHandle = true;
		});
		if (clickedHandle || this.options.disabled || !(this.currentHandle || this.previousHandle))
			return;

		// If a previous handle was focussed, focus it again
		if (!this.currentHandle && this.previousHandle)
			this.focus(this.previousHandle, true);
		
		// propagate only for distance > 0, otherwise propagation is done my drag
		this.offset = this.element.offset();

		this.moveTo({
			y: this.convertValue(e.pageY - this.offset.top - this.currentHandle[0].offsetHeight/2, "y"),
			x: this.convertValue(e.pageX - this.offset.left - this.currentHandle[0].offsetWidth/2, "x")
		}, null, !this.options.distance);
	},
	


	createRange: function() {
		if(this.rangeElement) return;
		this.rangeElement = $('<div></div>')
			.addClass('ui-slider-range')
			.css({ position: 'absolute' })
			.appendTo(this.element);
		this.updateRange();
	},
	removeRange: function() {
		this.rangeElement.remove();
		this.rangeElement = null;
	},
	updateRange: function() {
			var prop = this.options.axis == "vertical" ? "top" : "left";
			var size = this.options.axis == "vertical" ? "height" : "width";
			this.rangeElement.css(prop, (parseInt($(this.handle[0]).css(prop),10) || 0) + this.handleSize(0, this.options.axis == "vertical" ? "y" : "x")/2);
			this.rangeElement.css(size, (parseInt($(this.handle[1]).css(prop),10) || 0) - (parseInt($(this.handle[0]).css(prop),10) || 0));
	},
	getRange: function() {
		return this.rangeElement ? this.convertValue(parseInt(this.rangeElement.css(this.options.axis == "vertical" ? "height" : "width"),10), this.options.axis == "vertical" ? "y" : "x") : null;
	},

	handleIndex: function() {
		return this.handle.index(this.currentHandle[0]);
	},
	value: function(handle, axis) {
		if(this.handle.length == 1) this.currentHandle = this.handle;
		if(!axis) axis = this.options.axis == "vertical" ? "y" : "x";

		var curHandle = $(handle != undefined && handle !== null ? this.handle[handle] || handle : this.currentHandle);
		
		if(curHandle.data("mouse").sliderValue) {
			return parseInt(curHandle.data("mouse").sliderValue[axis],10);
		} else {
			return parseInt(((parseInt(curHandle.css(axis == "x" ? "left" : "top"),10) / (this.actualSize[axis == "x" ? "width" : "height"] - this.handleSize(handle,axis))) * this.options.realMax[axis]) + this.options.min[axis],10);
		}

	},
	convertValue: function(value,axis) {
		return this.options.min[axis] + (value / (this.actualSize[axis == "x" ? "width" : "height"] - this.handleSize(null,axis))) * this.options.realMax[axis];
	},
	
	translateValue: function(value,axis) {
		return ((value - this.options.min[axis]) / this.options.realMax[axis]) * (this.actualSize[axis == "x" ? "width" : "height"] - this.handleSize(null,axis));
	},
	translateRange: function(value,axis) {
		if (this.rangeElement) {
			if (this.currentHandle[0] == this.handle[0] && value >= this.translateValue(this.value(1),axis))
				value = this.translateValue(this.value(1,axis) - this.oneStep(axis), axis);
			if (this.currentHandle[0] == this.handle[1] && value <= this.translateValue(this.value(0),axis))
				value = this.translateValue(this.value(0,axis) + this.oneStep(axis), axis);
		}
		if (this.options.handles) {
			var handle = this.options.handles[this.handleIndex()];
			if (value < this.translateValue(handle.min,axis)) {
				value = this.translateValue(handle.min,axis);
			} else if (value > this.translateValue(handle.max,axis)) {
				value = this.translateValue(handle.max,axis);
			}
		}
		return value;
	},
	translateLimits: function(value,axis) {
		if (value >= this.actualSize[axis == "x" ? "width" : "height"] - this.handleSize(null,axis))
			value = this.actualSize[axis == "x" ? "width" : "height"] - this.handleSize(null,axis);
		if (value <= 0)
			value = 0;
		return value;
	},
	handleSize: function(handle,axis) {
		return $(handle != undefined && handle !== null ? this.handle[handle] : this.currentHandle)[0]["offset"+(axis == "x" ? "Width" : "Height")];	
	},
	oneStep: function(axis) {
		return this.options.stepping[axis] || 1;
	},
	pageStep: function(axis) {
		return /* this.options.paging[axis] ||*/ 10;
	},


	start: function(e, handle) {
	
		var o = this.options;
		if(o.disabled) return false;

		// Prepare the outer size
		this.actualSize = { width: this.element.outerWidth() , height: this.element.outerHeight() };
	
		// This is a especially ugly fix for strange blur events happening on mousemove events
		if (!this.currentHandle)
			this.focus(this.previousHandle, true); 

		this.offset = this.element.offset();
		
		this.handleOffset = this.currentHandle.offset();
		this.clickOffset = { top: e.pageY - this.handleOffset.top, left: e.pageX - this.handleOffset.left };
		
		this.firstValue = this.value();
		
		this.propagate('start', e);
		this.drag(e, handle);
		return true;
					
	},
	stop: function(e) {
		this.propagate('stop', e);
		if (this.firstValue != this.value())
			this.propagate('change', e);
		// This is a especially ugly fix for strange blur events happening on mousemove events
		this.focus(this.currentHandle, true);
		return false;
	},
	drag: function(e, handle) {

		var o = this.options;
		var position = { top: e.pageY - this.offset.top - this.clickOffset.top, left: e.pageX - this.offset.left - this.clickOffset.left};
		if(!this.currentHandle) this.focus(this.previousHandle, true); //This is a especially ugly fix for strange blur events happening on mousemove events

		position.left = this.translateLimits(position.left, "x");
		position.top = this.translateLimits(position.top, "y");
		
		if (o.stepping.x) {
			var value = this.convertValue(position.left, "x");
			value = Math.round(value / o.stepping.x) * o.stepping.x;
			position.left = this.translateValue(value, "x");	
		}
		if (o.stepping.y) {
			var value = this.convertValue(position.top, "y");
			value = Math.round(value / o.stepping.y) * o.stepping.y;
			position.top = this.translateValue(value, "y");	
		}
		
		position.left = this.translateRange(position.left, "x");
		position.top = this.translateRange(position.top, "y");

		if(o.axis != "vertical") this.currentHandle.css({ left: position.left });
		if(o.axis != "horizontal") this.currentHandle.css({ top: position.top });
		
		//Store the slider's value
		this.currentHandle.data("mouse").sliderValue = {
			x: Math.round(this.convertValue(position.left, "x")) || 0,
			y: Math.round(this.convertValue(position.top, "y")) || 0
		};
		
		if (this.rangeElement)
			this.updateRange();
		this.propagate('slide', e);
		return false;
	},
	
	moveTo: function(value, handle, noPropagation) {

		var o = this.options;

		// Prepare the outer size
		this.actualSize = { width: this.element.outerWidth() , height: this.element.outerHeight() };

		//If no handle has been passed, no current handle is available and we have multiple handles, return false
		if (handle == undefined && !this.currentHandle && this.handle.length != 1)
			return false; 
		
		//If only one handle is available, use it
		if (handle == undefined && !this.currentHandle)
			handle = 0;
		
		if (handle != undefined)
			this.currentHandle = this.previousHandle = $(this.handle[handle] || handle);


		if(value.x !== undefined && value.y !== undefined) {
			var x = value.x, y = value.y;
		} else {
			var x = value, y = value;
		}

		if(x !== undefined && x.constructor != Number) {
			var me = /^\-\=/.test(x), pe = /^\+\=/.test(x);
			if(me || pe) {
				x = this.value(null, "x") + parseInt(x.replace(me ? '=' : '+=', ''), 10);
			} else {
				x = isNaN(parseInt(x, 10)) ? undefined : parseInt(x, 10);
			}
		}
		
		if(y !== undefined && y.constructor != Number) {
			var me = /^\-\=/.test(y), pe = /^\+\=/.test(y);
			if(me || pe) {
				y = this.value(null, "y") + parseInt(y.replace(me ? '=' : '+=', ''), 10);
			} else {
				y = isNaN(parseInt(y, 10)) ? undefined : parseInt(y, 10);
			}
		}

		if(o.axis != "vertical" && x !== undefined) {
			if(o.stepping.x) x = Math.round(x / o.stepping.x) * o.stepping.x;
			x = this.translateValue(x, "x");
			x = this.translateLimits(x, "x");
			x = this.translateRange(x, "x");

			o.animate ? this.currentHandle.stop().animate({ left: x }, (Math.abs(parseInt(this.currentHandle.css("left")) - x)) * (!isNaN(parseInt(o.animate)) ? o.animate : 5)) : this.currentHandle.css({ left: x });
		}

		if(o.axis != "horizontal" && y !== undefined) {
			if(o.stepping.y) y = Math.round(y / o.stepping.y) * o.stepping.y;
			y = this.translateValue(y, "y");
			y = this.translateLimits(y, "y");
			y = this.translateRange(y, "y");
			o.animate ? this.currentHandle.stop().animate({ top: y }, (Math.abs(parseInt(this.currentHandle.css("top")) - y)) * (!isNaN(parseInt(o.animate)) ? o.animate : 5)) : this.currentHandle.css({ top: y });
		}
		
		if (this.rangeElement)
			this.updateRange();
			
		//Store the slider's value
		this.currentHandle.data("mouse").sliderValue = {
			x: Math.round(this.convertValue(x, "x")) || 0,
			y: Math.round(this.convertValue(y, "y")) || 0
		};
	
		if (!noPropagation) {
			this.propagate('start', null);
			this.propagate('stop', null);
			this.propagate('change', null);
			this.propagate("slide", null);
		}
	}
});

$.ui.slider.getter = "value";

$.ui.slider.defaults = {
	handle: ".ui-slider-handle",
	distance: 1,
	animate: false
};

})(jQuery);

/*
 * jQuery UI Sortable
 *
 * Copyright (c) 2008 Paul Bakaus
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Sortables
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

function contains(a, b) { 
    var safari2 = $.browser.safari && $.browser.version < 522; 
    if (a.contains && !safari2) { 
        return a.contains(b); 
    } 
    if (a.compareDocumentPosition) 
        return !!(a.compareDocumentPosition(b) & 16); 
    while (b = b.parentNode) 
          if (b == a) return true; 
    return false; 
};

$.widget("ui.sortable", $.extend({}, $.ui.mouse, {
	init: function() {

		var o = this.options;
		this.containerCache = {};
		this.element.addClass("ui-sortable");
	
		//Get the items
		this.refresh();

		//Let's determine if the items are floating
		this.floating = this.items.length ? (/left|right/).test(this.items[0].item.css('float')) : false;
		
		//Let's determine the parent's offset
		//if(!(/(relative|absolute|fixed)/).test(this.element.css('position'))) this.element.css('position', 'relative');
		this.offset = this.element.offset();

		//Initialize mouse events for interaction
		this.mouseInit();
		
	},
	plugins: {},
	ui: function(inst) {
		return {
			helper: (inst || this)["helper"],
			placeholder: (inst || this)["placeholder"] || $([]),
			position: (inst || this)["position"],
			absolutePosition: (inst || this)["positionAbs"],
			options: this.options,
			element: this.element,
			item: (inst || this)["currentItem"],
			sender: inst ? inst.element : null
		};		
	},
	
	propagate: function(n,e,inst, noPropagation) {
		$.ui.plugin.call(this, n, [e, this.ui(inst)]);
		if(!noPropagation) this.element.triggerHandler(n == "sort" ? n : "sort"+n, [e, this.ui(inst)], this.options[n]);
	},
	
	serialize: function(o) {

		var items = this.getItemsAsjQuery(o && o.connected);
		var str = []; o = o || {};
		
		$(items).each(function() {
			var res = ($(this.item || this).attr(o.attribute || 'id') || '').match(o.expression || (/(.+)[-=_](.+)/));
			if(res) str.push((o.key || res[1])+'[]='+(o.key && o.expression ? res[1] : res[2]));
		});
		
		return str.join('&');
		
	},
	
	toArray: function(attr) {
		
		var items = this.getItemsAsjQuery(o && o.connected);
		var ret = [];

		items.each(function() { ret.push($(this).attr(attr || 'id')); });
		return ret;
		
	},
	
	/* Be careful with the following core functions */
	intersectsWith: function(item) {
		var x1 = this.positionAbs.left, x2 = x1 + this.helperProportions.width,
		y1 = this.positionAbs.top, y2 = y1 + this.helperProportions.height;
		var l = item.left, r = l + item.width, 
		t = item.top, b = t + item.height;
		
		var dyClick = this.offset.click.top, dxClick = this.offset.click.left;
		var isOverElement = (y1 + dyClick) > t && (y1 + dyClick) < b && (x1 + dxClick) > l && (x1 + dxClick) < r;
		
		if(this.options.tolerance == "pointer" || this.options.forcePointerForContainers || (this.options.tolerance == "guess" && this.helperProportions[this.floating ? 'width' : 'height'] > item[this.floating ? 'width' : 'height'])) {
			return isOverElement;
		} else {
		
			return (l < x1 + (this.helperProportions.width / 2) // Right Half
				&& x2 - (this.helperProportions.width / 2) < r // Left Half
				&& t < y1 + (this.helperProportions.height / 2) // Bottom Half
				&& y2 - (this.helperProportions.height / 2) < b ); // Top Half
		
		}
	},
	
	intersectsWithEdge: function(item) {	
		var x1 = this.positionAbs.left, x2 = x1 + this.helperProportions.width,
			y1 = this.positionAbs.top, y2 = y1 + this.helperProportions.height;
		
		var l = item.left, r = l + item.width, 
			t = item.top, b = t + item.height;
		
		var dyClick = this.offset.click.top, dxClick = this.offset.click.left;
		var isOverElement = (y1 + dyClick) > t && (y1 + dyClick) < b && (x1 + dxClick) > l && (x1 + dxClick) < r;
		
		if(this.options.tolerance == "pointer" || (this.options.tolerance == "guess" && this.helperProportions[this.floating ? 'width' : 'height'] > item[this.floating ? 'width' : 'height'])) {
			if(!isOverElement) return false;

			if(this.floating) {
				if ((x1 + dxClick) > l && (x1 + dxClick) < l + item.width/2) return 2;
				if ((x1 + dxClick) > l + item.width/2 && (x1 + dxClick) < r) return 1;
			} else {
				var height = item.height;
				var direction = y1 - this.updateOriginalPosition.top < 0 ? 2 : 1; // 2 = up
				
				if (direction == 1 && (y1 + dyClick) < t + height/2) { return 2; } // up
				else if (direction == 2 && (y1 + dyClick) > t + height/2) { return 1; } // down
			}

		} else {
			if (!(l < x1 + (this.helperProportions.width / 2) // Right Half
				&& x2 - (this.helperProportions.width / 2) < r // Left Half
				&& t < y1 + (this.helperProportions.height / 2) // Bottom Half
				&& y2 - (this.helperProportions.height / 2) < b )) return false; // Top Half
			
			if(this.floating) {
				if(x2 > l && x1 < l) return 2; //Crosses left edge
				if(x1 < r && x2 > r) return 1; //Crosses right edge
			} else {
				if(y2 > t && y1 < t) return 1; //Crosses top edge
				if(y1 < b && y2 > b) return 2; //Crosses bottom edge
			}
		}
		
		return false;
		
	},
	
	refresh: function() {
		this.refreshItems();
		this.refreshPositions();
	},
	
	getItemsAsjQuery: function(connected) {
		
		var self = this;
		var items = [];
		var queries = [];
	
		if(this.options.connectWith && connected) {
			for (var i = this.options.connectWith.length - 1; i >= 0; i--){
				var cur = $(this.options.connectWith[i]);
				for (var j = cur.length - 1; j >= 0; j--){
					var inst = $.data(cur[j], 'sortable');
					if(inst && inst != this && !inst.options.disabled) {
						queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element) : $(inst.options.items, inst.element).not(".ui-sortable-helper"), inst]);
					}
				};
			};
		}
		
		queries.push([$.isFunction(this.options.items) ? this.options.items.call(this.element, null, { options: this.options, item: this.currentItem }) : $(this.options.items, this.element).not(".ui-sortable-helper"), this]);

		for (var i = queries.length - 1; i >= 0; i--){
			queries[i][0].each(function() {
				items.push(this);
			});
		};
		
		return $(items);
		
	},
	
	removeCurrentsFromItems: function() {
			
		var list = this.currentItem.find(":data(sortable-item)");	
	
		for (var i=0; i < this.items.length; i++) {
			
			for (var j=0; j < list.length; j++) {
				if(list[j] == this.items[i].item[0])
					this.items.splice(i,1);
			};
		
		};
		
	},
	
	refreshItems: function() {
		
		this.items = [];
		this.containers = [this];
		var items = this.items;
		var self = this;
		var queries = [[$.isFunction(this.options.items) ? this.options.items.call(this.element, null, { options: this.options, item: this.currentItem }) : $(this.options.items, this.element), this]];
	
		if(this.options.connectWith) {
			for (var i = this.options.connectWith.length - 1; i >= 0; i--){
				var cur = $(this.options.connectWith[i]);
				for (var j = cur.length - 1; j >= 0; j--){
					var inst = $.data(cur[j], 'sortable');
					if(inst && inst != this && !inst.options.disabled) {
						queries.push([$.isFunction(inst.options.items) ? inst.options.items.call(inst.element) : $(inst.options.items, inst.element), inst]);
						this.containers.push(inst);
					}
				};
			};
		}

		for (var i = queries.length - 1; i >= 0; i--){
			queries[i][0].each(function() {
				$.data(this, 'sortable-item', queries[i][1]); // Data for target checking (mouse manager)
				items.push({
					item: $(this),
					instance: queries[i][1],
					width: 0, height: 0,
					left: 0, top: 0
				});
			});
		};

	},
	
	refreshPositions: function(fast) {

		//This has to be redone because due to the item being moved out/into the offsetParent, the offsetParent's position will change
		if(this.offsetParent) {
			var po = this.offsetParent.offset();
			this.offset.parent = { top: po.top + this.offsetParentBorders.top, left: po.left + this.offsetParentBorders.left };
		}

		for (var i = this.items.length - 1; i >= 0; i--){		
			
			//We ignore calculating positions of all connected containers when we're not over them
			if(this.items[i].instance != this.currentContainer && this.currentContainer && this.items[i].item[0] != this.currentItem[0])
				continue;
				
			var t = this.options.toleranceElement ? $(this.options.toleranceElement, this.items[i].item) : this.items[i].item;
			
			if(!fast) {
				this.items[i].width = t[0].offsetWidth;
				this.items[i].height = t[0].offsetHeight;
			}
			
			var p = t.offset();
			this.items[i].left = p.left;
			this.items[i].top = p.top;
			
		};

		if(this.options.custom && this.options.custom.refreshContainers) {
			this.options.custom.refreshContainers.call(this);
		} else {
			for (var i = this.containers.length - 1; i >= 0; i--){
				var p =this.containers[i].element.offset();
				this.containers[i].containerCache.left = p.left;
				this.containers[i].containerCache.top = p.top;
				this.containers[i].containerCache.width	= this.containers[i].element.outerWidth();
				this.containers[i].containerCache.height = this.containers[i].element.outerHeight();
			};
		}

	},
	
	destroy: function() {
		this.element
			.removeClass("ui-sortable ui-sortable-disabled")
			.removeData("sortable")
			.unbind(".sortable");
		this.mouseDestroy();
		
		for ( var i = this.items.length - 1; i >= 0; i-- )
			this.items[i].item.removeData("sortable-item");
	},
	
	createPlaceholder: function(that) {
		
		var self = that || this, o = self.options;

		if(!o.placeholder || o.placeholder.constructor == String) {
			var className = o.placeholder;
			o.placeholder = {
				element: function() {
					var el = $(document.createElement(self.currentItem[0].nodeName)).addClass(className || "ui-sortable-placeholder")[0];
					if(!className) { el.style.visibility = "hidden"; el.innerHTML = self.currentItem[0].innerHTML; };
					return el;
				},
				update: function(container, p) {
					if(className) return;
					if(!p.height()) { p.height(self.currentItem.innerHeight()); };
					if(!p.width()) { p.width(self.currentItem.innerWidth()); };
				}
			};
		}
		
		self.placeholder = $(o.placeholder.element.call(self.element, self.currentItem)).appendTo(self.currentItem.parent());
		self.currentItem.before(self.placeholder);
		o.placeholder.update(self, self.placeholder);

	},
	
	contactContainers: function(e) {
		for (var i = this.containers.length - 1; i >= 0; i--){

			if(this.intersectsWith(this.containers[i].containerCache)) {
				if(!this.containers[i].containerCache.over) {
					

					if(this.currentContainer != this.containers[i]) {
						
						//When entering a new container, we will find the item with the least distance and append our item near it
						var dist = 10000; var itemWithLeastDistance = null; var base = this.positionAbs[this.containers[i].floating ? 'left' : 'top'];
						for (var j = this.items.length - 1; j >= 0; j--) {
							if(!contains(this.containers[i].element[0], this.items[j].item[0])) continue;
							var cur = this.items[j][this.containers[i].floating ? 'left' : 'top'];
							if(Math.abs(cur - base) < dist) {
								dist = Math.abs(cur - base); itemWithLeastDistance = this.items[j];
							}
						}
						
						if(!itemWithLeastDistance && !this.options.dropOnEmpty) //Check if dropOnEmpty is enabled
							continue;
						
						this.currentContainer = this.containers[i];
						itemWithLeastDistance ? this.options.sortIndicator.call(this, e, itemWithLeastDistance, null, true) : this.options.sortIndicator.call(this, e, null, this.containers[i].element, true);
						this.propagate("change", e); //Call plugins and callbacks
						this.containers[i].propagate("change", e, this); //Call plugins and callbacks
						
						//Update the placeholder
						this.options.placeholder.update(this.currentContainer, this.placeholder);

					}
					
					this.containers[i].propagate("over", e, this);
					this.containers[i].containerCache.over = 1;
				}
			} else {
				if(this.containers[i].containerCache.over) {
					this.containers[i].propagate("out", e, this);
					this.containers[i].containerCache.over = 0;
				}
			}
			
		};			
	},
	
	mouseCapture: function(e, overrideHandle) {
	
		if(this.options.disabled || this.options.type == 'static') return false;

		//We have to refresh the items data once first
		this.refreshItems();

		//Find out if the clicked node (or one of its parents) is a actual item in this.items
		var currentItem = null, self = this, nodes = $(e.target).parents().each(function() {	
			if($.data(this, 'sortable-item') == self) {
				currentItem = $(this);
				return false;
			}
		});
		if($.data(e.target, 'sortable-item') == self) currentItem = $(e.target);

		if(!currentItem) return false;
		if(this.options.handle && !overrideHandle) {
			var validHandle = false;
			
			$(this.options.handle, currentItem).find("*").andSelf().each(function() { if(this == e.target) validHandle = true; });
			if(!validHandle) return false;
		}
			
		this.currentItem = currentItem;
		this.removeCurrentsFromItems();
		return true;	
			
	},
	
	mouseStart: function(e, overrideHandle, noActivation) {

		var o = this.options;
		this.currentContainer = this;

		//We only need to call refreshPositions, because the refreshItems call has been moved to mouseCapture
		this.refreshPositions();

		//Create and append the visible helper			
		this.helper = typeof o.helper == 'function' ? $(o.helper.apply(this.element[0], [e, this.currentItem])) : (o.helper == "original" ? this.currentItem :  this.currentItem.clone());
		if (!this.helper.parents('body').length) $(o.appendTo != 'parent' ? o.appendTo : this.currentItem[0].parentNode)[0].appendChild(this.helper[0]); //Add the helper to the DOM if that didn't happen already

		/*
		 * - Position generation -
		 * This block generates everything position related - it's the core of draggables.
		 */

		this.margins = {																				//Cache the margins
			left: (parseInt(this.currentItem.css("marginLeft"),10) || 0),
			top: (parseInt(this.currentItem.css("marginTop"),10) || 0)
		};		
	
		this.offset = this.currentItem.offset();														//The element's absolute position on the page
		this.offset = {																					//Substract the margins from the element's absolute offset
			top: this.offset.top - this.margins.top,
			left: this.offset.left - this.margins.left
		};
		
		this.offset.click = {																			//Where the click happened, relative to the element
			left: e.pageX - this.offset.left,
			top: e.pageY - this.offset.top
		};
		
		this.offsetParent = this.helper.offsetParent();													//Get the offsetParent and cache its position
		var po = this.offsetParent.offset();			

		this.offsetParentBorders = {
			top: (parseInt(this.offsetParent.css("borderTopWidth"),10) || 0),
			left: (parseInt(this.offsetParent.css("borderLeftWidth"),10) || 0)
		};
		this.offset.parent = {																			//Store its position plus border
			top: po.top + this.offsetParentBorders.top,
			left: po.left + this.offsetParentBorders.left
		};
	
		this.updateOriginalPosition = this.originalPosition = this.generatePosition(e);				//Generate the original position
		this.domPosition = { prev: this.currentItem.prev()[0], parent: this.currentItem.parent()[0] };  //Cache the former DOM position
		
		//If o.placeholder is used, create a new element at the given position with the class
		this.helperProportions = { width: this.helper.outerWidth(), height: this.helper.outerHeight() };//Cache the helper size


		if(o.helper == "original") {
			this._storedCSS = { position: this.currentItem.css("position"), top: this.currentItem.css("top"), left: this.currentItem.css("left"), clear: this.currentItem.css("clear") };
		}
		
		if(o.helper != "original") this.currentItem.hide(); //Hide the original, won't cause anything bad this way
		this.helper.css({ position: 'absolute', clear: 'both' }).addClass('ui-sortable-helper'); //Position it absolutely and add a helper class
		this.createPlaceholder();

		//Call plugins and callbacks
		this.propagate("start", e);
		if(!this._preserveHelperProportions) this.helperProportions = { width: this.helper.outerWidth(), height: this.helper.outerHeight() };//Recache the helper size
		
		if(o.cursorAt) {
			if(o.cursorAt.left != undefined) this.offset.click.left = o.cursorAt.left;
			if(o.cursorAt.right != undefined) this.offset.click.left = this.helperProportions.width - o.cursorAt.right;
			if(o.cursorAt.top != undefined) this.offset.click.top = o.cursorAt.top;
			if(o.cursorAt.bottom != undefined) this.offset.click.top = this.helperProportions.height - o.cursorAt.bottom;
		}

		/*
		 * - Position constraining -
		 * Here we prepare position constraining like grid and containment.
		 */	
		
		if(o.containment) {
			if(o.containment == 'parent') o.containment = this.helper[0].parentNode;
			if(o.containment == 'document' || o.containment == 'window') this.containment = [
				0 - this.offset.parent.left,
				0 - this.offset.parent.top,
				$(o.containment == 'document' ? document : window).width() - this.offset.parent.left - this.helperProportions.width - this.margins.left - (parseInt(this.element.css("marginRight"),10) || 0),
				($(o.containment == 'document' ? document : window).height() || document.body.parentNode.scrollHeight) - this.offset.parent.top - this.helperProportions.height - this.margins.top - (parseInt(this.element.css("marginBottom"),10) || 0)
			];

			if(!(/^(document|window|parent)$/).test(o.containment)) {
				var ce = $(o.containment)[0];
				var co = $(o.containment).offset();
				
				this.containment = [
					co.left + (parseInt($(ce).css("borderLeftWidth"),10) || 0) - this.offset.parent.left,
					co.top + (parseInt($(ce).css("borderTopWidth"),10) || 0) - this.offset.parent.top,
					co.left+Math.max(ce.scrollWidth,ce.offsetWidth) - (parseInt($(ce).css("borderLeftWidth"),10) || 0) - this.offset.parent.left - this.helperProportions.width - this.margins.left - (parseInt(this.currentItem.css("marginRight"),10) || 0),
					co.top+Math.max(ce.scrollHeight,ce.offsetHeight) - (parseInt($(ce).css("borderTopWidth"),10) || 0) - this.offset.parent.top - this.helperProportions.height - this.margins.top - (parseInt(this.currentItem.css("marginBottom"),10) || 0)
				];
			}
		}
		
		//Post 'activate' events to possible containers
		if(!noActivation) {
			 for (var i = this.containers.length - 1; i >= 0; i--) { this.containers[i].propagate("activate", e, this); }
		}
		
		//Prepare possible droppables
		if($.ui.ddmanager) $.ui.ddmanager.current = this;
		if ($.ui.ddmanager && !o.dropBehaviour) $.ui.ddmanager.prepareOffsets(this, e);

		this.dragging = true;

		this.mouseDrag(e); //Execute the drag once - this causes the helper not to be visible before getting its correct position
		return true;


	},
	
	convertPositionTo: function(d, pos) {
		if(!pos) pos = this.position;
		var mod = d == "absolute" ? 1 : -1;
		return {
			top: (
				pos.top																	// the calculated relative position
				+ this.offset.parent.top * mod											// The offsetParent's offset without borders (offset + border)
				- (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop) * mod	// The offsetParent's scroll position
				+ this.margins.top * mod												//Add the margin (you don't want the margin counting in intersection methods)
			),
			left: (
				pos.left																// the calculated relative position
				+ this.offset.parent.left * mod											// The offsetParent's offset without borders (offset + border)
				- (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft) * mod	// The offsetParent's scroll position
				+ this.margins.left * mod												//Add the margin (you don't want the margin counting in intersection methods)
			)
		};
	},
	
	generatePosition: function(e) {
		
		var o = this.options;
		var position = {
			top: (
				e.pageY																	// The absolute mouse position
				- this.offset.click.top													// Click offset (relative to the element)
				- this.offset.parent.top												// The offsetParent's offset without borders (offset + border)
				+ (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop)	// The offsetParent's scroll position, not if the element is fixed
			),
			left: (
				e.pageX																	// The absolute mouse position
				- this.offset.click.left												// Click offset (relative to the element)
				- this.offset.parent.left												// The offsetParent's offset without borders (offset + border)
				+ (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft)	// The offsetParent's scroll position, not if the element is fixed
			)
		};
		
		if(!this.originalPosition) return position;										//If we are not dragging yet, we won't check for options
		
		/*
		 * - Position constraining -
		 * Constrain the position to a mix of grid, containment.
		 */
		if(this.containment) {
			if(position.left < this.containment[0]) position.left = this.containment[0];
			if(position.top < this.containment[1]) position.top = this.containment[1];
			if(position.left > this.containment[2]) position.left = this.containment[2];
			if(position.top > this.containment[3]) position.top = this.containment[3];
		}
		
		if(o.grid) {
			var top = this.originalPosition.top + Math.round((position.top - this.originalPosition.top) / o.grid[1]) * o.grid[1];
			position.top = this.containment ? (!(top < this.containment[1] || top > this.containment[3]) ? top : (!(top < this.containment[1]) ? top - o.grid[1] : top + o.grid[1])) : top;
			
			var left = this.originalPosition.left + Math.round((position.left - this.originalPosition.left) / o.grid[0]) * o.grid[0];
			position.left = this.containment ? (!(left < this.containment[0] || left > this.containment[2]) ? left : (!(left < this.containment[0]) ? left - o.grid[0] : left + o.grid[0])) : left;
		}
		
		return position;
	},
	
	mouseDrag: function(e) {

		//Compute the helpers position
		this.position = this.generatePosition(e);
		this.positionAbs = this.convertPositionTo("absolute");

		//Call the internal plugins
		$.ui.plugin.call(this, "sort", [e, this.ui()]);
		
		//Regenerate the absolute position used for position checks
		this.positionAbs = this.convertPositionTo("absolute");
		
		//Set the helper's position
		this.helper[0].style.left = this.position.left+'px';
		this.helper[0].style.top = this.position.top+'px';

		//Rearrange
		for (var i = this.items.length - 1; i >= 0; i--) {
			var intersection = this.intersectsWithEdge(this.items[i]);
			if(!intersection) continue;
			
			if(this.items[i].item[0] != this.currentItem[0] //cannot intersect with itself
				&&	this.placeholder[intersection == 1 ? "next" : "prev"]()[0] != this.items[i].item[0] //no useless actions that have been done before
				&&	!contains(this.placeholder[0], this.items[i].item[0]) //no action if the item moved is the parent of the item checked
				&& (this.options.type == 'semi-dynamic' ? !contains(this.element[0], this.items[i].item[0]) : true)
			) {
				
				this.updateOriginalPosition = this.generatePosition(e);
				
				this.direction = intersection == 1 ? "down" : "up";
				this.options.sortIndicator.call(this, e, this.items[i]);
				this.propagate("change", e); //Call plugins and callbacks
				break;
			}
		}
		
		//Post events to containers
		this.contactContainers(e);
		
		//Interconnect with droppables
		if($.ui.ddmanager) $.ui.ddmanager.drag(this, e);

		//Call callbacks
		this.element.triggerHandler("sort", [e, this.ui()], this.options["sort"]);

		return false;
		
	},
	
	rearrange: function(e, i, a, hardRefresh) {

		a ? a[0].appendChild(this.placeholder[0]) : i.item[0].parentNode.insertBefore(this.placeholder[0], (this.direction == 'down' ? i.item[0] : i.item[0].nextSibling));
		
		//Various things done here to improve the performance:
		// 1. we create a setTimeout, that calls refreshPositions
		// 2. on the instance, we have a counter variable, that get's higher after every append
		// 3. on the local scope, we copy the counter variable, and check in the timeout, if it's still the same
		// 4. this lets only the last addition to the timeout stack through
		this.counter = this.counter ? ++this.counter : 1;
		var self = this, counter = this.counter;

		window.setTimeout(function() {
			if(counter == self.counter) self.refreshPositions(!hardRefresh); //Precompute after each DOM insertion, NOT on mousemove
		},0);

	},
	
	mouseStop: function(e, noPropagation) {

		//If we are using droppables, inform the manager about the drop
		if ($.ui.ddmanager && !this.options.dropBehaviour)
			$.ui.ddmanager.drop(this, e);
			
		if(this.options.revert) {
			var self = this;
			var cur = self.placeholder.offset();

			$(this.helper).animate({
				left: cur.left - this.offset.parent.left - self.margins.left + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollLeft),
				top: cur.top - this.offset.parent.top - self.margins.top + (this.offsetParent[0] == document.body ? 0 : this.offsetParent[0].scrollTop)
			}, parseInt(this.options.revert, 10) || 500, function() {
				self.clear(e);
			});
		} else {
			this.clear(e, noPropagation);
		}

		return false;
		
	},
	
	clear: function(e, noPropagation) {

		//We first have to update the dom position of the actual currentItem
		if(!this._noFinalSort) this.placeholder.before(this.currentItem);
		this._noFinalSort = null;

		if(this.options.helper == "original")
			this.currentItem.css(this._storedCSS).removeClass("ui-sortable-helper");
		else
			this.currentItem.show();

		if(this.domPosition.prev != this.currentItem.prev().not(".ui-sortable-helper")[0] || this.domPosition.parent != this.currentItem.parent()[0]) this.propagate("update", e, null, noPropagation); //Trigger update callback if the DOM position has changed
		if(!contains(this.element[0], this.currentItem[0])) { //Node was moved out of the current element
			this.propagate("remove", e, null, noPropagation);
			for (var i = this.containers.length - 1; i >= 0; i--){
				if(contains(this.containers[i].element[0], this.currentItem[0])) {
					this.containers[i].propagate("update", e, this, noPropagation);
					this.containers[i].propagate("receive", e, this, noPropagation);
				}
			};
		};
		
		//Post events to containers
		for (var i = this.containers.length - 1; i >= 0; i--){
			this.containers[i].propagate("deactivate", e, this, noPropagation);
			if(this.containers[i].containerCache.over) {
				this.containers[i].propagate("out", e, this);
				this.containers[i].containerCache.over = 0;
			}
		}
		
		this.dragging = false;
		if(this.cancelHelperRemoval) {
			this.propagate("stop", e, null, noPropagation);
			return false;
		}
		
		this.propagate("beforeStop", e, null, noPropagation);
		
		this.placeholder.remove();
		if(this.options.helper != "original") this.helper.remove(); this.helper = null;
		this.propagate("stop", e, null, noPropagation);
		
		return true;
		
	}
}));

$.extend($.ui.sortable, {
	getter: "serialize toArray",
	defaults: {
		helper: "original",
		tolerance: "guess",
		distance: 1,
		delay: 0,
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		cancel: ":input",
		items: '> *',
		zIndex: 1000,
		dropOnEmpty: true,
		appendTo: "parent",
		sortIndicator: $.ui.sortable.prototype.rearrange,
		scope: "default"
	}
});

/*
 * Sortable Extensions
 */

$.ui.plugin.add("sortable", "cursor", {
	start: function(e, ui) {
		var t = $('body');
		if (t.css("cursor")) ui.options._cursor = t.css("cursor");
		t.css("cursor", ui.options.cursor);
	},
	beforeStop: function(e, ui) {
		if (ui.options._cursor) $('body').css("cursor", ui.options._cursor);
	}
});

$.ui.plugin.add("sortable", "zIndex", {
	start: function(e, ui) {
		var t = ui.helper;
		if(t.css("zIndex")) ui.options._zIndex = t.css("zIndex");
		t.css('zIndex', ui.options.zIndex);
	},
	beforeStop: function(e, ui) {
		if(ui.options._zIndex) $(ui.helper).css('zIndex', ui.options._zIndex);
	}
});

$.ui.plugin.add("sortable", "opacity", {
	start: function(e, ui) {
		var t = ui.helper;
		if(t.css("opacity")) ui.options._opacity = t.css("opacity");
		t.css('opacity', ui.options.opacity);
	},
	beforeStop: function(e, ui) {
		if(ui.options._opacity) $(ui.helper).css('opacity', ui.options._opacity);
	}
});

$.ui.plugin.add("sortable", "scroll", {
	start: function(e, ui) {
		var o = ui.options;
		var i = $(this).data("sortable");
	
		i.overflowY = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-y'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(i.currentItem);
		i.overflowX = function(el) {
			do { if(/auto|scroll/.test(el.css('overflow')) || (/auto|scroll/).test(el.css('overflow-x'))) return el; el = el.parent(); } while (el[0].parentNode);
			return $(document);
		}(i.currentItem);
		
		if(i.overflowY[0] != document && i.overflowY[0].tagName != 'HTML') i.overflowYOffset = i.overflowY.offset();
		if(i.overflowX[0] != document && i.overflowX[0].tagName != 'HTML') i.overflowXOffset = i.overflowX.offset();
		
	},
	sort: function(e, ui) {
		
		var o = ui.options;
		var i = $(this).data("sortable");
		
		if(i.overflowY[0] != document && i.overflowY[0].tagName != 'HTML') {
			if((i.overflowYOffset.top + i.overflowY[0].offsetHeight) - e.pageY < o.scrollSensitivity)
				i.overflowY[0].scrollTop = i.overflowY[0].scrollTop + o.scrollSpeed;
			if(e.pageY - i.overflowYOffset.top < o.scrollSensitivity)
				i.overflowY[0].scrollTop = i.overflowY[0].scrollTop - o.scrollSpeed;
		} else {
			if(e.pageY - $(document).scrollTop() < o.scrollSensitivity)
				$(document).scrollTop($(document).scrollTop() - o.scrollSpeed);
			if($(window).height() - (e.pageY - $(document).scrollTop()) < o.scrollSensitivity)
				$(document).scrollTop($(document).scrollTop() + o.scrollSpeed);
		}
		
		if(i.overflowX[0] != document && i.overflowX[0].tagName != 'HTML') {
			if((i.overflowXOffset.left + i.overflowX[0].offsetWidth) - e.pageX < o.scrollSensitivity)
				i.overflowX[0].scrollLeft = i.overflowX[0].scrollLeft + o.scrollSpeed;
			if(e.pageX - i.overflowXOffset.left < o.scrollSensitivity)
				i.overflowX[0].scrollLeft = i.overflowX[0].scrollLeft - o.scrollSpeed;
		} else {
			if(e.pageX - $(document).scrollLeft() < o.scrollSensitivity)
				$(document).scrollLeft($(document).scrollLeft() - o.scrollSpeed);
			if($(window).width() - (e.pageX - $(document).scrollLeft()) < o.scrollSensitivity)
				$(document).scrollLeft($(document).scrollLeft() + o.scrollSpeed);
		}
		
	}
});

$.ui.plugin.add("sortable", "axis", {
	sort: function(e, ui) {
		
		var i = $(this).data("sortable");
		
		if(ui.options.axis == "y") i.position.left = i.originalPosition.left;
		if(ui.options.axis == "x") i.position.top = i.originalPosition.top;
		
	}
});

})(jQuery);

/*
 * jQuery UI Tabs
 *
 * Copyright (c) 2007, 2008 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * http://docs.jquery.com/UI/Tabs
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.widget("ui.tabs", {
	init: function() {
		this.options.event += '.tabs'; // namespace event
		
		// create tabs
		this.tabify(true);
	},
	setData: function(key, value) {
		if ((/^selected/).test(key))
			this.select(value);
		else {
			this.options[key] = value;
			this.tabify();
		}
	},
	length: function() {
		return this.$tabs.length;
	},
	tabId: function(a) {
		return a.title && a.title.replace(/\s/g, '_').replace(/[^A-Za-z0-9\-_:\.]/g, '')
			|| this.options.idPrefix + $.data(a);
	},
	ui: function(tab, panel) {
		return {
			options: this.options,
			tab: tab,
			panel: panel,
			index: this.$tabs.index(tab)
		};
	},
	tabify: function(init) {

		this.$lis = $('li:has(a[href])', this.element);
		this.$tabs = this.$lis.map(function() { return $('a', this)[0]; });
		this.$panels = $([]);

		var self = this, o = this.options;

		this.$tabs.each(function(i, a) {
			// inline tab
			if (a.hash && a.hash.replace('#', '')) // Safari 2 reports '#' for an empty hash
				self.$panels = self.$panels.add(a.hash);
			// remote tab
			else if ($(a).attr('href') != '#') { // prevent loading the page itself if href is just "#"
				$.data(a, 'href.tabs', a.href); // required for restore on destroy
				$.data(a, 'load.tabs', a.href); // mutable
				var id = self.tabId(a);
				a.href = '#' + id;
				var $panel = $('#' + id);
				if (!$panel.length) {
					$panel = $(o.panelTemplate).attr('id', id).addClass(o.panelClass)
						.insertAfter( self.$panels[i - 1] || self.element );
					$panel.data('destroy.tabs', true);
				}
				self.$panels = self.$panels.add( $panel );
			}
			// invalid tab href
			else
				o.disabled.push(i + 1);
		});

		if (init) {

			// attach necessary classes for styling if not present
			this.element.addClass(o.navClass);
			this.$panels.each(function() {
				var $this = $(this);
				$this.addClass(o.panelClass);
			});

			// Selected tab
			// use "selected" option or try to retrieve:
			// 1. from fragment identifier in url
			// 2. from cookie
			// 3. from selected class attribute on <li>
			if (o.selected === undefined) {
				if (location.hash) {
					this.$tabs.each(function(i, a) {
						if (a.hash == location.hash) {
							o.selected = i;
							// prevent page scroll to fragment
							if ($.browser.msie || $.browser.opera) { // && !o.remote
								var $toShow = $(location.hash), toShowId = $toShow.attr('id');
								$toShow.attr('id', '');
								setTimeout(function() {
									$toShow.attr('id', toShowId); // restore id
								}, 500);
							}
							scrollTo(0, 0);
							return false; // break
						}
					});
				}
				else if (o.cookie) {
					var index = parseInt($.cookie('ui-tabs' + $.data(self.element)),10);
					if (index && self.$tabs[index])
						o.selected = index;
				}
				else if (self.$lis.filter('.' + o.selectedClass).length)
					o.selected = self.$lis.index( self.$lis.filter('.' + o.selectedClass)[0] );
			}
			o.selected = o.selected === null || o.selected !== undefined ? o.selected : 0; // first tab selected by default

			// Take disabling tabs via class attribute from HTML
			// into account and update option properly.
			// A selected tab cannot become disabled.
			o.disabled = $.unique(o.disabled.concat(
				$.map(this.$lis.filter('.' + o.disabledClass),
					function(n, i) { return self.$lis.index(n); } )
			)).sort();
			if ($.inArray(o.selected, o.disabled) != -1)
				o.disabled.splice($.inArray(o.selected, o.disabled), 1);
			
			// highlight selected tab
			this.$panels.addClass(o.hideClass);
			this.$lis.removeClass(o.selectedClass);
			if (o.selected !== null) {
				this.$panels.eq(o.selected).show().removeClass(o.hideClass); // use show and remove class to show in any case no matter how it has been hidden before
				this.$lis.eq(o.selected).addClass(o.selectedClass);
				
				// seems to be expected behavior that the show callback is fired
				var onShow = function() {
					self.trigger('show', null,
						self.ui(self.$tabs[o.selected], self.$panels[o.selected]));
				};

				// load if remote tab
				if ($.data(this.$tabs[o.selected], 'load.tabs'))
					this.load(o.selected, onShow);
				// just trigger show event
				else
					onShow();
			}
			
			// clean up to avoid memory leaks in certain versions of IE 6
			$(window).bind('unload', function() {
				self.$tabs.unbind('.tabs');
				self.$lis = self.$tabs = self.$panels = null;
			});

		}

		// disable tabs
		for (var i = 0, li; li = this.$lis[i]; i++)
			$(li)[$.inArray(i, o.disabled) != -1 && !$(li).hasClass(o.selectedClass) ? 'addClass' : 'removeClass'](o.disabledClass);

		// reset cache if switching from cached to not cached
		if (o.cache === false)
			this.$tabs.removeData('cache.tabs');
		
		// set up animations
		var hideFx, showFx, baseFx = { 'min-width': 0, duration: 1 }, baseDuration = 'normal';
		if (o.fx && o.fx.constructor == Array)
			hideFx = o.fx[0] || baseFx, showFx = o.fx[1] || baseFx;
		else
			hideFx = showFx = o.fx || baseFx;

		// reset some styles to maintain print style sheets etc.
		var resetCSS = { display: '', overflow: '', height: '' };
		if (!$.browser.msie) // not in IE to prevent ClearType font issue
			resetCSS.opacity = '';

		// Hide a tab, animation prevents browser scrolling to fragment,
		// $show is optional.
		function hideTab(clicked, $hide, $show) {
			$hide.animate(hideFx, hideFx.duration || baseDuration, function() { //
				$hide.addClass(o.hideClass).css(resetCSS); // maintain flexible height and accessibility in print etc.
				if ($.browser.msie && hideFx.opacity)
					$hide[0].style.filter = '';
				if ($show)
					showTab(clicked, $show, $hide);
			});
		}

		// Show a tab, animation prevents browser scrolling to fragment,
		// $hide is optional.
		function showTab(clicked, $show, $hide) {
			if (showFx === baseFx)
				$show.css('display', 'block'); // prevent occasionally occuring flicker in Firefox cause by gap between showing and hiding the tab panels
			$show.animate(showFx, showFx.duration || baseDuration, function() {
				$show.removeClass(o.hideClass).css(resetCSS); // maintain flexible height and accessibility in print etc.
				if ($.browser.msie && showFx.opacity)
					$show[0].style.filter = '';

				// callback
				self.trigger('show', null, self.ui(clicked, $show[0]));
			});
		}

		// switch a tab
		function switchTab(clicked, $li, $hide, $show) {
			/*if (o.bookmarkable && trueClick) { // add to history only if true click occured, not a triggered click
				$.ajaxHistory.update(clicked.hash);
			}*/
			$li.addClass(o.selectedClass)
				.siblings().removeClass(o.selectedClass);
			hideTab(clicked, $hide, $show);
		}

		// attach tab event handler, unbind to avoid duplicates from former tabifying...
		this.$tabs.unbind('.tabs').bind(o.event, function() {

			//var trueClick = e.clientX; // add to history only if true click occured, not a triggered click
			var $li = $(this).parents('li:eq(0)'),
				$hide = self.$panels.filter(':visible'),
				$show = $(this.hash);

			// If tab is already selected and not unselectable or tab disabled or 
			// or is already loading or click callback returns false stop here.
			// Check if click handler returns false last so that it is not executed
			// for a disabled or loading tab!
			if (($li.hasClass(o.selectedClass) && !o.unselect)
				|| $li.hasClass(o.disabledClass) 
				|| $(this).hasClass(o.loadingClass)
				|| self.trigger('select', null, self.ui(this, $show[0])) === false
				) {
				this.blur();
				return false;
			}

			self.options.selected = self.$tabs.index(this);

			// if tab may be closed
			if (o.unselect) {
				if ($li.hasClass(o.selectedClass)) {
					self.options.selected = null;
					$li.removeClass(o.selectedClass);
					self.$panels.stop();
					hideTab(this, $hide);
					this.blur();
					return false;
				} else if (!$hide.length) {
					self.$panels.stop();
					var a = this;
					self.load(self.$tabs.index(this), function() {
						$li.addClass(o.selectedClass).addClass(o.unselectClass);
						showTab(a, $show);
					});
					this.blur();
					return false;
				}
			}

			if (o.cookie)
				$.cookie('ui-tabs' + $.data(self.element), self.options.selected, o.cookie);

			// stop possibly running animations
			self.$panels.stop();

			// show new tab
			if ($show.length) {

				// prevent scrollbar scrolling to 0 and than back in IE7, happens only if bookmarking/history is enabled
				/*if ($.browser.msie && o.bookmarkable) {
					var showId = this.hash.replace('#', '');
					$show.attr('id', '');
					setTimeout(function() {
						$show.attr('id', showId); // restore id
					}, 0);
				}*/

				var a = this;
				self.load(self.$tabs.index(this), $hide.length ? 
					function() {
						switchTab(a, $li, $hide, $show);
					} :
					function() {
						$li.addClass(o.selectedClass);
						showTab(a, $show);
					}
				);

				// Set scrollbar to saved position - need to use timeout with 0 to prevent browser scroll to target of hash
				/*var scrollX = window.pageXOffset || document.documentElement && document.documentElement.scrollLeft || document.body.scrollLeft || 0;
				var scrollY = window.pageYOffset || document.documentElement && document.documentElement.scrollTop || document.body.scrollTop || 0;
				setTimeout(function() {
					scrollTo(scrollX, scrollY);
				}, 0);*/

			} else
				throw 'jQuery UI Tabs: Mismatching fragment identifier.';

			// Prevent IE from keeping other link focussed when using the back button
			// and remove dotted border from clicked link. This is controlled in modern
			// browsers via CSS, also blur removes focus from address bar in Firefox
			// which can become a usability and annoying problem with tabsRotate.
			if ($.browser.msie)
				this.blur();

			//return o.bookmarkable && !!trueClick; // convert trueClick == undefined to Boolean required in IE
			return false;

		});

		// disable click if event is configured to something else
		if (!(/^click/).test(o.event))
			this.$tabs.bind('click.tabs', function() { return false; });

	},
	add: function(url, label, index) {
		if (index == undefined) 
			index = this.$tabs.length; // append by default

		var o = this.options;
		var $li = $(o.tabTemplate.replace(/#\{href\}/g, url).replace(/#\{label\}/g, label));
		$li.data('destroy.tabs', true);

		var id = url.indexOf('#') == 0 ? url.replace('#', '') : this.tabId( $('a:first-child', $li)[0] );

		// try to find an existing element before creating a new one
		var $panel = $('#' + id);
		if (!$panel.length) {
			$panel = $(o.panelTemplate).attr('id', id)
				.addClass(o.hideClass)
				.data('destroy.tabs', true);
		}
		$panel.addClass(o.panelClass);
		if (index >= this.$lis.length) {
			$li.appendTo(this.element);
			$panel.appendTo(this.element[0].parentNode);
		} else {
			$li.insertBefore(this.$lis[index]);
			$panel.insertBefore(this.$panels[index]);
		}
		
		o.disabled = $.map(o.disabled,
			function(n, i) { return n >= index ? ++n : n });
			
		this.tabify();

		if (this.$tabs.length == 1) {
			$li.addClass(o.selectedClass);
			$panel.removeClass(o.hideClass);
			var href = $.data(this.$tabs[0], 'load.tabs');
			if (href)
				this.load(index, href);
		}

		// callback
		this.trigger('add', null, this.ui(this.$tabs[index], this.$panels[index]));
	},
	remove: function(index) {
		var o = this.options, $li = this.$lis.eq(index).remove(),
			$panel = this.$panels.eq(index).remove();

		// If selected tab was removed focus tab to the right or
		// in case the last tab was removed the tab to the left.
		if ($li.hasClass(o.selectedClass) && this.$tabs.length > 1)
			this.select(index + (index + 1 < this.$tabs.length ? 1 : -1));

		o.disabled = $.map($.grep(o.disabled, function(n, i) { return n != index; }),
			function(n, i) { return n >= index ? --n : n });

		this.tabify();

		// callback
		this.trigger('remove', null, this.ui($li.find('a')[0], $panel[0]));
	},
	enable: function(index) {
		var o = this.options;
		if ($.inArray(index, o.disabled) == -1)
			return;
			
		var $li = this.$lis.eq(index).removeClass(o.disabledClass);
		if ($.browser.safari) { // fix disappearing tab (that used opacity indicating disabling) after enabling in Safari 2...
			$li.css('display', 'inline-block');
			setTimeout(function() {
				$li.css('display', 'block');
			}, 0);
		}

		o.disabled = $.grep(o.disabled, function(n, i) { return n != index; });

		// callback
		this.trigger('enable', null, this.ui(this.$tabs[index], this.$panels[index]));
	},
	disable: function(index) {
		var self = this, o = this.options;
		if (index != o.selected) { // cannot disable already selected tab
			this.$lis.eq(index).addClass(o.disabledClass);

			o.disabled.push(index);
			o.disabled.sort();

			// callback
			this.trigger('disable', null, this.ui(this.$tabs[index], this.$panels[index]));
		}
	},
	select: function(index) {
		if (typeof index == 'string')
			index = this.$tabs.index( this.$tabs.filter('[href$=' + index + ']')[0] );
		this.$tabs.eq(index).trigger(this.options.event);
	},
	load: function(index, callback) { // callback is for internal usage only
		
		var self = this, o = this.options, $a = this.$tabs.eq(index), a = $a[0],
				bypassCache = callback == undefined || callback === false, url = $a.data('load.tabs');

		callback = callback || function() {};
		
		// no remote or from cache - just finish with callback
		if (!url || !bypassCache && $.data(a, 'cache.tabs')) {
			callback();
			return;
		}

		// load remote from here on
		
		var inner = function(parent) {
			var $parent = $(parent), $inner = $parent.find('*:last');
			return $inner.length && $inner.is(':not(img)') && $inner || $parent;
		};
		var cleanup = function() {
			self.$tabs.filter('.' + o.loadingClass).removeClass(o.loadingClass)
						.each(function() {
							if (o.spinner)
								inner(this).parent().html(inner(this).data('label.tabs'));
						});
			self.xhr = null;
		};
		
		if (o.spinner) {
			var label = inner(a).html();
			inner(a).wrapInner('<em></em>')
				.find('em').data('label.tabs', label).html(o.spinner);
		}

		var ajaxOptions = $.extend({}, o.ajaxOptions, {
			url: url,
			success: function(r, s) {
				$(a.hash).html(r);
				cleanup();
				
				if (o.cache)
					$.data(a, 'cache.tabs', true); // if loaded once do not load them again

				// callbacks
				self.trigger('load', null, self.ui(self.$tabs[index], self.$panels[index]));
				o.ajaxOptions.success && o.ajaxOptions.success(r, s);
				
				// This callback is required because the switch has to take
				// place after loading has completed. Call last in order to 
				// fire load before show callback...
				callback();
			}
		});
		if (this.xhr) {
			// terminate pending requests from other tabs and restore tab label
			this.xhr.abort();
			cleanup();
		}
		$a.addClass(o.loadingClass);
		setTimeout(function() { // timeout is again required in IE, "wait" for id being restored
			self.xhr = $.ajax(ajaxOptions);
		}, 0);

	},
	url: function(index, url) {
		this.$tabs.eq(index).removeData('cache.tabs').data('load.tabs', url);
	},
	destroy: function() {
		var o = this.options;
		this.element.unbind('.tabs')
			.removeClass(o.navClass).removeData('tabs');
		this.$tabs.each(function() {
			var href = $.data(this, 'href.tabs');
			if (href)
				this.href = href;
			var $this = $(this).unbind('.tabs');
			$.each(['href', 'load', 'cache'], function(i, prefix) {
				$this.removeData(prefix + '.tabs');
			});
		});
		this.$lis.add(this.$panels).each(function() {
			if ($.data(this, 'destroy.tabs'))
				$(this).remove();
			else
				$(this).removeClass([o.selectedClass, o.unselectClass,
					o.disabledClass, o.panelClass, o.hideClass].join(' '));
		});
	}
});

$.ui.tabs.defaults = {
	// basic setup
	unselect: false,
	event: 'click',
	disabled: [],
	cookie: null, // e.g. { expires: 7, path: '/', domain: 'jquery.com', secure: true }
	// TODO history: false,

	// Ajax
	spinner: 'Loading&#8230;',
	cache: false,
	idPrefix: 'ui-tabs-',
	ajaxOptions: {},

	// animations
	fx: null, // e.g. { height: 'toggle', opacity: 'toggle', duration: 200 }

	// templates
	tabTemplate: '<li><a href="#{href}"><span>#{label}</span></a></li>',
	panelTemplate: '<div></div>',

	// CSS classes
	navClass: 'ui-tabs-nav',
	selectedClass: 'ui-tabs-selected',
	unselectClass: 'ui-tabs-unselect',
	disabledClass: 'ui-tabs-disabled',
	panelClass: 'ui-tabs-panel',
	hideClass: 'ui-tabs-hide',
	loadingClass: 'ui-tabs-loading'
};

$.ui.tabs.getter = "length";

/*
 * Tabs Extensions
 */

/*
 * Rotate
 */
$.extend($.ui.tabs.prototype, {
	rotation: null,
	rotate: function(ms, continuing) {
		
		continuing = continuing || false;
		
		var self = this, t = this.options.selected;
		
		function start() {
			self.rotation = setInterval(function() {
				t = ++t < self.$tabs.length ? t : 0;
				self.select(t);
			}, ms); 
		}
		
		function stop(e) {
			if (!e || e.clientX) { // only in case of a true click
				clearInterval(self.rotation);
			}
		}
		
		// start interval
		if (ms) {
			start();
			if (!continuing)
				this.$tabs.bind(this.options.event, stop);
			else
				this.$tabs.bind(this.options.event, function() {
					stop();
					t = self.options.selected;
					start();
				});
		}
		// stop interval
		else {
			stop();
			this.$tabs.unbind(this.options.event, stop);
		}
	}
});

})(jQuery);

/*
 * jQuery UI Autocomplete
 *
 * Copyright (c) 2007, 2008 Dylan Verheul, Dan G. Switzer, Anjesh Tuladhar, Jörn Zaefferer
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 * 
 * http://docs.jquery.com/UI/Autocomplete
 *
 * Depends:
 *	ui.core.js
 */
(function($) {

$.widget("ui.autocomplete", {
	
	init: function() {

		$.extend(this.options, {
			delay: this.options.url ? $.Autocompleter.defaults.delay : 10,
			max: !this.options.scroll ? 10 : 150,
			highlight: this.options.highlight || function(value) { return value; }, // if highlight is set to false, replace it with a do-nothing function
			formatMatch: this.options.formatMatch || this.options.formatItem // if the formatMatch option is not specified, then use formatItem for backwards compatibility
		});
		
		new $.Autocompleter(this.element[0], this.options);
		
	},
	
	result: function(handler) {
		return this.element.bind("result", handler);
	},
	search: function(handler) {
		return this.element.trigger("search", [handler]);
	},
	flushCache: function() {
		return this.element.trigger("flushCache");
	},
	setData: function(key, value){
		return this.element.trigger("setOptions", [{ key: value }]);
	},
	destroy: function() {
		return this.element.trigger("unautocomplete");
	}
	
});

$.Autocompleter = function(input, options) {

	var KEY = {
		UP: 38,
		DOWN: 40,
		DEL: 46,
		TAB: 9,
		RETURN: 13,
		ESC: 27,
		COMMA: 188,
		PAGEUP: 33,
		PAGEDOWN: 34,
		BACKSPACE: 8
	};

	// Create $ object for input element
	var $input = $(input).attr("autocomplete", "off").addClass(options.inputClass);
	if(options.result) $input.bind('result.autocomplete', options.result);

	var timeout;
	var previousValue = "";
	var cache = $.Autocompleter.Cache(options);
	var hasFocus = 0;
	var lastKeyPressCode;
	var config = {
		mouseDownOnSelect: false
	};
	var select = $.Autocompleter.Select(options, input, selectCurrent, config);
	
	var blockSubmit;
	
	// prevent form submit in opera when selecting with return key
	$.browser.opera && $(input.form).bind("submit.autocomplete", function() {
		if (blockSubmit) {
			blockSubmit = false;
			return false;
		}
	});
	
	// only opera doesn't trigger keydown multiple times while pressed, others don't work with keypress at all
	$input.bind(($.browser.opera ? "keypress" : "keydown") + ".autocomplete", function(event) {
		// track last key pressed
		lastKeyPressCode = event.keyCode;
		switch(event.keyCode) {
		
			case KEY.UP:
				event.preventDefault();
				if ( select.visible() ) {
					select.prev();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.DOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.next();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEUP:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageUp();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEDOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageDown();
				} else {
					onChange(0, true);
				}
				break;
			
			// matches also semicolon
			case options.multiple && $.trim(options.multipleSeparator) == "," && KEY.COMMA:
			case KEY.TAB:
			case KEY.RETURN:
				if( selectCurrent() ) {
					// stop default to prevent a form submit, Opera needs special handling
					event.preventDefault();
					blockSubmit = true;
					return false;
				}
				break;
				
			case KEY.ESC:
				select.hide();
				break;
				
			default:
				clearTimeout(timeout);
				timeout = setTimeout(onChange, options.delay);
				break;
		}
	}).focus(function(){
		// track whether the field has focus, we shouldn't process any
		// results if the field no longer has focus
		hasFocus++;
	}).blur(function() {
		hasFocus = 0;
		if (!config.mouseDownOnSelect) {
			hideResults();
		}
	}).click(function() {
		// show select when clicking in a focused field
		if ( hasFocus++ > 1 && !select.visible() ) {
			onChange(0, true);
		}
	}).bind("search", function() {
		// TODO why not just specifying both arguments?
		var fn = (arguments.length > 1) ? arguments[1] : null;
		function findValueCallback(q, data) {
			var result;
			if( data && data.length ) {
				for (var i=0; i < data.length; i++) {
					if( data[i].result.toLowerCase() == q.toLowerCase() ) {
						result = data[i];
						break;
					}
				}
			}
			if( typeof fn == "function" ) fn(result);
			else $input.trigger("result", result && [result.data, result.value]);
		}
		$.each(trimWords($input.val()), function(i, value) {
			request(value, findValueCallback, findValueCallback);
		});
	}).bind("flushCache", function() {
		cache.flush();
	}).bind("setOptions", function() {
		$.extend(options, arguments[1]);
		// if we've updated the data, repopulate
		if ( "data" in arguments[1] )
			cache.populate();
	}).bind("unautocomplete", function() {
		select.unbind();
		$input.unbind();
		$(input.form).unbind(".autocomplete");
	});
	
	
	function selectCurrent() {
		var selected = select.selected();
		if( !selected )
			return false;
		
		var v = selected.result;
		previousValue = v;
		
		if ( options.multiple ) {
			var words = trimWords($input.val());
			if ( words.length > 1 ) {
				v = words.slice(0, words.length - 1).join( options.multipleSeparator ) + options.multipleSeparator + v;
			}
			v += options.multipleSeparator;
		}
		
		$input.val(v);
		hideResultsNow();
		$input.trigger("result", [selected.data, selected.value]);
		return true;
	}
	
	function onChange(crap, skipPrevCheck) {
		if( lastKeyPressCode == KEY.DEL ) {
			select.hide();
			return;
		}
		
		var currentValue = $input.val();
		
		if ( !skipPrevCheck && currentValue == previousValue )
			return;
		
		previousValue = currentValue;
		
		currentValue = lastWord(currentValue);
		if ( currentValue.length >= options.minChars) {
			$input.addClass(options.loadingClass);
			if (!options.matchCase)
				currentValue = currentValue.toLowerCase();
			request(currentValue, receiveData, hideResultsNow);
		} else {
			stopLoading();
			select.hide();
		}
	};
	
	function trimWords(value) {
		if ( !value ) {
			return [""];
		}
		var words = value.split( options.multipleSeparator );
		var result = [];
		$.each(words, function(i, value) {
			if ( $.trim(value) )
				result[i] = $.trim(value);
		});
		return result;
	}
	
	function lastWord(value) {
		if ( !options.multiple )
			return value;
		var words = trimWords(value);
		return words[words.length - 1];
	}
	
	// fills in the input box w/the first match (assumed to be the best match)
	// q: the term entered
	// sValue: the first matching result
	function autoFill(q, sValue){
		// autofill in the complete box w/the first match as long as the user hasn't entered in more data
		// if the last user key pressed was backspace, don't autofill
		if( options.autoFill && (lastWord($input.val()).toLowerCase() == q.toLowerCase()) && lastKeyPressCode != KEY.BACKSPACE ) {
			// fill in the value (keep the case the user has typed)
			$input.val($input.val() + sValue.substring(lastWord(previousValue).length));
			// select the portion of the value not typed by the user (so the next character will erase)
			$.Autocompleter.Selection(input, previousValue.length, previousValue.length + sValue.length);
		}
	};

	function hideResults() {
		clearTimeout(timeout);
		timeout = setTimeout(hideResultsNow, 200);
	};

	function hideResultsNow() {
		var wasVisible = select.visible();
		select.hide();
		clearTimeout(timeout);
		stopLoading();
		if (options.mustMatch) {
			// call search and run callback
			$input.autocomplete("search", function (result){
					// if no value found, clear the input box
					if( !result ) {
						if (options.multiple) {
							var words = trimWords($input.val()).slice(0, -1);
							$input.val( words.join(options.multipleSeparator) + (words.length ? options.multipleSeparator : "") );
						}
						else
							$input.val( "" );
					}
				}
			);
		}
		if (wasVisible)
			// position cursor at end of input field
			$.Autocompleter.Selection(input, input.value.length, input.value.length);
	};

	function receiveData(q, data) {
		if ( data && data.length && hasFocus ) {
			stopLoading();
			select.display(data, q);
			autoFill(q, data[0].value);
			select.show();
		} else {
			hideResultsNow();
		}
	};

	function request(term, success, failure) {
		if (!options.matchCase)
			term = term.toLowerCase();
		var data = cache.load(term);
		// recieve the cached data
		if (data && data.length) {
			success(term, data);
		// if an AJAX url has been supplied, try loading the data now
		
		} else if( (typeof options.url == "string") && (options.url.length > 0) ){
			
			var extraParams = {
				timestamp: +new Date()
			};
			$.each(options.extraParams, function(key, param) {
				extraParams[key] = typeof param == "function" ? param() : param;
			});
			
			$.ajax({
				// try to leverage ajaxQueue plugin to abort previous requests
				mode: "abort",
				// limit abortion to this input
				port: "autocomplete" + input.name,
				dataType: options.dataType,
				url: options.url,
				data: $.extend({
					q: lastWord(term),
					limit: options.max
				}, extraParams),
				success: function(data) {
					var parsed = options.parse && options.parse(data) || parse(data);
					cache.add(term, parsed);
					success(term, parsed);
				}
			});
		}

		else if (options.source && typeof options.source == 'function') {
			var resultData = options.source(term);
			var parsed = (options.parse) ? options.parse(resultData) : resultData;

			cache.add(term, parsed);
			success(term, parsed);
		} else {
			// if we have a failure, we need to empty the list -- this prevents the the [TAB] key from selecting the last successful match
			select.emptyList();
			failure(term);
		}
	};
	
	function parse(data) {
		var parsed = [];
		var rows = data.split("\n");
		for (var i=0; i < rows.length; i++) {
			var row = $.trim(rows[i]);
			if (row) {
				row = row.split("|");
				parsed[parsed.length] = {
					data: row,
					value: row[0],
					result: options.formatResult && options.formatResult(row, row[0]) || row[0]
				};
			}
		}
		return parsed;
	};

	function stopLoading() {
		$input.removeClass(options.loadingClass);
	};

};

$.Autocompleter.defaults = {
	inputClass: "ui-autocomplete-input",
	resultsClass: "ui-autocomplete-results",
	loadingClass: "ui-autocomplete-loading",
	minChars: 1,
	delay: 400,
	matchCase: false,
	matchSubset: true,
	matchContains: false,
	cacheLength: 10,
	max: 100,
	mustMatch: false,
	extraParams: {},
	selectFirst: true,
	formatItem: function(row) { return row[0]; },
	formatMatch: null,
	autoFill: false,
	width: 0,
	multiple: false,
	multipleSeparator: ", ",
	highlight: function(value, term) {
		return value.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
	},
    scroll: true,
    scrollHeight: 180
};

$.extend($.ui.autocomplete, {
	defaults: $.Autocompleter.defaults
});

$.Autocompleter.Cache = function(options) {

	var data = {};
	var length = 0;
	
	function matchSubset(s, sub) {
		if (!options.matchCase) 
			s = s.toLowerCase();
		var i = s.indexOf(sub);
		if (i == -1) return false;
		return i == 0 || options.matchContains;
	};
	
	function add(q, value) {
		if (length > options.cacheLength){
			flush();
		}
		if (!data[q]){ 
			length++;
		}
		data[q] = value;
	}
	
	function populate(){
		if( !options.data ) return false;
		// track the matches
		var stMatchSets = {},
			nullData = 0;

		// no url was specified, we need to adjust the cache length to make sure it fits the local data store
		if( !options.url ) options.cacheLength = 1;
		
		// track all options for minChars = 0
		stMatchSets[""] = [];
		
		// loop through the array and create a lookup structure
		for ( var i = 0, ol = options.data.length; i < ol; i++ ) {
			var rawValue = options.data[i];
			// if rawValue is a string, make an array otherwise just reference the array
			rawValue = (typeof rawValue == "string") ? [rawValue] : rawValue;
			
			var value = options.formatMatch(rawValue, i+1, options.data.length);
			if ( value === false )
				continue;
				
			var firstChar = value.charAt(0).toLowerCase();
			// if no lookup array for this character exists, look it up now
			if( !stMatchSets[firstChar] ) 
				stMatchSets[firstChar] = [];

			// if the match is a string
			var row = {
				value: value,
				data: rawValue,
				result: options.formatResult && options.formatResult(rawValue) || value
			};
			
			// push the current match into the set list
			stMatchSets[firstChar].push(row);

			// keep track of minChars zero items
			if ( nullData++ < options.max ) {
				stMatchSets[""].push(row);
			}
		};

		// add the data items to the cache
		$.each(stMatchSets, function(i, value) {
			// increase the cache size
			options.cacheLength++;
			// add to the cache
			add(i, value);
		});
	}
	
	// populate any existing data
	setTimeout(populate, 25);
	
	function flush(){
		data = {};
		length = 0;
	}
	
	return {
		flush: flush,
		add: add,
		populate: populate,
		load: function(q) {
			if (!options.cacheLength || !length)
				return null;
			/* 
			 * if dealing w/local data and matchContains than we must make sure
			 * to loop through all the data collections looking for matches
			 */
			if( !options.url && options.matchContains ){
				// track all matches
				var csub = [];
				// loop through all the data grids for matches
				for( var k in data ){
					// don't search through the stMatchSets[""] (minChars: 0) cache
					// this prevents duplicates
					if( k.length > 0 ){
						var c = data[k];
						$.each(c, function(i, x) {
							// if we've got a match, add it to the array
							if (matchSubset(x.value, q)) {
								csub.push(x);
							}
						});
					}
				}				
				return csub;
			} else 
			// if the exact item exists, use it
			if (data[q]){
				return data[q];
			} else
			if (options.matchSubset) {
				for (var i = q.length - 1; i >= options.minChars; i--) {
					var c = data[q.substr(0, i)];
					if (c) {
						var csub = [];
						$.each(c, function(i, x) {
							if (matchSubset(x.value, q)) {
								csub[csub.length] = x;
							}
						});
						return csub;
					}
				}
			}
			return null;
		}
	};
};

$.Autocompleter.Select = function (options, input, select, config) {
	var CLASSES = {
		ACTIVE: "ui-autocomplete-over"
	};
	
	var listItems,
		active = -1,
		data,
		term = "",
		needsInit = true,
		element,
		list;
	
	// Create results
	function init() {
		if (!needsInit)
			return;
		element = $("<div/>")
		.hide()
		.addClass(options.resultsClass)
		.css("position", "absolute")
		.appendTo(document.body);
	
		list = $("<ul/>").appendTo(element).mouseover( function(event) {
			if(target(event).nodeName && target(event).nodeName.toUpperCase() == 'LI') {
	            active = $("li", list).removeClass(CLASSES.ACTIVE).index(target(event));
			    $(target(event)).addClass(CLASSES.ACTIVE);            
	        }
		}).click(function(event) {
			$(target(event)).addClass(CLASSES.ACTIVE);
			select();
			// TODO provide option to avoid setting focus again after selection? useful for cleanup-on-focus
			input.focus();
			return false;
		}).mousedown(function() {
			config.mouseDownOnSelect = true;
		}).mouseup(function() {
			config.mouseDownOnSelect = false;
		});
		
		if( options.width > 0 )
			element.css("width", options.width);
			
		needsInit = false;
	} 
	
	function target(event) {
		var element = event.target;
		while(element && element.tagName != "LI")
			element = element.parentNode;
		// more fun with IE, sometimes event.target is empty, just ignore it then
		if(!element)
			return [];
		return element;
	}

	function moveSelect(step) {
		listItems.slice(active, active + 1).removeClass(CLASSES.ACTIVE);
		movePosition(step);
        var activeItem = listItems.slice(active, active + 1).addClass(CLASSES.ACTIVE);
        if(options.scroll) {
            var offset = 0;
            listItems.slice(0, active).each(function() {
				offset += this.offsetHeight;
			});
            if((offset + activeItem[0].offsetHeight - list.scrollTop()) > list[0].clientHeight) {
                list.scrollTop(offset + activeItem[0].offsetHeight - list.innerHeight());
            } else if(offset < list.scrollTop()) {
                list.scrollTop(offset);
            }
        }
	};
	
	function movePosition(step) {
		active += step;
		if (active < 0) {
			active = listItems.size() - 1;
		} else if (active >= listItems.size()) {
			active = 0;
		}
	}
	
	function limitNumberOfItems(available) {
		return options.max && options.max < available
			? options.max
			: available;
	}
	
	function fillList() {
		list.empty();
		var max = limitNumberOfItems(data.length);
		for (var i=0; i < max; i++) {
			if (!data[i])
				continue;
			var formatted = options.formatItem(data[i].data, i+1, max, data[i].value, term);
			if ( formatted === false )
				continue;
			var li = $("<li/>").html( options.highlight(formatted, term) ).addClass(i%2 == 0 ? "ui-autocomplete-even" : "ui-autocomplete-odd").appendTo(list)[0];
			$.data(li, "ui-autocomplete-data", data[i]);
		}
		listItems = list.find("li");
		if ( options.selectFirst ) {
			listItems.slice(0, 1).addClass(CLASSES.ACTIVE);
			active = 0;
		}
		// apply bgiframe if available
		if ( $.fn.bgiframe )
			list.bgiframe();
	}
	
	return {
		display: function(d, q) {
			init();
			data = d;
			term = q;
			fillList();
		},
		next: function() {
			moveSelect(1);
		},
		prev: function() {
			moveSelect(-1);
		},
		pageUp: function() {
			if (active != 0 && active - 8 < 0) {
				moveSelect( -active );
			} else {
				moveSelect(-8);
			}
		},
		pageDown: function() {
			if (active != listItems.size() - 1 && active + 8 > listItems.size()) {
				moveSelect( listItems.size() - 1 - active );
			} else {
				moveSelect(8);
			}
		},
		hide: function() {
			element && element.hide();
			listItems && listItems.removeClass(CLASSES.ACTIVE)
			active = -1;
			$(input).triggerHandler("autocompletehide", [{}, { options: options }], options["hide"]);
		},
		visible : function() {
			return element && element.is(":visible");
		},
		current: function() {
			return this.visible() && (listItems.filter("." + CLASSES.ACTIVE)[0] || options.selectFirst && listItems[0]);
		},
		show: function() {
			var offset = $(input).offset();
			element.css({
				width: typeof options.width == "string" || options.width > 0 ? options.width : $(input).width(),
				top: offset.top + input.offsetHeight,
				left: offset.left
			}).show();
			
            if(options.scroll) {
                list.scrollTop(0);
                list.css({
					maxHeight: options.scrollHeight,
					overflow: 'auto'
				});
				
                if($.browser.msie && typeof document.body.style.maxHeight === "undefined") {
					var listHeight = 0;
					listItems.each(function() {
						listHeight += this.offsetHeight;
					});
					var scrollbarsVisible = listHeight > options.scrollHeight;
                    list.css('height', scrollbarsVisible ? options.scrollHeight : listHeight );
					if (!scrollbarsVisible) {
						// IE doesn't recalculate width when scrollbar disappears
						listItems.width( list.width() - parseInt(listItems.css("padding-left")) - parseInt(listItems.css("padding-right")) );
					}
                }
                
            }
            
            $(input).triggerHandler("autocompleteshow", [{}, { options: options }], options["show"]);
            
		},
		selected: function() {
			var selected = listItems && listItems.filter("." + CLASSES.ACTIVE).removeClass(CLASSES.ACTIVE);
			return selected && selected.length && $.data(selected[0], "ui-autocomplete-data");
		},
		emptyList: function (){
			list && list.empty();
		},
		unbind: function() {
			element && element.remove();
		}
	};
};

$.Autocompleter.Selection = function(field, start, end) {
	if( field.createTextRange ){
		var selRange = field.createTextRange();
		selRange.collapse(true);
		selRange.moveStart("character", start);
		selRange.moveEnd("character", end);
		selRange.select();
	} else if( field.setSelectionRange ){
		field.setSelectionRange(start, end);
	} else {
		if( field.selectionStart ){
			field.selectionStart = start;
			field.selectionEnd = end;
		}
	}
	field.focus();
};

})(jQuery);

;(function($) {

	$.fn.extend({
		colorpicker: function(options) {
			var args = Array.prototype.slice.call(arguments, 1);
			return this.each(function() {
				if (typeof options == "string") {
					var inst = $.data(this, "colorpicker");
					if(inst) inst[options].apply(inst, args);
				} else if(!$.data(this, "colorpicker"))
					new $.ui.colorpicker(this, options);
			});
		}
	});
	
	$.ui.colorpicker = function(element, options) {

		//Initialize needed constants
		var self = this;
		this.element = $(element);
		var o = this.options = $.extend({}, options);
		
		$.data(element, "colorpicker", this);
		this.element.addClass("ui-colorpicker")
			.append('<div class="ui-colorpicker-color">')
				.find('div.ui-colorpicker-color').append('<div class="ui-colorpicker-color-handle">').end()
			.append('<div class="ui-colorpicker-hue">')
				.find('div.ui-colorpicker-hue').append('<div class="ui-colorpicker-hue-handle">').end()
			.append('<div class="ui-colorpicker-current">')
				.find('div.ui-colorpicker-current').append('<div class="ui-colorpicker-last">').end()
			.append('<div class="ui-colorpicker-values">')
				.find('div.ui-colorpicker-values').append('<table cellpadding="0" cellspacing="2">')
					.find('table')
					.append('<tr><td>R:</td><td><input type="text" size="2" class="ui-colorpicker-rgbR" value="255" /></td></tr>')
					.append('<tr><td>G:</td><td><input type="text" size="2" class="ui-colorpicker-rgbG" value="255" /></td></tr>')
					.append('<tr><td>B:</td><td><input type="text" size="2" class="ui-colorpicker-rgbB" value="255" /></td></tr>')
					.append('<tr><td>#</td><td><input type="text" size="5" class="ui-colorpicker-hex" value="FFFFFF" /></td></tr>')
					.end()
				.end()
		;
		
		$(element).bind("setData.colorpicker", function(event, key, value){
			self.options[key] = value;
		}).bind("getData.colorpicker", function(event, key){
			return self.options[key];
		});

		this.baseColor = {r:255,g:0,b:0};
		this.currentColor = {r:255,g:255,b:255};
		this.lastValues = [0,0];
		this.colorfieldCurrent = $('div.ui-colorpicker-current', this.element);
		this.colorfieldLast = $('div.ui-colorpicker-last', this.element);
		
		$('div.ui-colorpicker-color', this.element).slider({
			handle: "div",
			axis: "both",
			distance: 0,
			slide : function(e, ui) {
				self.lastValues = [parseInt(ui.value.x * 255/100),parseInt(ui.value.y * 255/100)];
				self.setGradientColor();
				self.propagate("picking", e);
			},
			change : function(e) {
				self.colorfieldLast.css("backgroundColor", 'rgb(' + self.currentColor.r + ',' + self.currentColor.g + ',' + self.currentColor.b + ')');
				self.propagate("change", e);
			},
			stop: function(e) { self.propagate("pick", e); },
			start: function(e) { self.propagate("start", e); }
		});

		$('div.ui-colorpicker-hue', this.element).slider({
			handle: "div",
			distance: 0,
			slide : function(e, ui) {
				self.setVertColor(parseInt(ui.value * 255 / 100));
				self.setGradientColor();
				self.propagate("picking", e);
			},
			change : function(e) {
				self.colorfieldLast.css("backgroundColor", 'rgb(' + self.currentColor.r + ',' + self.currentColor.g + ',' + self.currentColor.b + ')');
				self.propagate("change", e);
			},
			stop: function(e) { self.propagate("pick", e); },
			start: function(e) { self.propagate("start", e); }
		});		
		
		
	};
	
	$.extend($.ui.colorpicker.prototype, {
		plugins: {},
		ui: function(e) {
			return {
				instance: this,
				options: this.options,
				element: this.element,
				rgb: this.currentColor,
				hex: (this.toHex(this.currentColor.r) + this.toHex(this.currentColor.g) + this.toHex(this.currentColor.b)).toUpperCase()
			};
		},
		propagate: function(n,e) {
			$.ui.plugin.call(this, n, [e, this.ui()]);
			return this.element.triggerHandler(n == "pick" ? n : "pick"+n, [e, this.ui()], this.options[n]);
		},
		destroy: function() {
			if(!$.data(this.element[0], 'colorpicker')) return;
			this.element
				.removeClass("ui-colorpicker ui-colorpicker-disabled")
				.removeData("colorpicker")
				.unbind(".colorpicker");
		},
		enable: function() {
			this.element.removeClass("ui-colorpicker-disabled");
			this.options.disabled = false;
		},
		disable: function() {
			this.element.addClass("ui-colorpicker-disabled");
			this.options.disabled = true;
		},
		setVertColor: function(indic){
			var n=256/6, j=256/n, C=indic, c=C%n;
			this.baseColor = {
				r : parseInt(C<n?255:C<n*2?255-c*j:C<n*4?0:C<n*5?c*j:255),
				g : parseInt(C<n*2?0:C<n*3?c*j:C<n*5?255:255-c*j),
				b : parseInt(C<n?c*j:C<n*3?255:C<n*4?255-c*j:0)
			};
			
			$("div.ui-colorpicker-color", this.element).css('backgroundColor', 'rgb(' + this.baseColor.r + ',' + this.baseColor.g + ',' + this.baseColor.b + ')');
		},
		setGradientColor: function(){
			var r = Math.round((1-(1-(this.baseColor.r/255))*(this.lastValues[0]/255))*(255-this.lastValues[1]));
			var g = Math.round((1-(1-(this.baseColor.g/255))*(this.lastValues[0]/255))*(255-this.lastValues[1]));
			var b = Math.round((1-(1-(this.baseColor.b/255))*(this.lastValues[0]/255))*(255-this.lastValues[1]));
			this.colorfieldCurrent.css('backgroundColor','rgb(' + r + ',' + g + ',' + b + ')');
			$('input.ui-colorpicker-rgbR', this.element)[0].value = r;
			$('input.ui-colorpicker-rgbG', this.element)[0].value = g;
			$('input.ui-colorpicker-rgbB', this.element)[0].value = b;
			$('input.ui-colorpicker-hex', this.element)[0].value = (this.toHex(r) + this.toHex(g) + this.toHex(b)).toUpperCase();
			this.currentColor = {r:r,g:g,b:b};
		},
		toHex: function(color){
			color=parseInt(color).toString(16);
			return color.length<2?"0"+color:color;
		}
	});
	
})(jQuery);
/*
 * Treeview pre-1.4.1 - jQuery plugin to hide and show branches of a tree
 * 
 * http://bassistance.de/jquery-plugins/jquery-plugin-treeview/
 * http://docs.jquery.com/Plugins/Treeview
 *
 * Copyright (c) 2007 Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id: jquery.treeview.js 5759 2008-07-01 07:50:28Z joern.zaefferer $
 *
 */

;(function($) {

	$.extend($.fn, {
		swapClass: function(c1, c2) {
			var c1Elements = this.filter('.' + c1);
			this.filter('.' + c2).removeClass(c2).addClass(c1);
			c1Elements.removeClass(c1).addClass(c2);
			return this;
		},
		replaceClass: function(c1, c2) {
			return this.filter('.' + c1).removeClass(c1).addClass(c2).end();
		},
		hoverClass: function(className) {
			className = className || "hover";
			return this.hover(function() {
				$(this).addClass(className);
			}, function() {
				$(this).removeClass(className);
			});
		},
		heightToggle: function(animated, callback) {
			animated ?
				this.animate({ height: "toggle" }, animated, callback) :
				this.each(function(){
					jQuery(this)[ jQuery(this).is(":hidden") ? "show" : "hide" ]();
					if(callback)
						callback.apply(this, arguments);
				});
		},
		heightHide: function(animated, callback) {
			if (animated) {
				this.animate({ height: "hide" }, animated, callback);
			} else {
				this.hide();
				if (callback)
					this.each(callback);				
			}
		},
		prepareBranches: function(settings) {
			if (!settings.prerendered) {
				// mark last tree items
				this.filter(":last-child:not(ul)").addClass(CLASSES.last);
				// collapse whole tree, or only those marked as closed, anyway except those marked as open
				this.filter((settings.collapsed ? "" : "." + CLASSES.closed) + ":not(." + CLASSES.open + ")").find(">ul").hide();
			}
			// return all items with sublists
			return this.filter(":has(>ul)");
		},
		applyClasses: function(settings, toggler) {
			this.filter(":has(>ul):not(:has(>a))").find(">span").unbind("click.treeview").bind("click.treeview", function(event) {
				// don't handle click events on children, eg. checkboxes
				if ( this == event.target )
					toggler.apply($(this).next());
			}).add( $("a", this) ).hoverClass();
			
			if (!settings.prerendered) {
				// handle closed ones first
				this.filter(":has(>ul:hidden)")
						.addClass(CLASSES.expandable)
						.replaceClass(CLASSES.last, CLASSES.lastExpandable);
						
				// handle open ones
				this.not(":has(>ul:hidden)")
						.addClass(CLASSES.collapsable)
						.replaceClass(CLASSES.last, CLASSES.lastCollapsable);
						
	            // create hitarea if not present
				var hitarea = this.find("div." + CLASSES.hitarea);
				if (!hitarea.length)
					hitarea = this.prepend("<div class=\"" + CLASSES.hitarea + "\"/>").find("div." + CLASSES.hitarea);
				hitarea.removeClass().addClass(CLASSES.hitarea).each(function() {
					var classes = "";
					$.each($(this).parent().attr("class").split(" "), function() {
						classes += this + "-hitarea ";
					});
					$(this).addClass( classes );
				})
			}
			
			// apply event to hitarea
			this.find("div." + CLASSES.hitarea).click( toggler );
		},
		treeview: function(settings) {
			
			settings = $.extend({
				cookieId: "treeview"
			}, settings);
			
			if ( settings.toggle ) {
				var callback = settings.toggle;
				settings.toggle = function() {
					return callback.apply($(this).parent()[0], arguments);
				};
			}
		
			// factory for treecontroller
			function treeController(tree, control) {
				// factory for click handlers
				function handler(filter) {
					return function() {
						// reuse toggle event handler, applying the elements to toggle
						// start searching for all hitareas
						toggler.apply( $("div." + CLASSES.hitarea, tree).filter(function() {
							// for plain toggle, no filter is provided, otherwise we need to check the parent element
							return filter ? $(this).parent("." + filter).length : true;
						}) );
						return false;
					};
				}
				// click on first element to collapse tree
				$("a:eq(0)", control).click( handler(CLASSES.collapsable) );
				// click on second to expand tree
				$("a:eq(1)", control).click( handler(CLASSES.expandable) );
				// click on third to toggle tree
				$("a:eq(2)", control).click( handler() ); 
			}
		
			// handle toggle event
			function toggler() {
				$(this)
					.parent()
					// swap classes for hitarea
					.find(">.hitarea")
						.swapClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
						.swapClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
					.end()
					// swap classes for parent li
					.swapClass( CLASSES.collapsable, CLASSES.expandable )
					.swapClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
					// find child lists
					.find( ">ul" )
					// toggle them
					.heightToggle( settings.animated, settings.toggle );
				if ( settings.unique ) {
					$(this).parent()
						.siblings()
						// swap classes for hitarea
						.find(">.hitarea")
							.replaceClass( CLASSES.collapsableHitarea, CLASSES.expandableHitarea )
							.replaceClass( CLASSES.lastCollapsableHitarea, CLASSES.lastExpandableHitarea )
						.end()
						.replaceClass( CLASSES.collapsable, CLASSES.expandable )
						.replaceClass( CLASSES.lastCollapsable, CLASSES.lastExpandable )
						.find( ">ul" )
						.heightHide( settings.animated, settings.toggle );
				}
			}
			this.data("toggler", toggler);
			
			function serialize() {
				function binary(arg) {
					return arg ? 1 : 0;
				}
				var data = [];
				branches.each(function(i, e) {
					data[i] = $(e).is(":has(>ul:visible)") ? 1 : 0;
				});
				$.cookie(settings.cookieId, data.join(""), settings.cookieOptions );
			}
			
			function deserialize() {
				var stored = $.cookie(settings.cookieId);
				if ( stored ) {
					var data = stored.split("");
					branches.each(function(i, e) {
						$(e).find(">ul")[ parseInt(data[i]) ? "show" : "hide" ]();
					});
				}
			}
			
			// add treeview class to activate styles
			this.addClass("treeview");
			
			// prepare branches and find all tree items with child lists
			var branches = this.find("li").prepareBranches(settings);
			
			switch(settings.persist) {
			case "cookie":
				var toggleCallback = settings.toggle;
				settings.toggle = function() {
					serialize();
					if (toggleCallback) {
						toggleCallback.apply(this, arguments);
					}
				};
				deserialize();
				break;
			case "location":
				var current = this.find("a").filter(function() { return this.href.toLowerCase() == location.href.toLowerCase(); });
				if ( current.length ) {
					current.addClass("selected").parents("ul, li").add( current.next() ).show();
				}
				break;
			}
			
			branches.applyClasses(settings, toggler);
				
			// if control option is set, create the treecontroller and show it
			if ( settings.control ) {
				treeController(this, settings.control);
				$(settings.control).show();
			}
			
			return this;
		}
	});
	
	// classes used by the plugin
	// need to be styled via external stylesheet, see first example
	$.treeview = {};
	var CLASSES = ($.treeview.classes = {
		open: "open",
		closed: "closed",
		expandable: "expandable",
		expandableHitarea: "expandable-hitarea",
		lastExpandableHitarea: "lastExpandable-hitarea",
		collapsable: "collapsable",
		collapsableHitarea: "collapsable-hitarea",
		lastCollapsableHitarea: "lastCollapsable-hitarea",
		lastCollapsable: "lastCollapsable",
		lastExpandable: "lastExpandable",
		last: "last",
		hitarea: "hitarea"
	});
	
	// provide backwards compability
	$.fn.Treeview = $.fn.treeview;
	
})(jQuery);
(function($) {

$.widget("ui.tree", {
	init: function() {

		var self = this;
		this.identifier = (new Date()).getTime()+Math.random();

		this.element.sortable({
			items: this.options.sortOn,
			scope: this.identifier,
			distance: this.options.distance,
			placeholder: "ui-tree-placeholder",
			start: function(e, ui) {
				var inst = $(this).data("sortable");
				inst.placeholder.hide();
				inst.helperProportions.height = inst.currentItem.find(self.options.dropOn).length ? inst.currentItem.find(self.options.dropOn).outerHeight() : inst.currentItem.outerHeight();
				inst._preserveHelperProportions = true;
				inst.refreshPositions(true);
				
				self.originalParent = ui.item.parent();
				
				(self.options.start && self.options.start.apply(this, [e, ui]));
			},
			stop: function(e, ui) {
				var sortable = $(this).data("sortable");
				$(sortable.options.items, sortable.element).removeClass(sortable.options.sortIndicatorDown).removeClass(sortable.options.sortIndicatorUp);
				
				if ( self.originalParent.is(':empty') )
					self.originalParent.remove();
				
				(self.options.stop && self.options.stop.apply(this, [e, ui]));
			},
			sortIndicator: function(e, item, append, hardRefresh) {
	
				append ? append[0].appendChild(this.placeholder[0]) : item.item[0].parentNode.insertBefore(this.placeholder[0], (this.direction == 'down' ? item.item[0] : item.item[0].nextSibling));

				$(this.options.items, this.element).removeClass(self.options.sortIndicatorDown).removeClass(self.options.sortIndicatorUp);
				item.item.addClass(this.direction == "down" ? self.options.sortIndicatorDown : self.options.sortIndicatorUp);
	
			}
		});
		
		//Make certain nodes droppable
		$(this.options.dropOn, this.element).droppable({
			accept: this.options.sortOn,
			hoverClass: this.options.dropHoverClass,
			tolerance: "pointer",
			scope: this.identifier,
			over: function(e, ui) {
				$(self.options.sortOn, self.element).removeClass(self.options.sortIndicatorDown).removeClass(self.options.sortIndicatorUp);
				self.overDroppable = true;
				self.trigger('over', e, ui);
			},
			out: function(e, ui) {
				self.overDroppable = false;
				(self.options.out && self.options.out.apply(this, [e, ui]));
			},
			drop: function(e, ui) {
				
				var ul = $(this).parent().find("ul");
				if(!ul.length) var ul = $("<ul></ul>").appendTo($(this).parent());
				
				ui.draggable.appendTo( $(this).parent().find("> ul") );
				
				self.element.data("sortable")._noFinalSort = true;
				
				(self.options.drop && self.options.drop.apply(this, [e, ui]));
			}
		});

	}
});

$.extend($.ui.tree, {
	defaults: {
		sortOn: "*",
		dropOn: "div",
		dropHoverClass: "ui-tree-hover",
		sortIndicatorDown: "hover-down",
		sortIndicatorUp: "hover-up"
	}
});

})(jQuery);
;(function( $ ){

	var $scrollTo = $.scrollTo = function( target, duration, settings ){
		$scrollTo.window().scrollTo( target, duration, settings );
	};

	$scrollTo.defaults = {
		axis:'y',
		duration:1
	};

	//returns the element that needs to be animated to scroll the window
	$scrollTo.window = function(){
		return $( $.browser.safari ? 'body' : 'html' );
	};

	$.fn.scrollTo = function( target, duration, settings ){
		if( typeof duration == 'object' ){
			settings = duration;
			duration = 0;
		}
		settings = $.extend( {}, $scrollTo.defaults, settings );
		duration = duration || settings.speed || settings.duration;//speed is still recognized for backwards compatibility
		settings.queue = settings.queue && settings.axis.length > 1;//make sure the settings are given right
		if( settings.queue )
			duration /= 2;//let's keep the overall speed, the same.
		settings.offset = both( settings.offset );
		settings.over = both( settings.over );

		return this.each(function(){
			var elem = this, $elem = $(elem),
				t = target, toff, attr = {},
				win = $elem.is('html,body');
			switch( typeof t ){
				case 'number'://will pass the regex
				case 'string':
					if( /^([+-]=)?\d+(px)?$/.test(t) ){
						t = both( t );
						break;//we are done
					}
					t = $(t,this);// relative selector, no break!
				case 'object':
					if( t.is || t.style )//DOM/jQuery
						toff = (t = $(t)).offset();//get the real position of the target 
			}
			$.each( settings.axis.split(''), function( i, axis ){
				var Pos	= axis == 'x' ? 'Left' : 'Top',
					pos = Pos.toLowerCase(),
					key = 'scroll' + Pos,
					act = elem[key],
					Dim = axis == 'x' ? 'Width' : 'Height',
					dim = Dim.toLowerCase();

				if( toff ){//jQuery/DOM
					attr[key] = toff[pos] + ( win ? 0 : act - $elem.offset()[pos] );

					if( settings.margin ){//if it's a dom element, reduce the margin
						attr[key] -= parseInt(t.css('margin'+Pos)) || 0;
						attr[key] -= parseInt(t.css('border'+Pos+'Width')) || 0;
					}
					
					attr[key] += settings.offset[pos] || 0;//add/deduct the offset
					
					if( settings.over[pos] )//scroll to a fraction of its width/height
						attr[key] += t[dim]() * settings.over[pos];
				}else
					attr[key] = t[pos];//remove the unnecesary 'px'

				if( /^\d+$/.test(attr[key]) )//number or 'number'
					attr[key] = attr[key] <= 0 ? 0 : Math.min( attr[key], max(Dim) );//check the limits

				if( !i && settings.queue ){//queueing each axis is required					
					if( act != attr[key] )//don't waste time animating, if there's no need.
						animate( settings.onAfterFirst );//intermediate animation
					delete attr[key];//don't animate this axis again in the next iteration.
				}
			});			
			animate( settings.onAfter );			

			function animate( callback ){
				$elem.animate( attr, duration, settings.easing, callback && function(){
					callback.call(this, target);
				});
			};
			function max( Dim ){
				var el = win ? $.browser.opera ? document.body : document.documentElement : elem;
				return el['scroll'+Dim] - el['client'+Dim];
			};
		});
	};

	function both( val ){
		return typeof val == 'object' ? val : { top:val, left:val };
	};

})( jQuery );
/*
 * jQuery Live Search Plugin
 * Version: 0.1
 * Date: 2008-08-04
 * Author: Eduardo Lundgren
 * Copyright: Copyright (c) 2008 Eduardo Lundgren under dual MIT/GPL license.
*/

; (function($) {

    $.fn.extend({
        liveSearch: function(options) {
            return this.each(function() {
                var opt = options = $.extend({},
                $.liveSearch.defaults, options || {});
                new $.liveSearch(this, opt);
            });
        }
    });


    $.liveSearch = function(element, options) {
        this.init(element, options);
    };

    $.extend($.liveSearch.prototype, {
        init: function(element, options) {
            var instance = this;

            this.options = options;
            this.timer = null;
            this.cache = null;
            this.element = jQuery(element);
            this.list = jQuery(this.options.list);
            this.delay = this.options.delay;
            this.filterList = jQuery(this.options.filter || this.list);

            if (this.filterList.length) {
                this.cache = this.filterList.map(this.options.data);

                this.element
                .keyup(function() {
                    var self = this;

                    if (instance.timer) {
                        clearTimeout(instance.timer);
                    }

                    instance.timer = setTimeout(function() {
                        instance.options.before.apply(instance);
                        instance.filter();
                        instance.options.after.apply(instance);
                    },
                    instance.delay);
                })
                .parents('form').submit(function() {
                    return false;
                });
            }

            return this;
        },

        filter: function() {
            var instance = this,
            results = [],
            rows = this.list;

            this.term = $.trim(this.element.val().toLowerCase()).match(/(\w|\s|[*])*/g).join("");

            if (!this.term) {
                rows.each(function() {
                    instance.options.show.apply(this);
                });
            } else {
                rows.each(function() {
                    instance.options.hide.apply(this);
                });

                this.cache.each(function(i, v) {
                    var regex = new RegExp(instance.term.replace("*", ""), "g");
                    if (regex.test(v) || instance.options.exclude.apply(rows[i])) {
                        results.push(i);
                    }
                });

                $.each(results,
                function() {
                    instance.options.show.apply(jQuery(rows[this]));
                });
            }
        }
    });

    $.extend($.liveSearch, {
        defaults: {
            delay: 250,
            show: function() {
                $(this).show();
            },
            hide: function() {
                $(this).hide();
            },
            data: function() {
                return $(this)[0].innerHTML.toLowerCase();
            },
            exclude: function() {
            	return false;
            },
            before: function() {},
            after: function() {}
        }
    });

})(jQuery);


/*
Class: Class
	The base class object of the <http://mootools.net> framework.
	
Arguments:
	properties - the collection of properties that apply to the class. Creates a new class, its initialize method will fire upon class instantiation.
	
Example:
	>var Cat = new Class({
	>	initialize: function(name){
	>		this.name = name;
	>	}
	>});
	>var myCat = new Cat('Micia');
	>alert myCat.name; //alerts 'Micia'
*/

var Class = function(properties){
	var klass = function(){
		for (var p in this){
			if (this[p]) this[p]._proto_ = this;
		}
		if (arguments[0] != 'noinit' && this.initialize) return this.initialize.apply(this, arguments);
	};
	klass.extend = this.extend;
	klass.implement = this.implement;
	klass.prototype = properties;
	return klass;
};

/*
Property: empty
	Returns an empty function
*/

Class.empty = function(){};

/*
Property: create
	same as new Class. see <Class>
*/

Class.create = function(properties){
	return new Class(properties);
};

Class.prototype = {

	/*
	Property: extend
		Returns the copy of the Class extended with the passed in properties.
		
	Arguments:
		properties - the properties to add to the base class in this new Class.
		
	Example:
		>var Animal = new Class({
		>	initialize: function(age){
		>		this.age = age;
		>	}
		>});
		>var Cat = Animal.extend({
		>	initialize: function(name, age){
		>		this.parent(age); //will call the previous initialize;
		>		this.name = name;
		>	}
		>});
		>var myCat = new Cat('Micia', 20);
		>alert myCat.name; //alerts 'Micia'
		>alert myCat.age; //alerts 20
	*/

	extend: function(properties){
		var pr0t0typ3 = new this('noinit');
		for (var property in properties){
			var previous = pr0t0typ3[property];
			var current = properties[property];
			if (previous && previous != current) current = previous.parentize(current) || current;
			pr0t0typ3[property] = current;
		}
		return new Class(pr0t0typ3);
	},
	
	/*	
	Property: implement
		Implements the passed in properties to the base Class prototypes, altering the base class, unlike <Class.extend>.

	Arguments:
		properties - the properties to add to the base class.
		
	Example:
		>var Animal = new Class({
		>	initialize: function(age){
		>		this.age = age;
		>	}
		>});
		>Animal.implement({
		>	setName: function(name){
		>		this.name = name
		>	}
		>});
		>var myAnimal = new Animal(20);
		>myAnimal.setName('Micia');
		>alert(myAnimal.name); //alerts 'Micia'
	*/
	
	implement: function(properties){
		for (var property in properties) this.prototype[property] = properties[property];
	}

};

/*
Function: Object.Native
	Will add a .extend method to the objects passed as a parameter, equivalent to <Class.implement>

Arguments:
	a number of classes/native javascript objects

*/

Object.Native = function(){
	for (var i = 0; i < arguments.length; i++) arguments[i].extend = Class.prototype.implement;
};

new Object.Native(Function, Array, String, Number);

Function.extend({

	parentize: function(current){
		var previous = this;
		return function(){
			this.parent = previous;
			return current.apply(this, arguments);
		};
	}

});
/**
 * SWFObject v1.5.1: Flash Player detection and embed - http://blog.deconcept.com/swfobject/
 *
 * SWFObject is (c) 2007 Geoff Stearns and is released under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 */
if(typeof deconcept == "undefined") var deconcept = {};
if(typeof deconcept.util == "undefined") deconcept.util = {};
if(typeof deconcept.SWFObjectUtil == "undefined") deconcept.SWFObjectUtil = {};
deconcept.SWFObject = function(swf, id, w, h, ver, c, quality, xiRedirectUrl, redirectUrl, detectKey) {
	if (!document.getElementById) { return; }
	this.DETECT_KEY = detectKey ? detectKey : 'detectflash';
	this.skipDetect = deconcept.util.getRequestParameter(this.DETECT_KEY);
	this.params = {};
	this.variables = {};
	this.attributes = [];
	if(swf) { this.setAttribute('swf', swf); }
	if(id) { this.setAttribute('id', id); }
	if(w) { this.setAttribute('width', w); }
	if(h) { this.setAttribute('height', h); }
	if(ver) { this.setAttribute('version', new deconcept.PlayerVersion(ver.toString().split("."))); }
	this.installedVer = deconcept.SWFObjectUtil.getPlayerVersion();
	if (!window.opera && document.all && this.installedVer.major > 7) {
		// only add the onunload cleanup if the Flash Player version supports External Interface and we are in IE
		// fixes bug in some fp9 versions see http://blog.deconcept.com/2006/07/28/swfobject-143-released/
		if (!deconcept.unloadSet) {
			deconcept.SWFObjectUtil.prepUnload = function() {
				__flash_unloadHandler = function(){};
				__flash_savedUnloadHandler = function(){};
				window.attachEvent("onunload", deconcept.SWFObjectUtil.cleanupSWFs);
			}
			window.attachEvent("onbeforeunload", deconcept.SWFObjectUtil.prepUnload);
			deconcept.unloadSet = true;
		}
	}
	if(c) { this.addParam('bgcolor', c); }
	var q = quality ? quality : 'high';
	this.addParam('quality', q);
	this.setAttribute('useExpressInstall', false);
	this.setAttribute('doExpressInstall', false);
	var xir = (xiRedirectUrl) ? xiRedirectUrl : window.location;
	this.setAttribute('xiRedirectUrl', xir);
	this.setAttribute('redirectUrl', '');
	if(redirectUrl) { this.setAttribute('redirectUrl', redirectUrl); }
}
deconcept.SWFObject.prototype = {
	useExpressInstall: function(path) {
		this.xiSWFPath = !path ? "expressinstall.swf" : path;
		this.setAttribute('useExpressInstall', true);
	},
	setAttribute: function(name, value){
		this.attributes[name] = value;
	},
	getAttribute: function(name){
		return this.attributes[name] || "";
	},
	addParam: function(name, value){
		this.params[name] = value;
	},
	getParams: function(){
		return this.params;
	},
	addVariable: function(name, value){
		this.variables[name] = value;
	},
	getVariable: function(name){
		return this.variables[name] || "";
	},
	getVariables: function(){
		return this.variables;
	},
	getVariablePairs: function(){
		var variablePairs = [];
		var key;
		var variables = this.getVariables();
		for(key in variables){
			variablePairs[variablePairs.length] = key +"="+ variables[key];
		}
		return variablePairs;
	},
	getSWFHTML: function() {
		var swfNode = "";
		if (navigator.plugins && navigator.mimeTypes && navigator.mimeTypes.length) { // netscape plugin architecture
			if (this.getAttribute("doExpressInstall")) {
				this.addVariable("MMplayerType", "PlugIn");
				this.setAttribute('swf', this.xiSWFPath);
			}
			swfNode = '<embed type="application/x-shockwave-flash" src="'+ this.getAttribute('swf') +'" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'" style="'+ (this.getAttribute('style') || "") +'"';
			swfNode += ' id="'+ this.getAttribute('id') +'" name="'+ this.getAttribute('id') +'" ';
			var params = this.getParams();
			 for(var key in params){ swfNode += [key] +'="'+ params[key] +'" '; }
			var pairs = this.getVariablePairs().join("&");
			 if (pairs.length > 0){ swfNode += 'flashvars="'+ pairs +'"'; }
			swfNode += '/>';
		} else { // PC IE
			if (this.getAttribute("doExpressInstall")) {
				this.addVariable("MMplayerType", "ActiveX");
				this.setAttribute('swf', this.xiSWFPath);
			}
			swfNode = '<object id="'+ this.getAttribute('id') +'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="'+ this.getAttribute('width') +'" height="'+ this.getAttribute('height') +'" style="'+ (this.getAttribute('style') || "") +'">';
			swfNode += '<param name="movie" value="'+ this.getAttribute('swf') +'" />';
			var params = this.getParams();
			for(var key in params) {
			 swfNode += '<param name="'+ key +'" value="'+ params[key] +'" />';
			}
			var pairs = this.getVariablePairs().join("&");
			if(pairs.length > 0) {swfNode += '<param name="flashvars" value="'+ pairs +'" />';}
			swfNode += "</object>";
		}
		return swfNode;
	},
	write: function(elementId){
		if(this.getAttribute('useExpressInstall')) {
			// check to see if we need to do an express install
			var expressInstallReqVer = new deconcept.PlayerVersion([6,0,65]);
			if (this.installedVer.versionIsValid(expressInstallReqVer) && !this.installedVer.versionIsValid(this.getAttribute('version'))) {
				this.setAttribute('doExpressInstall', true);
				this.addVariable("MMredirectURL", escape(this.getAttribute('xiRedirectUrl')));
				document.title = document.title.slice(0, 47) + " - Flash Player Installation";
				this.addVariable("MMdoctitle", document.title);
			}
		}
		if(this.skipDetect || this.getAttribute('doExpressInstall') || this.installedVer.versionIsValid(this.getAttribute('version'))){
			var n = (typeof elementId == 'string') ? document.getElementById(elementId) : elementId;
			n.innerHTML = this.getSWFHTML();
			return true;
		}else{
			if(this.getAttribute('redirectUrl') != "") {
				document.location.replace(this.getAttribute('redirectUrl'));
			}
		}
		return false;
	}
}

/* ---- detection functions ---- */
deconcept.SWFObjectUtil.getPlayerVersion = function(){
	var PlayerVersion = new deconcept.PlayerVersion([0,0,0]);
	if(navigator.plugins && navigator.mimeTypes.length){
		var x = navigator.plugins["Shockwave Flash"];
		if(x && x.description) {
			PlayerVersion = new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/, "").replace(/(\s+r|\s+b[0-9]+)/, ".").split("."));
		}
	}else if (navigator.userAgent && navigator.userAgent.indexOf("Windows CE") >= 0){ // if Windows CE
		var axo = 1;
		var counter = 3;
		while(axo) {
			try {
				counter++;
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+ counter);
//				document.write("player v: "+ counter);
				PlayerVersion = new deconcept.PlayerVersion([counter,0,0]);
			} catch (e) {
				axo = null;
			}
		}
	} else { // Win IE (non mobile)
		// do minor version lookup in IE, but avoid fp6 crashing issues
		// see http://blog.deconcept.com/2006/01/11/getvariable-setvariable-crash-internet-explorer-flash-6/
		try{
			var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
		}catch(e){
			try {
				var axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
				PlayerVersion = new deconcept.PlayerVersion([6,0,21]);
				axo.AllowScriptAccess = "always"; // error if player version < 6.0.47 (thanks to Michael Williams @ Adobe for this code)
			} catch(e) {
				if (PlayerVersion.major == 6) {
					return PlayerVersion;
				}
			}
			try {
				axo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
			} catch(e) {}
		}
		if (axo != null) {
			PlayerVersion = new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));
		}
	}
	return PlayerVersion;
}
deconcept.PlayerVersion = function(arrVersion){
	this.major = arrVersion[0] != null ? parseInt(arrVersion[0]) : 0;
	this.minor = arrVersion[1] != null ? parseInt(arrVersion[1]) : 0;
	this.rev = arrVersion[2] != null ? parseInt(arrVersion[2]) : 0;
}
deconcept.PlayerVersion.prototype.versionIsValid = function(fv){
	if(this.major < fv.major) return false;
	if(this.major > fv.major) return true;
	if(this.minor < fv.minor) return false;
	if(this.minor > fv.minor) return true;
	if(this.rev < fv.rev) return false;
	return true;
}
/* ---- get value of query string param ---- */
deconcept.util = {
	getRequestParameter: function(param) {
		var q = document.location.search || document.location.hash;
		if (param == null) { return q; }
		if(q) {
			var pairs = q.substring(1).split("&");
			for (var i=0; i < pairs.length; i++) {
				if (pairs[i].substring(0, pairs[i].indexOf("=")) == param) {
					return pairs[i].substring((pairs[i].indexOf("=")+1));
				}
			}
		}
		return "";
	}
}
/* fix for video streaming bug */
deconcept.SWFObjectUtil.cleanupSWFs = function() {
	var objects = document.getElementsByTagName("OBJECT");
	for (var i = objects.length - 1; i >= 0; i--) {
		objects[i].style.display = 'none';
		for (var x in objects[i]) {
			if (typeof objects[i][x] == 'function') {
				objects[i][x] = function(){};
			}
		}
	}
}
/* add document.getElementById if needed (mobile IE < 5) */
if (!document.getElementById && document.all) { document.getElementById = function(id) { return document.all[id]; }}

/* add some aliases for ease of use/backwards compatibility */
var getQueryParamValue = deconcept.util.getRequestParameter;
var FlashObject = deconcept.SWFObject; // for legacy support
var SWFObject = deconcept.SWFObject;

jQuery.noConflict();

Liferay = {};

Liferay.Editor = {};

if (!Liferay._ajaxOld) {
	Liferay._ajaxOld = jQuery.ajax;
}

if (Liferay._ajaxOld) {
	jQuery.ajax = function(options) {
		if (Liferay.Util) {
			options.url = Liferay.Util.getURLWithSessionId(options.url);
		}

		return Liferay._ajaxOld(options);
	};
}

jQuery.ajaxSetup(
	{
		data: {},
		type: 'POST'
	}
);

Liferay.Service = {
	actionUrl: themeDisplay.getPathMain() + '/portal/json_service',

	tunnelUrl: themeDisplay.getPathContext() + '/tunnel-web/secure/json',

	classNameSuffix: 'ServiceJSON',

	ajax: function(options, callback) {
		var instance = this;

		var serviceUrl = instance.actionUrl;
		var tunnelEnabled = (Liferay.ServiceAuth && Liferay.ServiceAuth.header);

		if (tunnelEnabled) {
			serviceUrl = instance.tunnelUrl;
		}

		options.serviceParameters = Liferay.Service.getParameters(options);

		if (callback) {
			jQuery.ajax(
				{
					type: 'GET',
					url: serviceUrl,
					data: options,
					cache: false,
					dataType: 'json',
					beforeSend: function(xHR) {
						if (tunnelEnabled) {
							xHR.setRequestHeader('Authorization', Liferay.ServiceAuth.header);
						}
					},
					success: callback
				}
			);
		}
		else {
			var xHR = jQuery.ajax(
				{
					url: serviceUrl,
					data: options,
					dataType: 'json',
					async: false
				}
			);

			return eval('(' + xHR.responseText + ')');
		}
	},

	getParameters: function(options) {
		var serviceParameters = '';

		for (var key in options) {
			if ((key != 'serviceClassName') && (key != 'serviceMethodName') && (key != 'serviceParameterTypes')) {
				serviceParameters += key + ',';
			}
		}

		if (Liferay.Util.endsWith(serviceParameters, ',')) {
			serviceParameters = serviceParameters.substring(0, serviceParameters.length - 1);
		}

		return serviceParameters;
	}
};

/*

LEP-6815

Liferay.ServiceAuth = {
	header: null,

	setHeader: function(userId, password) {
		var instance = this;

		instance.header = "Basic " + Liferay.Base64.encode(userId + ':' + password);
	}
};

Liferay.Base64 = {
	encode: function(input) {
		var instance = this;

		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		input = instance._utf8Encode(input);

		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			}
			else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
		}

		return output;
	},

	decode: function(input) {
		var instance = this;

		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		while (i < input.length) {
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + String.fromCharCode(chr1);

			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}

			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}

		output = instance._utf8Decode(output);

		return output;
	},

	_utf8Encode: function(string) {
		string = string.replace(/\r\n/g,"\n");

		var utftext = "";

		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	_utf8Decode: function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;

		while (i < utftext.length) {
			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}

		return string;
	},

	_keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
};

*/

Liferay.Template = {
	PORTLET: '<div class="portlet"><div class="portlet-topper"><div class="portlet-title"></div></div><div class="portlet-content"></div><div class="forbidden-action"></div></div>'
}

jQuery.fn.exactHeight = jQuery.fn.height;
jQuery.fn.exactWidth = jQuery.fn.width;

if (!window.String.prototype.trim) {
	String.prototype.trim = function() {
		return jQuery.trim(this);
	};
}

// Fixing IE's lack of an indexOf/lastIndexOf on an Array

if (!window.Array.prototype.indexOf) {
	window.Array.prototype.indexOf = function(item) {
		for (var i=0; i<this.length; i++) {
            if(this[i]==item) {
                return i;
            }
        }

        return -1;
	};
}

if (!window.Array.prototype.lastIndexOf) {
	window.Array.prototype.lastIndexOf = function(item, fromIndex) {
		var length = this.length;

		if (fromIndex == null) {
			fromIndex = length - 1;
		}
		else if (fromIndex < 0) {
			fromIndex = Math.max(0, length + fromIndex);
		}

		for (var i = fromIndex; i >= 0; i--) {
			if (this[i] === item) {
				return i;
			}
		}

		return -1;
	};
}
Liferay.Browser = {

	init: function() {
		var instance = this;

		var version = instance.version();
		var exactVersion = instance.version(true);

		instance._browserVars = {
			agent: '',

			is_firefox: false,

			is_ie: false,
			is_ie_4: false,
			is_ie_5: false,
			is_ie_5_5: false,
			is_ie_5_up: false,
			is_ie_6: false,
			is_ie_7: false,

			is_mozilla: false,
			is_mozilla_1_3_up: false,

			is_ns_4: false,

			is_rtf: false,

			is_safari: false,
			is_opera: false
		};

		instance._browserVars.agent = instance.browser().toLowerCase();

		instance._browserVars.is_firefox = jQuery.browser.firefox;

		instance._browserVars.is_ie = jQuery.browser.msie;
		instance._browserVars.is_ie_4 = (instance.is_ie && version == 4);
		instance._browserVars.is_ie_5 = (instance.is_ie && version == 5);
		instance._browserVars.is_ie_5_5 = (instance.is_ie && exactVersion == 5.5);
		instance._browserVars.is_ie_5_up = (instance.is_ie && version >= 5);
		instance._browserVars.is_ie_6 = (instance.is_ie && version == 6);
		instance._browserVars.is_ie_7 = (instance.is_ie && version == 7);

		instance._browserVars.is_mozilla = (jQuery.browser.mozilla);
		instance._browserVars.is_mozilla_1_3_up = (instance.is_mozilla && exactVersion > 1.3);

		instance._browserVars.is_ns_4 = (jQuery.browser.netscape && version == 4);

		instance._browserVars.is_rtf = (instance.is_ie_5_5_up || instance.is_mozilla_1_3_up);

		instance._browserVars.is_safari = jQuery.browser.safari;
		instance._browserVars.is_opera = jQuery.browser.opera;

		jQuery.extend(instance, instance._browserVars);
	},

	browser: function() {
		var instance = this;

		return jQuery.browser.browser;
	},

	compat: function() {
		var instance = this;

		for (var i in instance._browserVars) {
			if (!window[i]) {
				window[i] = instance._browserVars[i];
			}
		}
	},

	version: function(exact) {
		var instance = this;

		if (!exact) {
			return jQuery.browser.version.major;
		}
		else {
			return jQuery.browser.version.string;
		}
	},

	_browserVars: {}
};

jQuery(
	function() {
		Liferay.Browser.init();
		//Uncomment the following line if you wish to have the original global variables set (eg. is_ie6, is_mozilla, etc)
		// Liferay.Browser.compat();
	}
);
Liferay.Util = {
	submitCountdown: 0,

	actsAsAspect: function(object) {
		object.yield = null;
		object.rv = {};

		object.before = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				f.apply(this, arguments);

				return original.apply(this, arguments);
			};
		};

		object.after = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.rv[method] = original.apply(this, arguments);

				return f.apply(this, arguments);
			};
		};

		object.around = function(method, f) {
			var original = eval('this.' + method);

			this[method] = function() {
				this.yield = original;

				return f.apply(this, arguments);
			};
		};
	},

	addInputFocus: function() {
		var inputs = jQuery('input:text, input:text, textarea');

		var focusEvent = function(event) {
			jQuery(this).addClass('focus');

			var value = this.value;
			var caretPos = value.length;

			if (this.createTextRange && (this.nodeName.toLowerCase() !== 'textarea')) {
				var textRange = this.createTextRange();

				textRange.moveStart('character', caretPos);
			}
			else if (this.selectionStart) {
				this.selectionStart = caretPos;
				this.selectionEnd = caretPos;
			}

			if (Liferay.Browser.is_ie && (this != document.activeElement)) {
				this.focus();
			}
		};

		var blurEvent = function(event) {
			jQuery(this).removeClass('focus');
		};

		inputs.focus(focusEvent);
		inputs.blur(blurEvent);

		inputs.livequery(
			'focus',
			focusEvent
		);

		inputs.livequery(
			'blur',
			blurEvent
		);

		jQuery('input.lfr-auto-focus').livequery(
			function() {
				jQuery('input').trigger('blur');

				jQuery(this).trigger('focus');
			}
		);
	},

	addInputType: function(el) {
		var instance = this;

		instance.addInputType = function() {
		};

		if (Liferay.Browser.is_ie && Liferay.Browser.version() < 7) {
			instance.addInputType = function(el) {
				var item;

				if (el) {
					if (typeof el == 'object') {
						item = jQuery(el);
					}
					else {
						item = jQuery('#' + el);
					}
				}
				else {
					item = document.body;
				}

				jQuery('input', item).each(function() {
					var current = jQuery(this);
					var type = this.type || 'text';

					current.addClass(type);
				});
			};
		}

		return instance.addInputType(el);
	},

	addParams: function(params, url) {
		var instance = this;

		if (typeof params == 'object') {
			params = jQuery.param(params);
		}
		else {
			params = jQuery.trim(params);
		}

		if (params != '') {
			var loc = url || location.href;
			var anchorHash, finalUrl;

			if (loc.indexOf('#') > -1) {
				var locationPieces = loc.split('#');
				loc = locationPieces[0];
				anchorHash = locationPieces[1];
			}

			if (loc.indexOf('?') == -1) {
				params = '?' + params;
			}
			else {
				params = '&' + params;
			}

			if (loc.indexOf(params) == -1) {
				finalUrl = loc + params;

				if (anchorHash) {
					finalUrl += '#' + anchorHash;
				}
				if (!url) {
					location.href = finalUrl;
				}
				return finalUrl;
			}
		}
	},

	check: function(form, name, checked) {
		jQuery('input[@name=' + name + ']:checkbox',form).attr('checked', checked);
	},

	checkAll: function(form, name, allBox) {
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[@name='+ name.join(']:checkbox,input[@name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[@name=' + name + ']:checkbox', form);
		}

		inputs.attr('checked', allBox.checked);
	},

	checkAllBox: function(form, name, allBox) {
		var totalBoxes = 0;
		var totalOn = 0;
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[@name='+ name.join(']:checkbox,input[@name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[@name=' + name + ']:checkbox', form);
		}

		inputs = inputs.not(allBox);

		totalBoxes = inputs.length;
		totalOn = inputs.filter(':checked').length;

		allBox.checked = (totalBoxes == totalOn);
	},

	checkMaxLength: function(box, maxLength) {
		if ((box.value.length) >= maxLength) {
			box.value = box.value.substring(0, maxLength - 1);
		}
	},

	checkTab: function(box) {
		if ((document.all) && (event.keyCode == 9)) {
			box.selection = document.selection.createRange();
			setTimeout('Liferay.Util.processTab("' + box.id + '")', 0);
		}
	},

	createFlyouts: function(options) {
		var instance = this;

		options = options || {};

		var flyout, containers;

		var containerFilter = function() {
			return (jQuery('ul', this).length != 0);
		};

		if (!options.container) {
			flyout = jQuery('.lfr-flyout');
			containers = flyout.find('li').filter(containerFilter);
		}
		else {
			flyout = jQuery('li', options.container);
			containers = flyout.filter(containerFilter);
		}

		containers.addClass('lfr-flyout');
		containers.addClass('has-children');

		if (!options.container) {
			containers = containers.add(flyout);
		}

		var over = function(event) {
			jQuery('> ul', this).show();

			if (options.mouseOver) {
				options.mouseOver.apply(this, [event]);
			}
		};

		var out = function(event) {
			jQuery('> ul', this).hide();

			if (options.mouseOut) {
				options.mouseOut.apply(this, [event]);
			}
		};

		containers.hoverIntent(
			{
				interval: 0,
				out: out,
				over: over,
				sensitivity: 2,
				timeout: 300
			}
		);
	},

	disableElements: function(obj) {
		var el = jQuery(obj);
		var children = el.find('*');

		var emptyFn = function() { return false; };

		var defaultEvents = function(el) {
			el.onclick = emptyFn;
			el.onmouseover = emptyFn;
			el.onmouseout = emptyFn;
			jQuery.event.remove(el);
		};

		var ieEvents = function(el) {
			el.onmouseenter = emptyFn;
			el.onmouseleave = emptyFn;
		};

		var removeEvents = defaultEvents;

		if (Liferay.Browser.is_ie) {
			removeEvents = function(el) {
				defaultEvents(el);
				ieEvents(el);
			};
		}

		for (var i = children.length - 1; i >= 0; i--) {
			var item = children[i];
			var nodeName = item.nodeName.toLowerCase();

			item.style.cursor = 'default';

			removeEvents(item);

			if (nodeName == 'a') {
				item.href = 'javascript: ;';
			}
			else if (nodeName == 'input' || nodeName == 'select' || nodeName == 'script') {
				item.disabled = true;
			}
			else if (nodeName == 'form') {
				item.action = '';
				item.onsubmit = emptyFn;
			}
		};
	},

	disableEsc: function() {
		if ((document.all) && (event.keyCode == 27)) {
			event.returnValue = false;
		}
	},

	disableTextareaTabs: function(textarea) {
		var instance = this;

		if (!textarea.jquery) {
			textarea = jQuery(textarea);
		}

		if (textarea.attr('textareatabs') != 'enabled') {
			textarea.attr('textareatabs', 'disabled');
			textarea.unbind('keydown.liferay', Liferay.Util.textareaTabs);
		}
	},

	enableTextareaTabs: function(textarea) {
		var instance = this;

		if (!textarea.jquery) {
			textarea = jQuery(textarea);
		}

		if (textarea.attr('textareatabs') != 'enabled') {
			textarea.attr('textareatabs', 'enabled');
			textarea.bind('keydown.liferay', Liferay.Util.textareaTabs);
		}
	},

	endsWith: function(str, x) {
		return (str.lastIndexOf(x) === (str.length - x.length));
	},

	focusFormField: function(el, caretPosition) {
		var interacting = false;
		var eventData = caretPosition ? [caretPosition] : null;

		jQuery(document).one(
			'click',
			function() {
				interacting = true;
			}
		);

		jQuery(
			function() {
				if (el && (el.offsetHeight != 0) && !interacting) {
					var elObj = jQuery(el);

					jQuery('input').trigger('blur');

					elObj.trigger('focus', eventData);
				}
			}
		);
	},

	getColumnId: function(str) {
		var columnId = str.replace(/layout-column_/, '');

		return columnId;
	},

	getPortletId: function(portletId) {
		portletId = portletId.replace(/^p_p_id_/i, '');
		portletId = portletId.replace(/_$/, '');

		return portletId;
	},

	getSelectedRadioValue: function(col) {
		return jQuery(col).filter(':checked').val() || '';
	},

	getURLWithSessionId: function(url) {
		if (document.cookie && (document.cookie.length > 0)) {
			return url;
		}

		// LEP-4787

		var x = url.indexOf(';');

		if (x > -1) {
			return url;
		}

		var sessionId = ';jsessionid=' + themeDisplay.getSessionId();

		x = url.indexOf('?');

		if (x > -1) {
			return url.substring(0, x) + sessionId + url.substring(x);
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
		// http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf('//');

		if (x > -1) {
			var y = url.lastIndexOf('/');

			if (x + 1 == y) {
				return url + '/' + sessionId;
			}
		}

		return url + sessionId;
	},

	/**
	 * OPTIONS
	 *
	 * Required
	 * button {string|object}: The button that opens the popup when clicked.
	 * height {number}: The height to set the popup to.
	 * textarea {string}: the name of the textarea to auto-resize.
	 * url {string}: The url to open that sets the editor.
	 * width {number}: The width to set the popup to.
	 */

	inlineEditor: function(options) {
		var instance = this;

		if (options.url && options.button) {
			var url = options.url;
			var button = options.button;
			var width = options.width || 680;
			var height = options.height || 640;
			var textarea = options.textarea;
			var clicked = false;

			var editorButton = jQuery(button);

			editorButton.click(
				function(event) {
					if (!clicked) {
						var form = jQuery([]);

						var popup = Liferay.Popup(
							{
								height: 640,
								width: 680,
								noCenter: true,
								title: '',
								resize: function(e, ui) {
									var cssData = ui.size;
									var dimensions = {};

									if (cssData.height) {
										dimensions.height = cssData.height - 130;
									}

									if (cssData.width) {
										dimensions.width = cssData.width - 20;
									}

									form.css(dimensions);

									jQuery(document).trigger('popupResize');
								},
								onClose: function() {
									jQuery(document).unbind('popupResize.liferay');
									clicked = false;
								}
							}
						);

						jQuery.ajax(
							{
								url: url + '&rt=' + Liferay.Util.randomInt(),
								success: function(message) {
									popup.find('.loading-animation').remove();

									popup.append(message);

									form = popup.find('form');

									if (textarea) {
										var usingPlainEditor = popup.find('.lfr-textarea').length;

										Liferay.Util.resizeTextarea(textarea, !usingPlainEditor, true);
									}
								}
							}
						);

						clicked = true;
					}
				}
			);
		}
	},

	isArray: function(object) {
		return !!(window.Array && object.constructor == window.Array);
	},

	listChecked: function(form) {
		var s = [];
		var inputs = jQuery('input[@value!=]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listCheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[@value!=][@name!="' + except + '"]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listSelect: function(box, delimeter) {
		var s = [];

		delimeter = delimeter || ',';

		if (box == null) {
			return '';
		}

		var opts = jQuery(box).find('option[@value!=]');

		opts.each(
			function() {
				s.push(this.value);
			}
		);

		if (s[0] == '.none') {
			return '';
		}
		else {
			return s.join(',');
		}
	},

	listUncheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[@value!=][@name!="' + except + '"]:checkbox:not(:checked)', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	moveItem: function(fromBox, toBox, sort) {
		if (fromBox.selectedIndex >= 0) {
			var toSelect = jQuery(toBox);
			var selectedOption = jQuery(fromBox).find('option:selected');

			toSelect.append(selectedOption);
		}

		if (selectedOption.text() != '' && sort == true) {
			Liferay.Util.sortBox(toBox);
		}
	},

	portletTitleEdit: function(options) {
		var instance = this;

		var obj = options.obj;
		var plid = options.plid;
		var doAsUserId = options.doAsUserId;
		var portletId = options.portletId;
		var url = options.url;

		var title = obj.find('.portlet-title');

		if (!title.is('.not-editable')) {
			title.editable(
				function(value, settings) {
					var cruft = settings._LFR_.cruft || [];

					cruft = cruft.join('');

					if (value != settings._LFR_.oldText) {
						Liferay.Util.savePortletTitle(
							{
								plid: plid,
								doAsUserId: doAsUserId,
								portletId: portletId,
								title: value
							}
						);
					}

					return cruft + value;
				},
				{
					cssclass: 'text',
					data: function(value, settings) {
						var input = jQuery(this);
						var re = new RegExp('<\/?[^>]+>|\n|\r|\t', 'gim');

						var cruft = value.match(re);

						settings._LFR_ = {};
						settings._LFR_.oldText = value;
						settings._LFR_.cruft = cruft;

						value = value.replace(re, '');
						settings._LFR_.oldText = value;

						return value;
					},
					height: '',
					width: '',
					onblur: 'submit',
					type: 'text',
					select: false,
					style: '',
					submit: ''
				}
			);
		}
	},

	processTab: function(id) {
		document.all[id].selection.text = String.fromCharCode(9);
		document.all[id].focus();
	},

	randomInt: function() {
		return (Math.ceil(Math.random() * (new Date).getTime()));
	},

	randomMinMax: function(min, max) {
		return (Math.round(Math.random() * (max - min))) + min;
	},

	removeItem: function(box, value) {
		var selectEl = jQuery(box);

		if (!value) {
			selectEl.find('option:selected').remove();
		}
		else {
			selectEl.find('option[@value=' + value + ']:selected').remove();
		}
	},

	reorder: function(box, down) {
		var si = box.selectedIndex;

		if (si == -1) {
			box.selectedIndex = 0;
		}
		else {
			sText = box.options[si].text;
			sValue = box.options[si].value;

			if ((box.options[si].value > '') && (si > 0) && (down == 0)) {
				box.options[si].text = box.options[si - 1].text;
				box.options[si].value = box.options[si - 1].value;
				box.options[si - 1].text = sText;
				box.options[si - 1].value = sValue;
				box.selectedIndex--;
			}
			else if ((si < box.length - 1) && (box.options[si + 1].value > '') && (down == 1)) {
				box.options[si].text = box.options[si + 1].text;
				box.options[si].value = box.options[si + 1].value;
				box.options[si + 1].text = sText;
				box.options[si + 1].value = sValue;
				box.selectedIndex++;
			}
			else if (si == 0) {
				for (var i = 0; i < (box.length - 1); i++) {
					box.options[i].text = box.options[i + 1].text;
					box.options[i].value = box.options[i + 1].value;
				}

				box.options[box.length - 1].text = sText;
				box.options[box.length - 1].value = sValue;

				box.selectedIndex = box.length - 1;
			}
			else if (si == (box.length - 1)) {
				for (var j = (box.length - 1); j > 0; j--) {
					box.options[j].text = box.options[j - 1].text;
					box.options[j].value = box.options[j - 1].value;
				}

				box.options[0].text = sText;
				box.options[0].value = sValue;

				box.selectedIndex = 0;
			}
		}
	},

	resizeTextarea: function(elString, usingRichEditor, resizeToInlinePopup) {
		var init = function() {
			var el = jQuery('#' + elString);

			if (!el.length) {
				el = jQuery('textarea[@name=' + elString + ']');
			}

			if (el.length) {
				var pageBody;

				if (resizeToInlinePopup) {
					pageBody = el.parents('.ui-dialog:first');
				}
				else {
					pageBody = jQuery('body');
				}

				var resize = function() {
					var pageBodyHeight = pageBody.height();

					if (usingRichEditor) {
						try {
							if (!el.is('iframe')) {
								el = eval(elString);

								if (!el.jquery) {
									el = jQuery(el);
								}
							}
						}
						catch (e) {
						}
					}

					var diff = 170;

					if (!resizeToInlinePopup) {
						diff = 100;
					}

					el.css(
						{
							height: (pageBodyHeight - diff) + 'px',
							width: '98%'
						}
					);
				};

				resize();

				if (resizeToInlinePopup) {
					jQuery(document).bind('popupResize.liferay', resize);
				}
				else {
					jQuery(window).resize(resize);
				}
			}
		};

		jQuery(init);
	},

	resubmitCountdown: function(formName) {
		if (Liferay.Util.submitCountdown > 0) {
			Liferay.Util.submitCountdown--;

			setTimeout('Liferay.Util.resubmitCountdown("' + formName + '")', 1000);
		}
		else {
			Liferay.Util.submitCountdown = 0;

			if (!Liferay.Browser.is_ns_4) {
				document.body.style.cursor = 'auto';
			}

			var form = document.forms[formName];

			for (var i = 0; i < form.length; i++) {
				var e = form.elements[i];

				if (e.type && (e.type.toLowerCase() == 'button' || e.type.toLowerCase() == 'reset' || e.type.toLowerCase() == 'submit')) {
					e.disabled = false;
				}
			}
		}
	},

	savePortletTitle: function(params) {
		var defaultParams = {
			plid: 0,
			doAsUserId: 0,
			portletId: 0,
			title: '',
			url: themeDisplay.getPathMain() + '/portlet_configuration/update_title'
		};

		var settings = jQuery.extend(defaultParams, params);

		jQuery.ajax(
			{
				url: settings.url,
				data: {
					p_l_id: settings.plid,
					doAsUserId: settings.doAsUserId,
					portletId: settings.portletId,
					title: settings.title
				}
			}
		);
	},

	selectAndCopy: function(el) {
		el.focus();
		el.select();

		if (document.all) {
			var textRange = el.createTextRange();

			textRange.execCommand('copy');
		}
	},

	setBox: function(oldBox, newBox) {
		for (var i = oldBox.length - 1; i > -1; i--) {
			oldBox.options[i] = null;
		}

		for (var i = 0; i < newBox.length; i++) {
			oldBox.options[i] = new Option(newBox[i].value, i);
		}

		oldBox.options[0].selected = true;
	},

	setSelectedValue: function(col, value) {
		jQuery('option[@value=' + value + ']', col).attr('selected', true);
	},

	showCapsLock: function(event, span) {
		var keyCode = event.keyCode ? event.keyCode : event.which;
		var shiftKey = event.shiftKey ? event.shiftKey : ((keyCode == 16) ? true : false);

		if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) ||
			((keyCode >= 97 && keyCode <= 122) && shiftKey)) {

			document.getElementById(span).style.display = '';
		}
		else {
			document.getElementById(span).style.display = 'none';
		}
	},

	sortBox: function(box) {
		var newBox = [];

		for (var i = 0; i < box.length; i++) {
			newBox[i] = [box[i].value, box[i].text];
		}

		newBox.sort(Liferay.Util.sortByAscending);

		var boxObj = jQuery(box);

		boxObj.find('option').remove();

		jQuery.each(
			newBox,
			function(key, value) {
				boxObj.append('<option value="' + value[0] + '">' + value[1] + '</option>');
			}
		);

		if (Liferay.Browser.is_ie) {
			var currentWidth = boxObj.css('width');

			if (currentWidth == 'auto') {
				boxObj.css('width', 'auto');
			}
		}
	},

	sortByAscending: function(a, b) {
		a = a[1].toLowerCase();
		b = b[1].toLowerCase();

		if (a > b) {
			return 1;
		}

		if (a < b) {
			return -1;
		}

		return 0;
	},

	startsWith: function(str, x) {
		return (str.indexOf(x) === 0);
	},

	/**
	 * OPTIONS
	 *
	 * Required
	 * popup {string|object}: A jQuery selector or DOM element of the popup that contains the editor.
	 * textarea {string}: the name of the textarea to auto-resize.
	 * url {string}: The url to open that sets the editor.
	 */

	switchEditor: function(options) {
		var instance = this;

		if (options.url && options.popup) {
			var url = options.url;
			var popup = options.popup;
			var textarea = options.textarea;

			if (!popup.jquery) {
				popup = jQuery(popup);
			}

			var popupMessage = popup;

			jQuery.ajax(
				{
					url: url,
					beforeSend: function() {
						popupMessage.empty();
						popupMessage.append('<div class="loading-animation"><div>');
					},
				  	success: function(message) {
						popupMessage.empty();
						popupMessage.append(message);

						if (textarea) {
							var usingPlainEditor = popup.find('.lfr-textarea').length;

							Liferay.Util.resizeTextarea(textarea, !usingPlainEditor, true);
						}
				 	}
				}
			);
		}
	},

	textareaTabs: function(event) {
		var el = this;
		var pressedKey = event.which;

		if(pressedKey == 9 || (Liferay.Browser.is_safari && pressedKey == 25)) {
			event.preventDefault();
			event.stopPropagation();

			var oldscroll = el.scrollTop;

			if (el.setSelectionRange) {
				var caretPos = el.selectionStart + 1;
				var elValue = el.value;

				el.value = elValue.substring(0, el.selectionStart) + '\t' + elValue.substring(el.selectionEnd, elValue.length);

				setTimeout(
					function() {
						el.focus();
						el.setSelectionRange(caretPos, caretPos);
					}, 0);

			}
			else {
				document.selection.createRange().text='\t';
			}

	        el.scrollTop = oldscroll;

			return false;
	    }
	},

	toggleByIdSpan: function(obj, id) {
		jQuery('#' + id).toggle();

		var spans = jQuery(obj).find('span');

		spans.toggle();
	},

	toggle: function(obj, returnState, displayType) {
		if (typeof obj == 'string') {
			obj = '#' + obj;
		}

		var el = jQuery(obj);
		var hidden = el.toggle().is(':visible');

		if (displayType) {
			el.css('display', displayType);
			hidden = el.is(':visible');
		}

		if (returnState) {
			return hidden;
		}
	},

	toggleBoxes: function(checkBoxId, toggleBoxId) {
		var checkBox = jQuery('#' + checkBoxId);
		var toggleBox = jQuery('#' + toggleBoxId);

		if (!checkBox.is(':checked')) {
			toggleBox.hide();
		}

		checkBox.click(
			function() {
				toggleBox.toggle();
			}
		);
	},

	toggleControls: function() {
		var instance = this;

		var trigger = jQuery('.toggle-controls');
		var docBody = jQuery(document.body);
		var hiddenClass = 'controls-hidden';
		var visibleClass = 'controls-visible';
		var currentClass = visibleClass;

		if (Liferay._editControlsState != 'visible') {
			currentClass = hiddenClass;
		}

		docBody.addClass(currentClass);

		trigger.click(
			function(event) {
				docBody.toggleClass(visibleClass).toggleClass(hiddenClass);

				Liferay._editControlsState = (docBody.is('.' + visibleClass) ? 'visible' : 'hidden');

				jQuery.ajax(
					{
						url: themeDisplay.getPathMain() + '/portal/session_click',
						data: {
							'liferay_toggle_controls': Liferay._editControlsState
						}
					}
				);
			}
		);
	},

	viewport: {
		frame: function() {
			var instance = this;
			var viewport = jQuery(window);

			var x = viewport.width();
			var y = viewport.height();

			return {x: x, y: y};
		},
		page: function() {
			var instance = this;
			var viewport = jQuery(document);

			var x = viewport.width();
			var y = viewport.height();

			return {x: x, y: y};
		},
		scroll: function() {
			var instance = this;
			var viewport = jQuery(window);

			var x = viewport.scrollLeft();
			var y = viewport.scrollTop();

			return {x: x, y: y};
		}
	}
};

function submitForm(form, action, singleSubmit) {
	if (Liferay.Util.submitCountdown == 0) {
		Liferay.Util.submitCountdown = 10;

		setTimeout('Liferay.Util.resubmitCountdown("' + form.name + '")', 1000);

		if (singleSubmit == null || singleSubmit) {
			Liferay.Util.submitCountdown++;

			var inputs = jQuery('input[@type=button], input[@type=reset], input[@type=submit]', form);

			inputs.attr('disabled', true);
			inputs.fadeTo(50, 0.5);
		}

		if (action != null) {
			form.action = action;
		}

		if (!Liferay.Browser.is_ns_4) {
			document.body.style.cursor = 'wait';
		}

		form.submit();
	}
}

// 0-200: Theme Developer
// 200-400: Portlet Developer
// 400+: Liferay

Liferay.zIndex = {
	DOCK:			10,
	DOCK_PARENT:	20,
	ALERT:			430,
	DROP_AREA:		440,
	DROP_POSITION:	450,
	DRAG_ITEM:		460,
	TOOLTIP:		470
};
Liferay.Language = {
	get: function(key, extraParams) {
		var instance = this;

		var url = themeDisplay.getPathContext() + '/language/' + themeDisplay.getLanguageId() + '/' + key + '/';

		if (extraParams) {
			if (typeof extraParams == 'string') {
				url += extraParams;
			}
			else if (Liferay.Util.isArray(extraParams)) {
				url += extraParams.join('/');
			}
		}

		var value = instance._cache[url];

		if (value != null) {
			return value;
		}

		var xHR = jQuery.ajax(
			{
				async: false,
				type: 'GET',
				url: url
			}
		);

		value = xHR.responseText;

		instance._cache[url] = value;

		return value;
	},

	_cache: {}
};
Liferay.Layout = {
	init: function(options) {
		var instance = this;

		instance.isFreeForm = options.freeForm;

		var layoutHandler;

		if (!options.freeForm) {
			layoutHandler = instance.Columns;
		}
		else {
			layoutHandler = instance.FreeForm;
		}

		instance._useCloneProxy = options.clonePortlet;

		layoutHandler.init(options);

		instance.layoutHandler = layoutHandler;
	},

	refresh: function(portletBound) {
		var instance = this;

		instance.layoutHandler.refresh(portletBound);
	},

	showTemplates: function() {
		var instance = this;

		var url = themeDisplay.getPathMain() + '/layout_configuration/templates';

		jQuery.ajax(
			{
				url: url,
				data: {
					p_l_id: themeDisplay.getPlid(),
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					redirect: Liferay.currentURL
				},
				success: function(response) {
					Liferay.Popup(
						{
							width: 700,
							modal: true,
							message: response,
							position: ['center', 100],
							title: Liferay.Language.get('layout')
						}
					);
				}
			}
		);
	},

	_findIndex: function(portlet, parentNode) {
		var instance = this;

		parentNode = parentNode || portlet.parentNode;

		return jQuery('> .portlet-boundary', parentNode).index(portlet);
	},

	_saveLayout: function(options) {
		var instance = this;

		var data = {
			doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
			p_l_id: themeDisplay.getPlid()
		};

		jQuery.extend(data, options);

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/portal/update_layout',
				data: data
			}
		);
	}
};

Liferay.Layout.Columns = {
	init: function(options) {
		var instance = this;

		instance._columns = options.columnSelector;
		instance._portlets = options.boxSelector;
		instance._grid = jQuery(options.grid);
		instance._handleSelector = options.handleSelector;
		instance._boxSelector = options.boxSelector;
		instance._placeHolderClass = options.placeHolderClass;
		instance._onCompleteCallback = options.onComplete;

		instance._activeAreaClass = 'active-area';
		instance._dropAreaClass = 'drop-area';

		instance._gridColumns = '.lfr-column';

		instance._counter = 0;

		var options = {
			appendTo: 'body',
			connectWith: [instance._columns],
			dropOnEmpty: true,
			forcePointerForContainers: true,
			handle: instance._handleSelector,
			items: instance._boxSelector,
			helper: instance._createHelper,
			//placeholder: 'portlet-sort-helper',
			tolerance: 'guess',
			revert:	false,
			distance: 2,
			scroll: true,
			scrollSensitivity: 50,
			scrollSpeed: 30,
			custom: {
				refreshContainers: function() {
					for (var i = this.containers.length - 1; i >= 0; i--){
						var container = this.containers[i];
						var cell = container.element.parent();
						var offset = cell.offset();

						container.containerCache.left = offset.left;
						container.containerCache.top = offset.top;
						container.containerCache.width	= cell.outerWidth();
						container.containerCache.height = cell.outerHeight();
					};
				}
			},

			// Callbacks

			start: function(event, ui) {
				instance._onStart(event, ui);
			},
			stop: function(event, ui) {
				instance._onStop(event, ui);
			},
			update: function(event, ui) {
				instance._onUpdate(event, ui);
			},
			receive: function(event, ui) {
				instance._onReceive(event, ui);
			},
			remove: function(event, ui) {
				instance._onRemove(event, ui);
			},

			// These methods are sensitive to performance, so we don't add to
			// the callstack and instead just do the work inline.

			over: function(event, ui) {
				instance._counter++;
				jQuery(this).parent(instance._gridColumns).addClass(instance._activeAreaClass);
				ui.helper.removeClass('not-intersecting');
			},
			out: function(event, ui) {
				instance._counter++;
				jQuery(this).parent(instance._gridColumns).removeClass(instance._activeAreaClass);

				// We need to make sure that the active class and the intersection
				// logic don't fall out of sync

				if (!(instance._counter % 2)) {
					ui.helper.addClass('not-intersecting');
					instance._counter = 0;
				}
			},
			activate: function(event, ui) {
				instance._grid.addClass('dragging');
				jQuery(this).parent(instance._gridColumns).addClass(instance._dropAreaClass);
			},
			deactivate: function(event, ui) {
				jQuery(this).parent(instance._gridColumns).removeClass(instance._dropAreaClass);
			}
		};

		instance.sortColumns = jQuery(instance._columns);

		instance.sortColumns.sortable(options);

		jQuery(instance._boxSelector).find(instance._handleSelector).css('cursor', 'move');
	},

	refresh: function(portletBound) {
		var instance = this;

		if (portletBound) {
			jQuery(instance._handleSelector, portletBound).css('cursor', 'move');
		}

		instance.sortColumns.sortable('refresh');
	},

	startDragging: function() {
		var instance = this;

		instance._grid.addClass('dragging');
	},

	stopDragging: function() {
		var instance = this;

		instance._grid.removeClass('dragging');
	},

	_createHelper: function(event, obj) {
		var instance = this;

		var width = obj[0].offsetWidth;
		var height = obj[0].offsetHeight;
		var div = [];

		if (instance._useCloneProxy) {
			div = obj.clone();
		}
		else {
			div = jQuery(Liferay.Template.PORTLET);
			div.addClass('ui-proxy');

			var titleHtml = obj.find('.portlet-title, .portlet-title-default').html();

			div.find('.portlet-title').html(titleHtml);
		}

		div.css(
			{
				width: width,
				height: height,
				zIndex: Liferay.zIndex.DRAG_ITEM
			}
		);

		return div[0];
	},

	_onOut: function(event, ui) {
		var instance = this;
	},

	_onReceive: function(event, ui) {
		var instance = this;

		if (ui.element[0].className.indexOf('empty') > -1) {
			ui.element.removeClass('empty');
		}
	},

	_onRemove: function(event, ui) {
		var instance = this;

		var oCol = ui.element;
		var foundPortlets = oCol.find('.portlet-boundary');
		var minPortlets = 1;
		if (foundPortlets.length < minPortlets) {
			oCol.addClass('empty');
		}
	},

	_onStart: function(event, ui) {
		var instance = this;

		instance.startDragging();

		var sortColumns = instance.sortColumns.data('sortable');

		if (sortColumns.refreshPositions) {
			sortColumns.refreshPositions(true);
		}
	},

	_onStop: function(event, ui) {
		var instance = this;

		instance.stopDragging();
	},

	_onUpdate: function(event, ui) {
		var instance = this;

		var currentCol = ui.element[0];
		var portlet = (ui.item || [false])[0];

		if (portlet && portlet.parentNode == currentCol) {
			var position = Liferay.Layout._findIndex(portlet, currentCol);
			var currentColumnId = Liferay.Util.getColumnId(currentCol.id);
			var portletId = Liferay.Util.getPortletId(portlet.id);

			var viewport = Liferay.Util.viewport.scroll();
			var portletOffset = ui.item.offset();

			Liferay.Layout._saveLayout(
				{
					cmd: 'move',
					p_p_col_id: currentColumnId,
					p_p_col_pos: position,
					p_p_id: portletId
				}
			);

			if (instance._onCompleteCallback) {
				instance._onCompleteCallback(event, ui);
			}

			if (viewport.y > portletOffset.top) {
				window.scrollTo(portletOffset.left, portletOffset.top - 10);
			}
		}
	}
};

Liferay.Layout.FreeForm = {
	init: function(options) {
		var instance = this;

		// Set private variables

		instance._columns = options.columnSelector;
		instance._portlets = options.boxSelector;

		jQuery(instance._columns).find(instance._portlets).each(
			function() {
				instance.add(this);
			}
		);
	},

	add: function(portlet) {
		var instance = this;

		var handle = jQuery('.portlet-header-bar, .portlet-title-default, .portlet-topper', portlet);

		handle.css('cursor', 'move');

		var jPortlet = jQuery(portlet);

		if (!jPortlet.find('.ui-resizable-handle').length) {
			jPortlet.append('<div class="ui-resizable-handle ui-resizable-se"></div>');
		}

		jPortlet.css('position', 'absolute');

		instance._createHelperCache(portlet);

		var helperZIndex = instance._maxZIndex + 10;

		jPortlet.draggable(
			{
				handle: '.portlet-header-bar, .portlet-title-default, .portlet-topper, .portlet-topper *',
				helper: function(event) {
					var portlet = jQuery(this);
					var helper = instance._createHelperCache(this);

					var height = portlet.height();
					var width = portlet.width();

					helper.css(
						{
							height: height,
							width: width,
							zIndex: helperZIndex
						}
					);

					var titleHtml = portlet.find('.portlet-title, .portlet-title-default').html();

					helper.find('.portlet-title').html(titleHtml);

					return helper[0];
				},
				start: function(event, ui) {
					instance._moveToTop(this);
				},
				distance: 2,
				stop: function(event, ui) {
					var portlet = this;

					var left = parseInt(ui.position.left);
					var top = parseInt(ui.position.top);

					left = Math.round(left/10) * 10;
					top = Math.round(top/10) * 10;

					portlet.style.left = left + 'px';
					portlet.style.top = top + 'px';

					instance._savePosition(portlet);
				}
			}
		);

		jPortlet.mousedown(
			function(event) {
				if (instance._current != this) {
					instance._moveToTop(this, true);
					instance._savePosition(this, true);
					instance._current = this;
					this.style.zIndex = instance._maxZIndex;
				}
			}
		);

		var resizeBox = jQuery('.portlet-content-container, .portlet-borderless-container', portlet);
		var oldPortletHeight = parseInt(jPortlet[0].style.height) || jPortlet.height();

		jPortlet.resizable(
			{
				helper: 'ui-resizable-proxy',
				start: function(event, ui) {
					ui.helper.css('z-index', helperZIndex);
					instance._moveToTop(this);
				},
				stop: function(event, ui) {
					var portlet = this;
					var rBoxHeight = parseInt(resizeBox[0].style.height);
					var portletHeight = ui.size.height;
					var newHeight = Math.round((portletHeight / oldPortletHeight) * rBoxHeight);

					resizeBox.css('height', newHeight);
					jPortlet.css('height', 'auto');

					oldPortletHeight = portletHeight;
					instance._savePosition(portlet);
				}
			}
		);

		if ((parseInt(portlet.style.top) + parseInt(portlet.style.left)) == 0) {
			portlet.style.top = (20 * portlet.columnPos) + 'px';
			portlet.style.left = (20 * portlet.columnPos) + 'px';
		}

		instance._current = portlet;
	},

	refresh: function(portletBound) {
		var instance = this;

		if (portletBound) {
			instance.add(portletBound);
		}
	},

	_createHelperCache: function(obj) {
		var instance = this;

		if (!obj.jquery) {
			obj = jQuery(obj);
		}

		var cache = obj.data('ui-helper-drag');

		if (!cache) {
			var cachedObj = jQuery(Liferay.Template.PORTLET);

			cachedObj.addClass('ui-proxy');

			cache = obj.data('ui-helper-drag', cachedObj);
		}

		return cache;
	},

	_moveToTop: function(portlet, temporary) {
		var instance = this;

		var container = portlet.parentNode;
		portlet.oldPosition = Liferay.Layout._findIndex(portlet);

		if (!temporary) {
			container.appendChild(portlet);
		}
		else {
			portlet.style.zIndex = instance._maxZIndex + 5;

			jQuery(portlet).one(
				'click',
				function(event) {
					instance._moveToTop(this);
				}
			);
		}
	},

	_savePosition: function(portlet, wasClicked) {
		var instance = this;
		var resizeBox = jQuery(portlet).find('.portlet-content-container, .portlet-borderless-container')[0];
		var position = Liferay.Layout._findIndex(portlet);
		var portletId = Liferay.Util.getPortletId(portlet.id);
		var changedIndex = (position != portlet.oldPosition);
		var changedPosition = (resizeBox && !wasClicked);

		if (changedIndex || changedPosition) {
			if (changedIndex) {
				var currentColumnId = Liferay.Util.getColumnId(portlet.parentNode.id);

				Liferay.Layout._saveLayout(
					{
						cmd: 'move',
						p_p_col_id: currentColumnId,
						p_p_col_pos: position,
						p_p_id: portletId
					}
				);
			}

			if (changedPosition) {
				Liferay.Layout._saveLayout(
					{
						cmd: 'drag',
						height: resizeBox.style.height,
						left: portlet.style.left,
						p_p_id: portletId,
						top: portlet.style.top,
						width: portlet.style.width
					}
				);
			}
		}
	},

	_maxZIndex: 99
};
Liferay.Events = {
	bind: function(event, func, scope) {
		var instance = this;

		event = event + '.liferay-events';
		jQuery(document).bind(event, 
			function() {
				func.apply(scope || this, arguments);
			}
		);
	},

	trigger: function(event, data) {
		var instance = this;

		event = event + '.liferay-events';
		jQuery(document).trigger(event, data);
	},

	unbind: function(event, func) {
		var instance = this;

		event = event + '.liferay-events';
		jQuery(document).unbind(event, func);
	}
};

// Shorthand

Liferay.bind = Liferay.Events.bind;
Liferay.trigger = Liferay.Events.trigger;
Liferay.unbind = Liferay.Events.unbind;
/**
 * OPTIONS
 *
 * Required
 * message {string|object}: The default HTML/object to display.
 * width {number}: The starting width of the message box.
 *
 * Optional
 * className {string}: A class to add to the specific popup.
 * dragHelper {string|function}: A jQuery selector or a function that returns a DOM element.
 * handles {string}: A comma-separated list (n,ne,e,se,s,sw,w,nw) of the handles for resizing.
 * height {number}: The starting height of the message box.
 * messageId {string}: A unique ID to give to a popup's content.
 * modal {boolean}: Whether to show shaded background.
 * noCenter {boolean}: Whether to prevent re-centering.
 * stack {boolean}: Whether to automatically stack the popup on top of other ones.
 * resizeHelper {string}: A class that will be attached to resize proxy helper.
 *
 * Callbacks
 * dragStart {function}: Called when dragging of the dialog starts.
 * dragStop {function}: Called when dragging of the dialog starts.
 * onClose {function}: Called when a dialog is closed.
 */

Liferay.Popup = function(options) {
	var instance = this;

	var cacheDialogHelper = function(obj) {
		if (!obj.jquery) {
			obj = jQuery(obj);
		}

		var cache = obj.data('ui-helper-drag');

		if (!cache) {
			var cachedObj = obj.clone();

			cachedObj.find('.ui-dialog-content').empty();
			cachedObj.addClass('ui-proxy');

			cache = obj.data('ui-helper-drag', cachedObj);
		}

		return cache;
	};

	var checkExternalClick = function(element) {
		if (jQuery.datepicker) {
			jQuery.datepicker._checkExternalClick(
				{
					target: element
				}
			);
		}
	};

	options = options || {};

	if (options.dragHelper === null) {
		options.dragHelper = "original";
	}

	var defaults = {
		className: 'generic-dialog',
		draggable: true,
		handles: 'e,se,s,sw,w',
		resizeHelper: 'ui-resizable-proxy',
		message: '<div class="loading-animation"></div>',
		position: [5,5],
		height: 'auto',
		stack: false,
		dragHelper: function() {
			var dialog = jQuery(this);
			var cache = cacheDialogHelper(dialog);

			var height = dialog.height();
			var width = dialog.width();

			cache.css(
				{
					height: height,
					width: width
				}
			);

			return cache;
		},
		dragStart: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first');
				var target = jQuery(e.target);

				checkExternalClick(target);
				dialog.css('visibility', 'hidden');
			}
		},
		dragStop: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first');
				var helper = ui.helper;

				var left = helper.css('left');
				var top = helper.css('top');

				dialog.css(
					{
						left: left,
						top: top,
						visibility: 'visible'
					}
				);
			}
		},

		close: function() {
			var target = jQuery(this);

			checkExternalClick(target);
		},

		open: function(e, ui) {
			if (!options.dragHelper) {
				var dialog = jQuery(this).parents('.ui-dialog:first'), target = jQuery(this);
				
				dialog.click(function() {
					checkExternalClick(target);
				});
				
				cacheDialogHelper(dialog);
			}
		}
	};

	var config = jQuery.extend({}, defaults, options);

	var content = '';
	var message = config.message;

	if (typeof message == 'string') {
		content = jQuery('<div>' + config.message + '</div>');
	}
	else {
		content = jQuery('<div></div>').append(config.message);
	}

	var modal = config.modal;
	var draggable = config.draggable;
	var position = config.noCenter ? defaults.position : 'center';

	position = config.position || position;

	var top = config.top;
	var left = config.left;

	var className = config.className;
	var height = config.height;
	var dragHelper = config.dragHelper;
	var dragStart = config.dragStart;
	var dragStop = config.dragStop;
	var open = config.open;
	var close = config.close;
	var messageId = config.messageId;
	var resizable = config.resizable;
	var resizeHelper = config.resizeHelper;
	var stack = config.stack;
	var title = config.title;
	var width = config.width;

	if (resizable !== false) {
		resizable = config.handles;
	}

	if (Liferay.Util.isArray(position)) {
		var centering = position.indexOf('center');

		if (centering > -1) {
			var wnd = jQuery(window);
			var popupWidth = width || 0;
			var popupHeight = (typeof height == 'string') ? 0 : height;

			position[centering] = (centering == 0 ? (wnd.width() / 2) - (popupWidth / 2) : (wnd.height() / 2) - (popupHeight / 2));
		}
	}

	if (title) {
		className += ' has-title';
	}

	if (messageId) {
		content.attr('id', messageId);
	}

	content.appendTo('body');

	content.bind(
		'dialogclose',
		function(event) {
			if (config.onClose) {
				config.onClose();
			}

			jQuery(this).remove();
		}
	);

	return content.dialog(
		{
			autoResize: false,
			dialogClass: className,
			draggable: draggable,
			height: height,
			title: title,
			position: position,
			modal: modal,
			resizable: resizable,
			resizeHelper: resizeHelper,
			stack: stack,
			width: width,
			zIndex: Liferay.zIndex.ALERT, // compensate for UI's dialog
			dragHelper: dragHelper,
			dragStart: dragStart,
			dragStop: dragStop,
			open: open,
			close: close
		}
	);
};

jQuery.extend(
	Liferay.Popup,
	{
		close: function(el) {
			var instance = this;

			var obj = el;

			if (!el.jquery) {
				obj = jQuery(el);
			}

			if (!obj.is('.ui-dialog-content')) {
				obj = obj.parents('.ui-dialog-content');
			}

			obj.dialog('close');
		},

		update: function(id, url) {
			var instance = this;

			var obj = jQuery(id);

			obj.html('<div class="loading-animation"></div>');
			obj.load(url);
		}
	}
);
Liferay.Portal = {};

Liferay.Portal.Tabs = {
	show: function(namespace, names, id) {
		var tab = jQuery('#' + namespace + id + 'TabsId');
		var panel = jQuery('#' + namespace + id + 'TabsSection');

		tab.siblings().removeClass('current');
		tab.addClass('current');

		panel.show();

		var index = names.indexOf(id);
		names.splice(index, 1);

		for (var i = 0; i < names.length; i++) {
			el = jQuery('#' + namespace + names[i] + 'TabsSection');
			el.hide();
		}
	}
};

Liferay.Portal.StarRating = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * displayOnly {boolean}: Whether the display is modifiable.
	 *
	 * Optional
	 * rating {number}: The rating to initialize to.
	 *
	 * Callbacks
	 * onComplete {function}: Called when a rating is selected.
	 */

	initialize: function(id, options) {
		this.options = options || {};
		this.rating = this.options.rating || 0;
		var item = jQuery('#' + id);
		this.stars = item.find('img');
		var self = this;

		if (!this.options.displayOnly) {
			item.bind('mouseout', {self: this}, this.onHoverOut);

			this.stars.each(function(index) {
				this.index = index + 1;
				jQuery(this).bind('click', {self: self}, self.onClick)
					.bind('mouseover', {self: self}, self.onHoverOver);
			})
		}

		this.display(this.rating, 'rating');
	},

	display: function(rating, mode) {
		var self = this;
		rating = rating == null ? this.rating : rating;

		var whole = Math.floor(rating);
		var fraction = rating - whole;

		this.stars.each(function(index) {
			image = this;
			if (index < whole) {
				if (mode == 'hover') {
					image.src = image.src.replace(/\bstar_.*\./, 'star_hover.');
				}
				else {
					image.src = image.src.replace(/\bstar_.*\./, 'star_on.');
				}
			}
			else {
				if (fraction < 0.25) {
					image.src = image.src.replace(/\bstar_.*\./, 'star_off.');
				}
				else if (fraction < 0.50) {
					image.src = image.src.replace(/\bstar_.*\./, 'star_on_quarter.');
				}
				else if (fraction < 0.75) {
					image.src = image.src.replace(/\bstar_.*\./, 'star_on_half.');
				}
				else if (fraction < 1.00) {
					image.src = image.src.replace(/\bstar_.*\./, 'star_on_threequarters.');
				}
				fraction = 0;
			}
		});
	},

	onHoverOver: function(event) {
		event.data.self.display(this.index, 'hover');
	},

	onHoverOut: function(event) {
		event.data.self.display();
	},

	onClick: function(event) {
		var target = this;
		var newRating = target.index;
		var self = event.data.self;

		self.rating = newRating;

		if (self.options.onComplete) {
			self.options.onComplete(newRating);
		}

		self.display(newRating);
	}
});

Liferay.Portal.ThumbRating = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * displayOnly {boolean}: Whether the display is modifiable.
	 *
	 * Optional
	 * rating {number}: The rating to initialize to.
	 *
	 * Callbacks
	 * onComplete {function}: Called when a rating is selected.
	 */

	initialize: function(options) {
		var instance = this;

		options = options || {};
		instance.rating = options.rating || 0;

		var item = jQuery('#' + options.id);
		instance.triggers = item.find('.rating');
		instance._onComplete = options.onComplete;

		if (!options.displayOnly) {
			instance.triggers.click(
				function(event) {
					instance._click(event, this);
				}
			);
		}
	},

	_click: function(event, obj) {
		var instance = this;
		var trigger = jQuery(obj);
		var rating = trigger.is('.rate-up') ? 1 : -1;

		if (trigger.is('.rated')) {
			rating = 0;
		}

		instance.triggers.not(obj).removeClass('rated');
		trigger.toggleClass('rated');

		if (instance._onComplete) {
			instance._onComplete(rating);
		}
	}
});

Liferay.Portal.ToolTip = {
	container: null,

	show: function(event, obj, text) {
		var instance = this;

		var container = instance.container;
		var currentItem = jQuery(obj);
		var position = currentItem.offset();

		var dimensions = instance._windowCalculation();

		if (!container) {
			container = jQuery('<div class="portal-tool-tip"></div>').appendTo('body');

			instance.container = container;
		}

		container.html(text);

		container.show();

		var boxWidth = container.width();
		var width = currentItem.width();
		var height = currentItem.height();
		var boxHeight = container.height();
		var left = position.left - (boxWidth / 2);
		var top = position.top + height + 5;

		if (left < 0) {
			left = 5;
		}
		else {
			left += 5;
		}

		if (left + boxWidth > dimensions.right) {
			left = (left - (boxWidth / 2 )) + width;
		}

		if (top + boxHeight > dimensions.bottom) {
			top = top - (height + boxHeight + 5);
		}

		container.css(
			{
				cursor: 'default',
				left: left + 'px',
				position: 'absolute',
				top: top + 'px',
				zIndex: Liferay.zIndex.TOOLTIP
			}
		);

		currentItem.one(
			'mouseout',
			function() {
				instance.hide();
			}
		);
	},

	hide: function(event) {
		var instance = this;

		instance.container.hide();
	},

	_windowCalculation: function() {
		var instance = this;

		if (instance._window.right == null) {
			var windowSize = {};
			var body = instance._body;
			if (!body) {
				body = jQuery('body');
				instance._body = body;
			}

			instance._window = {
				bottom: body.height(),
				left: 0,
				right: body.width(),
				top: 0
			};

			jQuery(window).resize(
				function() {
					instance._window.bottom = body.height();
					instance._window.right = body.width();
				}
			);
		}
		return instance._window;
	},
	_body: null,
	_window: {}
};
Liferay.Portlet = {
	list: [],

	add: function(options) {
		var instance = this;

		var plid = options.plid || themeDisplay.getPlid();
		var portletId = options.portletId;
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();
		var placeHolder = jQuery(options.placeHolder || '<div class="loading-animation" />');
		var positionOptions = options.positionOptions;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		var container = jQuery('.lfr-portlet-column:first');

		if (!container.length) {
			return;
		}

		var portletPosition = 0;
		var currentColumnId = 'column-1';

		if (options.placeHolder) {
			var column = placeHolder.parent();

			placeHolder.addClass('portlet-boundary');

			portletPosition = column.find('.portlet-boundary').index(placeHolder[0]);

			currentColumnId = Liferay.Util.getColumnId(column[0].id);
		}

		var url = themeDisplay.getPathMain() + '/portal/update_layout';

		var data = {
			p_l_id: plid,
			p_p_id: portletId,
			p_p_col_id: currentColumnId,
			p_p_col_pos: portletPosition,
			doAsUserId: doAsUserId,
			dataType: 'json',
			cmd: 'add'
		};

		var firstPortlet = container.find('.portlet-boundary:first');
		var hasStaticPortlet = (firstPortlet.length && firstPortlet[0].isStatic);

		if (!options.placeHolder && !options.plid) {
			if (!hasStaticPortlet) {
				container.prepend(placeHolder);
			}
			else {
				firstPortlet.after(placeHolder);
			}
		}

		if (themeDisplay.isFreeformLayout()) {
			container.prepend(placeHolder);
		}

		data.currentURL = Liferay.currentURL;

		return instance.addHTML(
			{
				beforePortletLoaded: beforePortletLoaded,
				data: data,
				url: url,
				placeHolder: placeHolder[0],
				onComplete: onComplete
			}
		);
	},

	addHTML: function(options) {
		var instance = this;

		var portletBoundary = null;

		var url = options.url;
		var data = options.data;
		var dataType = 'html';
		var placeHolder = options.placeHolder;
		var beforePortletLoaded = options.beforePortletLoaded;
		var onComplete = options.onComplete;

		if (data && data.dataType) {
			dataType = data.dataType;
		}

		var addPortletReturn = function(html) {
			var container = placeHolder.parentNode;

			var portletBound = jQuery('<div></div>')[0];

			portletBound.innerHTML = html;
			portletBound = portletBound.firstChild;

			var portletId = Liferay.Util.getPortletId(portletBound.id);

			portletBound.portletId = portletId;

			jQuery(placeHolder).hide().after(portletBound).remove();

			instance.refreshLayout(portletBound);

			Liferay.Util.addInputType(portletBound.id);

			if (window.location.hash) {
				window.location.hash = "p_" + portletId;
			}

			portletBoundary = portletBound;

			if (onComplete) {
				onComplete(portletBoundary, portletId);
			}

			var jContainer = jQuery(container);

			if (jContainer.is('.empty')) {
				jContainer.removeClass('empty');
			}

			return portletId;
		};

		if (beforePortletLoaded) {
			beforePortletLoaded(placeHolder);
		}

		jQuery.ajax(
			{
				url: url,
				data: data,
				dataType: dataType,
				success: function(message) {
					if (dataType == 'html') {
						addPortletReturn(message);
					}
					else {
						if (message.refresh) {
							location.reload();
						}
						else {
							addPortletReturn(message.portletHTML);
						}
					}
				}
			}
		);
	},

	close: function(portlet, skipConfirm, options) {
		var instance = this;

		if (skipConfirm || confirm(Liferay.Language.get('are-you-sure-you-want-to-remove-this-component'))) {
			options = options || {};

			var plid = options.plid || themeDisplay.getPlid();
			var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

			var portletId = portlet.portletId;
			var currentPortlet = jQuery(portlet);
			var column = currentPortlet.parents('.lfr-portlet-column:first');

			currentPortlet.remove();
			jQuery('#' + portletId).remove();

			var url = themeDisplay.getPathMain() + '/portal/update_layout';

			jQuery.ajax(
				{
					url: url,
					data: {
						p_l_id: plid,
						p_p_id: portletId,
						doAsUserId: doAsUserId,
						cmd: 'delete'
					}
				}
			);

			var portletsLeft = column.find('.portlet-boundary').length;

			if (!portletsLeft) {
				column.addClass('empty');
			}

			Liferay.trigger('closePortlet', {plid: plid, portletId: portletId});
		}
		else {
			self.focus();
		}
	},

	minimize: function(portlet, el, options) {
		var instance = this;

		options = options || {};

		var plid = options.plid || themeDisplay.getPlid();
		var doAsUserId = options.doAsUserId || themeDisplay.getDoAsUserIdEncoded();

		var content = jQuery('.portlet-content-container', portlet);
		var restore = content.is(':hidden');

		content.toggle(
			'blind',
			{
				direction: 'vertical'
			},
			'fast',
			function() {
				var action = (restore) ? 'removeClass' : 'addClass';
				jQuery(portlet)[action]('portlet-minimized');

				if (el) {
					var title = (restore) ? Liferay.Language.get('minimize') : Liferay.Language.get('restore');

					var link = jQuery(el);
					var img = link.find('img');

					var imgSrc = img.attr('src');
					if (restore) {
						imgSrc = imgSrc.replace(/restore.png$/, 'minimize.png');
					}
					else {
						imgSrc = imgSrc.replace(/minimize.png$/, 'restore.png');
					}

					img.attr('alt', title);
					img.attr('title', title);

					link.attr('title', title);
					img.attr('src', imgSrc);
				}
			}
		);

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + '/portal/update_layout',
				data: {
					p_l_id: plid,
					p_p_id: portlet.portletId,
					p_p_restore: restore,
					doAsUserId: doAsUserId,
					cmd: 'minimize'
				}
			}
		);
	},

	onLoad: function(options) {
		var instance = this;

		var canEditTitle = options.canEditTitle;
		var columnPos = options.columnPos;
		var isStatic = (options.isStatic == 'no') ? null : options.isStatic;
		var namespacedId = options.namespacedId;
		var portletId = options.portletId;

		jQuery(
			function () {
				var jPortlet = jQuery('#' + namespacedId);
				var portlet = jPortlet[0];

				if (!portlet.portletProcessed) {
					portlet.portletProcessed = true;
					portlet.portletId = portletId;
					portlet.columnPos = columnPos;
					portlet.isStatic = isStatic;

					// Functions to run on portlet load

					if (canEditTitle) {
						Liferay.Util.portletTitleEdit(
							{
								obj: jPortlet,
								plid: themeDisplay.getPlid(),
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								portletId: portletId
							}
						);
					}

					if (!themeDisplay.layoutMaximized) {
						jPortlet.find('.portlet-configuration:first a').click(
							function(event) {
								location.href = this.href + '&previewWidth=' + portlet.offsetHeight;

								return false;
							}
						);

						jPortlet.find('.portlet-minimize:first a').click(
							function(event) {
								instance.minimize(portlet, this);

								return false;
							}
						);

						jPortlet.find('.portlet-maximize:first a').click(
							function(event) {
								submitForm(document.hrefFm, this.href);

								return false;
							}
						);

						jPortlet.find('.portlet-close:first a').click(
							function(event) {
								instance.close(portlet);

								return false;
							}
						);

						jPortlet.find('.portlet-refresh:first a').click(
							function(event) {
								instance.refresh(portlet);

								return false;
							}
						);

						jPortlet.find('.portlet-print:first a').click(
							function(event) {
								location.href = this.href;

								return false;
							}
						);

						jPortlet.find('.portlet-css:first a').click(
							function(event) {
								Liferay.PortletCSS.init(portlet.portletId);
							}
						);
					}

					Liferay.trigger('portletReady', {portletId: portletId, portlet: jPortlet});

					var list = instance.list;

					var index = list.indexOf(portletId);

					if (index > -1) {
						list.splice(index, 1);
					}

					if (!list.length) {
						Liferay.trigger('allPortletsReady', {portletId: portletId});
					}
				}
			}
		);
	},

	refresh: function(portlet) {
		var instance = this;

		if (portlet.refreshURL) {
			var url = portlet.refreshURL;
			var id = portlet.id;

			portlet = jQuery(portlet);

			var placeHolder = jQuery('<div class="loading-animation" id="p_load' + id + '" />');

			portlet.before(placeHolder);
			portlet.remove();

			instance.addHTML(
				{
					url: url,
					placeHolder: placeHolder[0],
					onComplete: function(portlet, portletId) {
						portlet.refreshURL = url;
					}
				}
			);
		}
	},

	refreshLayout: function(portletBound) {
	}
};

jQuery.fn.last = function(fn) {
	Liferay.bind('allPortletsReady',
		function(event) {
			fn();
		}
	)
};

// Backwards compatability

Liferay.Portlet.ready = function(fn) {
	Liferay.bind('portletReady',
		function(event, data) {
			fn(data.portletId, data.portlet);
		}
	);
};
Liferay.autoFields = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * addText {string}: The text you wish to use for the "Add" link.
	 * clearText {string}: The text you wish to use for the "Clear" link (this link removes all of the added forms except the very first one).
	 * container {string|object}: A jQuery selector that specifies where you wish to append the HTML to.
	 * confirmText {string}: the text you wish to use to confirm that the user wishes to clear all of the added buttons (leave empty to not confirm).
	 * html {string}: HTML to append to the end of the container.
	 * removeText {string}: The text you wish to use for the "Remove" link.
	 * rowType {string}: The html tag for the row of fields (eg. fieldset, div or tr).
	 *
	 * Callbacks
	 * init {function}: Called after the class has fully initialized.
	 * onAdd {function}: Called after new fields have been added.
	 * onRemove {function}: Called after fields have been removed.
	 * onClear {function}: Called after the form fields have been returned.
	 */

	initialize: function(options) {
		var instance = this;

		options = jQuery.extend(options, {});

		instance._html = jQuery(options.html || '');
		instance._container = jQuery(options.container || '');
		instance._addText = options.addText || '';
		instance._removeText = options.removeText || '';
		instance._clearText = options.clearText || '';
		instance._confirmText = options.confirmText || '';
		instance._rowType = options.rowType || '';
		instance._onAdd = options.onAdd;
		instance._onRemove = options.onRemove;
		instance._onClear = options.onClear;
		instance._init = options.init || false;

		instance._numField = 1;

		instance._run();

		if (instance._init) {
			instance._init();
		}
	},

	_run: function() {
		var instance = this;

		var container = instance._container;

		if (container.length) {
			var html = instance._html;

			var addLink, removeLink, clearLink;
			var links = jQuery('<span class="lfr-control-links"></span>');

			if (instance._addText) {
				addLink = jQuery('<a href="javascript:;">' + instance._addText + '</a>');

				addLink.click(
				   function() {
					   var newField = instance._addFields();
					   if (instance._onAdd) {
							   instance._onAdd(newField);
					   }
				   }
				);

				links.append(addLink);
			}

			if (instance._removeText) {
				removeLink = jQuery('<a href="javascript:;">' + instance._removeText + '</a>');

				removeLink.hide();

				removeLink.click(
				   function() {
					   instance._removeFields();
					   if (instance._onRemove) {
							   instance._onRemove();
					   }
				   }
				);

				links.append(removeLink);
			}

			if (instance._clearText) {
				clearLink = jQuery('<a href="javascript:;">' + instance._clearText + '</a>');

				clearLink.click(
				   function() {
					   instance._clearFields();
					   if (instance._onClear) {
							   instance._onClear();
					   }
				   }
				);

				links.append(clearLink);
			}

			container.after(links);
			instance._controlLinks = links;
		}
	},

	_addFields: function() {
		var instance = this;

		var container = instance._container;
		var html = instance._html.clone();

		container.append(html);

		instance._numField++;

		var removeLink = instance._controlLinks.find('a:eq(1)');

		if (removeLink.is(':hidden')) {
			removeLink.show();
		}

		return html;
	},

	_clearFields: function() {
		var instance = this;

		var container = instance._container;
		var rows = container.find(instance._rowType).not(':first');

		var confirmBox = true;

		if (instance._confirmText) {
			   confirmBox = confirm(instance._confirmText);
		}

		if (confirmBox) {
			   rows.remove();
			   instance._numField = 1;
		}
	},

	_removeFields: function() {
		var instance = this;

		var container = instance._container;
		var lastRow = container.find(instance._rowType + ':last');

		if (instance._numField > 1) {
			   lastRow.remove();
			   --instance._numField;
		}

		if (instance._numField <= 1) {
		   var removeLink = instance._controlLinks.find('a:eq(1)');

		   if (removeLink.is(':visible')) {
				   removeLink.hide();
		   }
		}
	}
});
Liferay.ColorPicker = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * item {string|object}: A jQuery selector or DOM element that specifies which field to insert the selected value into.
	 *
	 * Optional
	 * context {object}: A DOM element which specifies the context in which to search for the item.
	 * hasImage {boolean}: Whether an image is provided in the DOM or options object (via the item option).
	 *
	 * Callbacks
	 * onChange {function}: Called whenever the color changes.
	 * onClose {function}: Called when the color picker is closed.
	 */

	initialize: function(options) {
		var instance = this;

		instance._onClose = options.onClose;
		instance._onChange = options.onChange;
		instance._context = options.context || document.body;
		instance._hasImage =  options.hasImage || false;
		instance._item = jQuery(options.item || '.use-colorpicker', instance._context);

		instance._currentColor = {r:255, g:255, b:255};

		instance._insertImages();

		instance._buildHTML();
	},

	_buildHTML: function() {
		var instance = this;

		var baseDiv = jQuery('<div class="lfr-colorpicker" />');
		var closeButton = jQuery('<div class="ui-colorpicker-close" />')

		baseDiv.append(closeButton);

		baseDiv.appendTo('body');

		closeButton.click(
			function(event) {
				instance._toggle(event, this);
			}
		);

		var onChange = function(event, ui) {
			instance._currentInput.val('#' + ui.hex);
			instance._currentColor = ui.rgb;

			if (instance._onChange) {
				instance._onChange(ui.rgb);
			}
		};

		baseDiv.colorpicker(
			{
				change: onChange,
				picking: onChange,
				pick: onChange
			}
		);

		baseDiv.hide();

		baseDiv.css(
			{
				position: 'absolute',
				zIndex: Liferay.zIndex.ALERT + 1
			}
		);

		instance._baseDiv = baseDiv;
	},

	_insertImages: function() {
		var instance = this;

		var context = instance._context;

		var items = instance._item;

		var colorPickerImgHTML = '<img alt="' + Liferay.Language.get('color-picker') + '" class="lfr-colorpicker-img" src="' + themeDisplay.getPathThemeImages() + '/color_picker/color_picker.png" title="' + Liferay.Language.get('color-picker') + '" />';

		if (items.length == 1) {
			var colorPickerImg;

			if (instance._hasImage) {
				colorPickerImg = items;
			}
			else {
				colorPickerImg = jQuery(colorPickerImgHTML);

				items.after(colorPickerImg);
			}

			colorPickerImg.click(
				function(event) {
					instance._toggle(event, this);
				}
			);
		}
		else {
			items.each(
				function() {
					var item = jQuery(this);
					var colorPickerImg;

					if (!instance._hasImage) {
						colorPickerImg = jQuery(colorPickerImgHTML);
					}
					else {
						colorPickerImg = item;
					}

					colorPickerImg.click(
						function(event) {
							instance._toggle(event, this);
						}
					);

					item.after(colorPickerImg);
				}
			);
		}
	},

	_toggle: function(event, obj) {
		var instance = this;

		var item = jQuery(obj);

		var dimensions = item.offset();

		instance._currentInput = item.prev();

		var baseDiv = instance._baseDiv;

		if (baseDiv.is(':visible')) {
			baseDiv.hide();

			if (instance._item.is('input')) {
				instance._item.trigger('blur');
			}

			if (instance._onClose) {
				instance._onClose();
			}
		}
		else {
			baseDiv.show();

			if (instance._item.is('input')) {
				instance._item.trigger('focus');
			}

			baseDiv.css(
				{
					top: dimensions.top + 'px',
					left: dimensions.left + 25 + 'px'
				}
			);
		}
	}
});
Liferay.Dock = {
	init: function() {
		var instance = this;

		var dock = jQuery('.lfr-dock');

		if (!dock.is('.interactive-mode')) {
			return;
		}

		dock.addClass('lfr-component');

		var dockList = dock.find('.lfr-dock-list');

		if (dockList.length > 0) {
			var myPlaces = jQuery('.my-places', dock);

			Liferay.Util.createFlyouts(
				{
					container: dockList[0],
					mouseOver: function(event) {
						if (this.className.indexOf('my-places') > -1) {
							jQuery('.current-community > ul', this).show();
						}
						else if (this.parentNode.className.indexOf('taglib-my-places') > -1) {
							jQuery('ul', this.parentNode).hide();
							jQuery('> ul', this).show();
						}
					}
				}
			);

			dockList.find('li:first-child, a:first-child').addClass('first');
			dockList.find('li:last-child, a:last-child').addClass('last');

			instance._dock = dock;
			instance._dockList = dockList;
			instance._myPlaces = myPlaces;

			dockList.hide();
			dockList.wrap('<div class="lfr-dock-list-container"></div>');

			var dockDefaults = {
				cursor: 'pointer',
				position: 'absolute',
				zIndex: Liferay.zIndex.DOCK
			};

			instance._setPosition(dock, dockDefaults);

			var dockOver = function(event) {
				instance._setCloser();
				instance._toggle('show');
			};

			var dockOut = function(event) {
				instance._toggle('hide');
			};

			dock.hoverIntent(
				{
					interval: 0,
					out: dockOut,
					over: dockOver,
					timeout: 500
				}
			);

			if (Liferay.Browser.is_ie && Liferay.Browser.version() <= 6) {
				myPlaces.find('> ul').css('zoom', 1);
			}

			var dockParent = dock.parent();

			var dockParentDefaults = {
				position: 'relative',
				zIndex: Liferay.zIndex.DOCK_PARENT
			};

			instance._setPosition(dockParent, dockParentDefaults);

			instance._handleDebug();
		}
	},

	_setPosition: function(obj, defaults) {
		var instance = this;

		var settings = defaults;

		if (!obj.is('.ignore-position')) {
			var position = obj.css('position');
			var zIndex = obj.css('z-index');
			var isStatic = !/absolute|relative|fixed/.test(position);

			if (zIndex == 'auto' || zIndex == 0) {
				zIndex = defaults.zIndex;
			}

			// The position is static, but use top/left positioning as a trigger

			if (isStatic) {
				position = defaults.position;

				var top = parseInt(obj.css('top'));

				if (Liferay.Browser.is_safari && isNaN(top)) {
					top = -1;
				}

				if (!isNaN(top) && top != 0) {
					position = '';
					zIndex = '';
				}
			}

			settings = jQuery.extend(
				defaults,
				{
					position: position,
					zIndex: zIndex
				}
			);
		}

		obj.css(settings);

		return settings;
	},

	_handleDebug: function() {
		var instance = this;

		var dock = instance._dock;
		var dockList = instance._dockList;
		var myPlacesList = instance._myPlaces.find('> ul');

		if (dock.is('.debug')) {
			dock.show();
			dockList.show();
			dockList.addClass('expanded');
		}
	},

	_setCloser: function() {
		var instance = this;

		if (!instance._hovered) {
			jQuery(document).one(
				'click',
				function(event) {
					var currentEl = jQuery(event.target);
					var dockParent = currentEl.parents('.lfr-dock');

					if ((dockParent.length == 0) && !currentEl.is('.lfr-dock')) {
						instance._toggle('hide');
						instance._hovered = false;
					}
				}
			);

			instance._hovered = true;
		}
	},

	_toggle: function(state) {
		var instance = this;

		var dock = instance._dock;
		var dockList = instance._dockList;

		if (state == 'hide') {
			dockList.hide();
			dock.removeClass('expanded');
		}
		else if (state == 'show') {
			dockList.show();
			dock.addClass('expanded');
		}
		else {
			dockList.toggle();
			dock.toggleClass('expanded');
		}
	}
};
Liferay.DynamicSelect = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * array {array}: An array of options.
	 * array[i].select {string}: An id of a select box.
	 * array[i].selectId {string}: A JSON object field name for an option value.
	 * array[i].selectDesc {string}: A JSON object field name for an option description.
	 * array[i].selectVal {string}: The value that is displayed in an option field.
	 *
	 * Callbacks
	 * array[i].selectData {function}: Returns a JSON array to populate the next select box.
	 */

	initialize: function(array) {
		var instance = this;

		instance.array = array;

		jQuery.each(
			array,
			function(i, options) {
				var id = options.select;
				var select = jQuery('#' + id);
				var selectData = options.selectData;

				var prevSelectVal = null;

				if (i > 0) {
					prevSelectVal = array[i - 1].selectVal;
				}

				selectData(
					function(list) {
						instance._updateSelect(i, list);
					},
					prevSelectVal
				);

				select.attr('name', id);

				select.bind(
					'change',
					function() {
						instance._callSelectData(i);
					}
				);
			}
		);
	},

	_callSelectData: function(i) {
		var instance = this;

		var array = instance.array;

		if ((i + 1) < array.length) {
			var curSelect = jQuery('#' + array[i].select);
			var nextSelectData = array[i + 1].selectData;

			nextSelectData(
				function(list) {
					instance._updateSelect(i + 1, list);
				},
				curSelect.val()
			);
		}
	},

	_updateSelect: function(i, list) {
		var instance = this;

		var options = instance.array[i];

		var select = jQuery('#' + options.select);
		var selectId = options.selectId;
		var selectDesc = options.selectDesc;
		var selectVal = options.selectVal;
		var selectNullable = options.selectNullable || true;

		var selectOptions = [];

		if (selectNullable) {
			selectOptions.push('<option value="0"></option>');
		}

		jQuery.each(
			list,
			function(i, obj) {
				var key = obj[selectId];
				var value = obj[selectDesc];

				selectOptions.push('<option value="' + key + '">' + value + '</option>');
			}
		);

		selectOptions = selectOptions.join('');

		select.html(selectOptions);
		select.find('option[@value=' + selectVal + ']').attr('selected', 'selected');

		if (Liferay.Browser.is_ie) {
			select.css('width', 'auto');
		}
	}
});
var LayoutConfiguration = {
	categories: [],
	portlets: [],
	showTimer: 0,

	init: function() {
		var instance = this;

		var menu = jQuery('#portal_add_content');

		instance.menu = menu;

		if (menu.length) {
			instance.portlets = menu.find('.lfr-portlet-item');
			instance.categories = menu.find('.lfr-content-category');
			instance.categoryContainers = menu.find('.lfr-add-content');

			var searchField = jQuery('#layout_configuration_content');
				searchField.liveSearch(
					{
						list: instance.portlets,
						data: function() {
							return jQuery(this).attr('id').toLowerCase();
						},
						show: function() {
							var portlet = jQuery(this);

							portlet.show();
							portlet.parents('.lfr-content-category').addClass('visible').removeClass('hidden').show();
							portlet.parents('.lfr-add-content').addClass('expanded').removeClass('collapsed').show();
						},
						hide: function() {
							var portlet = jQuery(this);
							portlet.hide();
						}
					}
				);

				searchField.liveSearch(
					{
						list: instance.categoryContainers,
						data: function() {
							return jQuery(this).attr('id').toLowerCase();
						},
						after: function() {
							if (!this.term) {
								instance.categories.addClass('hidden').removeClass('visible').css('display', '');
								instance.categoryContainers.addClass('collapsed').removeClass('expanded').css('display', '');
								instance.portlets.css('display', '');
							}

							if (this.term == "*") {
								instance.categories.addClass('visible').removeClass('hidden');
								instance.categoryContainers.addClass('expanded').removeClass('collapsed');
								instance.portlets.show();
							}
						},
						exclude: function() {
							var categoryContent = jQuery('.lfr-content-category', this);

							var totalVisibleChildren = categoryContent.find('> div:visible').length;

							return totalVisibleChildren > 0;
						}
					}
				);
		}
	},

	toggle: function(ppid) {
		var instance = this;

		var plid = themeDisplay.getPlid();
		var doAsUserId = themeDisplay.getDoAsUserIdEncoded();

		if (!instance.menu) {
			var url = themeDisplay.getPathMain() + '/portal/render_portlet';

			var popupWidth = 250;
			var body = jQuery('body');

			body.addClass('lfr-has-sidebar');

			instance._dialog = Liferay.Popup(
				{
					width: popupWidth,
					message: '<div class="loading-animation" />',
					position: [5,5],
					resizable: false,
					title: Liferay.Language.get("add-application"),
					onClose: function() {
						instance.menu = null;
						body.removeClass('lfr-has-sidebar');
					}
				}
			);

			jQuery.ajax(
				{
					url: url,
					data: {
						p_l_id: plid,
						p_p_id: ppid,
						p_p_state: 'exclusive',
						doAsUserId: doAsUserId
					},
					success: function(message) {
						instance._dialog.html(message);
						instance._loadContent();
					}
				}
			);
		}
	},

	_addPortlet: function(portlet, options) {
		var instance = this;

		var portletMetaData = instance._getPortletMetaData(portlet);

		if (!portletMetaData.portletUsed) {
			var plid = portletMetaData.plid;
			var portletId = portletMetaData.portletId;
			var isInstanceable = portletMetaData.instanceable;

			if (!isInstanceable) {
				portlet.addClass('lfr-portlet-used');
				portlet.draggable('disable');
			}

			var placeHolder = jQuery('<div class="loading-animation" />');
			var onComplete = null;
			var beforePortletLoaded = null;

			if (options) {
				var item = options.item;

				options.placeHolder = placeHolder[0];
				onComplete = options.onComplete;
				beforePortletLoaded = options.beforePortletLoaded;

				item.after(placeHolder);
				item.remove();
			}
			else {
				if (instance._sortColumns) {
					instance._sortColumns.filter(':first').prepend(placeHolder);
				}
			}

			var portletOptions = {
				beforePortletLoaded: beforePortletLoaded,
				onComplete: onComplete,
				plid: plid,
				portletId: portletId,
				placeHolder: placeHolder
			}

			var portletPosition = Liferay.Portlet.add(portletOptions);

			instance._loadPortletFiles(portletMetaData);
		}
	},

	_getPortletMetaData: function(portlet) {
		var instance = this;

		var portletMetaData = portlet._LFR_portletMetaData;

		if (!portletMetaData) {
			var instanceable = (portlet.attr('instanceable') == 'true');
			var plid = portlet.attr('plid');
			var portletId = portlet.attr('portletId');
			var portletUsed = portlet.is('.lfr-portlet-used');
			var headerPortalCssPaths = (portlet.attr('headerPortalCssPaths') || '').split(',');
            var headerPortletCssPaths = (portlet.attr('headerPortletCssPaths') || '').split(',');
			var footerPortalCssPaths = (portlet.attr('footerPortalCssPaths') || '').split(',');
			var footerPortletCssPaths = (portlet.attr('footerPortletCssPaths') || '').split(',');

			portletMetaData = {
				instanceable: instanceable,
				plid: plid,
				portletId: portletId,
				portletPaths: {
					footer: footerPortletCssPaths,
					header: headerPortletCssPaths
				},
				portalPaths: {
					footer: footerPortalCssPaths,
					header: headerPortalCssPaths
				},
				portletUsed: portletUsed
			}

			portlet._LFR_portletMetaData = portletMetaData;
		}

		return portletMetaData;
	},

	_loadContent: function() {
		var instance = this;

		instance.init();

		Liferay.Util.addInputType();

		Liferay.bind('closePortlet', instance._onPortletClose, instance);

		instance._portletItems = instance._dialog.find('div.lfr-portlet-item');
		var portlets = instance._portletItems;

		portlets.find('a').click(
			function(event) {
				var link = jQuery(this);
				var portlet = link.parents('.lfr-portlet-item:first');

				instance._addPortlet(portlet);
			}
		);

		var zIndex = instance._dialog.parents('.ui-dialog').css('z-index');

		instance._helper = jQuery(Liferay.Template.PORTLET).css('z-index', zIndex + 10);
		instance._helper.addClass('ui-proxy generic-portlet not-intersecting');

		var type = 'Column';
		var appendTo = 'body';

		if (Liferay.Layout.isFreeForm) {
			appendTo = '#column-1';
			type = 'FreeForm';
		}
		else {

			// Let's make sure we have all the columns ready

			if (!instance._sortColumns || !instance._sortableInstance) {
				instance._sortColumns = Liferay.Layout.Columns.sortColumns;
				instance._sortableInstance = instance._sortColumns.data('sortable');
			}

			var sortColumns = instance._sortColumns;
			var sortableInstance = instance._sortableInstance;

			sortableInstance.refresh();

			if (!instance._eventsBound) {
				sortColumns.bind('sortreceive.sortable',
					function(event, ui) {
						if (ui.item.is('.lfr-portlet-item') && ui.sender.is('.lfr-portlet-item') && !sortableInstance.dragging) {
							var placeholder = ui.item;
							var portlet = ui.sender;

							var options = {
								item: placeholder
							};

							instance._addPortlet(portlet, options);

							placeholder.hide();
						}
					}
				);

				sortColumns.bind('sortactivate.sortable',
					function(event) {
						Liferay.Layout.Columns.startDragging();
						sortableInstance.refreshPositions(true);
					}
				);

				sortColumns.bind(
					'sortstart.sortable',
					function(event, ui) {
						if (ui.item.is('.lfr-portlet-item')) {
							ui.placeholder.css(
								{
									height: 200,
									width: 300
								}
							);
						}
					}
				);

				instance._eventsBound = true;
			}
		}

		instance._dragOptions = {
			appendTo: appendTo,
			connectToSortable: '.lfr-portlet-column',
			distance: 2,
			helper: function(event) {
				var helper = instance._helper.clone();
				var title = this.getAttribute('title');

				helper.find('.portlet-title').text(title);

				return helper[0];
			},
			start: function(event, ui) {
				if (instance['_on'+ type +'DragStart']) {
					instance['_on'+ type +'DragStart'](event, ui, this);
				}
			},
			drag: function(event, ui) {
				if (instance['_on'+ type +'Drag']) {
					instance['_on'+ type +'Drag'](event, ui, this);
				}
			},
			stop: function(event, ui) {
				if (instance['_on'+ type +'DragStop']) {
					instance['_on'+ type +'DragStop'](event, ui, this);
				}
			}
		};

		portlets.draggable(instance._dragOptions);

		portlets.filter('.lfr-portlet-used').draggable('disable');

		if (Liferay.Browser.is_ie) {
			portlets.hover(
				function() {
					this.className += ' over';
				},
				function() {
					this.className = this.className.replace('over', '');
				}
			);
		}

		jQuery('.lfr-add-content > h2').click(
			function() {
				var heading = jQuery(this).parent();
				var category = heading.find('> .lfr-content-category');

				category.toggleClass('hidden').toggleClass('visible');
				heading.toggleClass('collapsed').toggleClass('expanded');
			}
		);
	},

	_loadPortletFiles: function(portletMetaData) {
		var instance = this;

		var headerPortalCssPaths = portletMetaData.portalPaths.header;
		var footerPortalCssPaths = portletMetaData.portalPaths.footer;
		var headerPortletCssPaths = portletMetaData.portletPaths.header;
		var footerPortletCssPaths = portletMetaData.portletPaths.footer;

		var head = jQuery('head');
		var docBody = jQuery(document.body);

		var headerCSS = headerPortalCssPaths.concat(headerPortletCssPaths);
		var footerCSS = footerPortalCssPaths.concat(footerPortletCssPaths);

		jQuery.each(
			headerCSS,
			function(i, n) {
				head.prepend('<link href="' + this + '" rel="stylesheet" type="text/css" />');
			}
		);

		if (Liferay.Browser.is_ie) {
			jQuery('body link').appendTo('head');

			jQuery('link.lfr-css-file').each(
				function(i) {
					document.createStyleSheet(this.href);
				}
			);
		}

		jQuery.each(
			footerCSS,
			function(i, n) {
				docBody.append('<link href="' + this + '" rel="stylesheet" type="text/css" />');
			}
		);
	},

	_onColumnDragStop: function(event, ui, obj) {
		var instance = this;

		Liferay.Layout.Columns.stopDragging();
	},

	_onFreeFormDragStart: function(event, ui, obj) {
		var instance = this;

		ui.helper.removeClass('not-intersecting');
	},

	_onFreeFormDragStop: function(event, ui, obj) {
		var instance = this;

		var portlet = jQuery(obj);
		var helper = ui.helper;
		var position = ui.position;

		var dimensions = {
			height: ui.helper.height(),
			position: 'absolute',
			width: ui.helper.width()
		};

		var options = {
			beforePortletLoaded: function(placeHolder) {
				placeHolder = jQuery(placeHolder);
				placeHolder.css(position);
				placeHolder.css(dimensions);
			},
			item: helper,
			onComplete: function(portlet, portletId) {
				jQuery(portlet).css(position);
				Liferay.Layout.FreeForm._moveToTop(portlet);
				Liferay.Layout.FreeForm._savePosition(portlet);
			}
		};

		instance._addPortlet(portlet, options);
	},

	_onPortletClose: function(event, portletData) {
		var instance = this;

		var popup = jQuery('#portal_add_content');
		var item = popup.find('.lfr-portlet-item[@plid=' + portletData.plid + '][@portletId=' + portletData.portletId + '][@instanceable=false]');

		if (item.is('.lfr-portlet-used')) {
			item.removeClass('lfr-portlet-used');
			item.draggable('enable');
		}
	}
};
Liferay.LayoutExporter = {
	all: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj.checked) {
			jQuery(pane).hide();

			if (!publish) {
				jQuery('#publishBtn').show();
				jQuery('#selectBtn').hide();
			}
			else {
				jQuery('#changeBtn').hide();
			}
		}
	},

	details: function(options) {
		options = options || {};

		var toggle = options.toggle;
		var detail = options.detail;

		var img = jQuery(toggle)[0];

		if (jQuery(detail).css('display') == 'none') {
			jQuery(detail).slideDown('normal');
			img.src = Liferay.LayoutExporter.icons.minus;
		}
		else {
			jQuery(detail).slideUp('normal');
			img.src = Liferay.LayoutExporter.icons.plus;
		}
	},

	icons: {
		minus: themeDisplay.getPathThemeImages() + '/arrows/01_minus.png',
		plus: themeDisplay.getPathThemeImages() + '/arrows/01_plus.png'
	},

	proposeLayout: function(options) {
		options = options || {};

		var url = options.url;
		var namespace = options.namespace;
		var reviewers = options.reviewers;
		var title = options.title;

		var contents =
			"<div>" +
				"<form action='" + url + "' method='post'>" +
					"<textarea name='" + namespace + "description' style='height: 100px; width: 284px;'></textarea><br /><br />";

		if (reviewers.length > 0) {
			contents += Liferay.Language.get('reviewer') + " <select name='" + namespace + "reviewUserId'>";

			for (var i = 0; i < reviewers.length; i++) {
				contents += "<option value='" + reviewers[i].userId + "'>" + reviewers[i].fullName + "</option>";
			}

			contents += "</select><br /><br />";
		}

		contents +=
					"<input type='submit' value='" + Liferay.Language.get('proceed') + "' />" +
					"<input type='button' value='" + Liferay.Language.get('cancel') + "' onClick='Liferay.Popup.close(this);' />" +
				"</form>" +
			"</div>";

		Liferay.Popup({
			'title': title,
			message: contents,
			noCenter: false,
			modal: true,
			width: 300
		});
	},

	publishToLive: function(options) {
		options = options || {};

		var messageId = options.messageId;
		var url = options.url;
		var title = options.title;

		if (!title) {
			title = Liferay.Language.get(messageId);
		}

		var exportLayoutsPopup = Liferay.Popup(
			{
				title: title,
				modal: true,
				width: 600,
				overflow: 'auto',
				messageId: messageId
			}
		);

		jQuery.ajax(
			{
				url: url,
				success: function(response) {
					jQuery(exportLayoutsPopup).html(response);
				}
			}
		);
	},

	selected: function(options) {
		options = options || {};

		var pane = options.pane;
		var obj = options.obj;
		var publish = options.publish;

		if (obj.checked) {
			jQuery(pane).show();

			if (!publish) {
				jQuery('#publishBtn').hide();
				jQuery('#selectBtn').show();
			}
			else {
				jQuery('#changeBtn').show();
			}
		}
	}
};
Liferay.Menu = new Class({
	initialize: function(options) {
		var instance = this;

		instance._button = jQuery(options.button, options.context || document);
		instance._menu = instance._button.find('ul:first');
		instance._trigger = instance._button.find(options.trigger);

		if (instance._menu.length) {
			instance._run();
		}
	},

	_run: function() {
		var instance = this;

		var lastLi = instance._trigger.find('ul:first li:last-child');

		lastLi.addClass('last');

		var off = function(event) {
			instance._button.removeClass('visible');
		}

		var on = function(event) {
			var trigger = jQuery(this);

			var parent = trigger.parent();

			if (parent.is('.visible')) {
				parent.removeClass('visible');
			}
			else {
				instance._button.removeClass('visible');

				parent.addClass('visible');
			}

			jQuery(document).unbind('click.liferay').one(
				'click.liferay',
				off
			);

			var originalTarget = jQuery(event.originalTarget || event.srcElement);

			if (!originalTarget.is('a') && !originalTarget.is('img')) {
				return false;
			}
		};

		instance._trigger.unbind('click.liferay').bind('click.liferay', on);
	}
});
Liferay.Notice = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * content {string}: The content of the toolbar.
	 *
	 * Optional
	 * closeText {string}: Use for the "close" button. Set to false to not have a close button.
	 * toggleText {object}: The text to use for the "hide" and "show" button. Set to false to not have a hide button.
	 * noticeClass {string}: A class to add to the notice toolbar.
	 * type {string}: Either 'notice' or 'warning', depending on the type of the toolbar. Defaults to notice.
	 *
	 * Callbacks
	 * onClose {function}: Called when the toolbar is closed.
	 */

	initialize: function(options) {
		var instance = this;
		options = options || {};
		instance._noticeType = options.type || 'notice';
		instance._noticeClass = 'popup-alert-notice';
		instance._useCloseButton = true;
		instance._onClose = options.onClose;
		instance._closeText = options.closeText;
		instance._body = jQuery('body');

		instance._useToggleButton = false;
		instance._hideText = '';
		instance._showText = '';

		if (options.toggleText !== false) {
			instance.toggleText = jQuery.extend(
				{
					hide: null,
					show: null
				},
			options.toggleText);

			instance._useToggleButton = true;
		}

		if (instance._noticeType == 'warning') {
			instance._noticeClass = 'popup-alert-warning';
		}

		if (options.noticeClass) {
			instance._noticeClass += ' ' + options.noticeClass;
		}

		instance._content = options.content || '';

		instance._createHTML();

		return instance._notice;
	},

	setClosing: function() {
		var instance = this;

		var staticAlerts = jQuery('.popup-alert-notice, .popup-alert-warning').not('[@dynamic=true]');

		if (staticAlerts.length) {
			instance._useCloseButton = true;
			instance._addCloseButton(staticAlerts);

			if (!instance._body) {
				instance._body = jQuery('body');
			}

			instance._body.addClass('has-alerts')
		}
	},

	_createHTML: function() {
		var instance = this;

		var notice = jQuery('<div class="' + instance._noticeClass + '" dynamic="true"><div class="popup-alert-content"></div></div>');

		notice.html(instance._content);

		instance._addCloseButton(notice);
		instance._addToggleButton(notice);

		instance._body.append(notice);
		instance._body.addClass('has-alerts');

		instance._notice = notice;
	},

	_addCloseButton: function(notice) {
		var instance = this;

		if (instance._closeText !== false) {
			instance._closeText = instance._closeText || Liferay.Language.get('close');
		}
		else {
			instance._useCloseButton = false;
			instance._closeText = '';
		}

		if (instance._useCloseButton) {
			var html = '<input class="submit popup-alert-close" type="submit" value="' + instance._closeText + '" />';

			notice.append(html);

			var closeButton = notice.find('.popup-alert-close');
			closeButton.click(
				function() {
					notice.slideUp('normal',
						function() {
							notice.remove();
							instance._body.removeClass('has-alerts');
						}
					);

					if (instance._onClose) {
						instance._onClose();
					}
				}
			);
		}
	},

	_addToggleButton: function(notice) {
		var instance = this;

		if (instance._useToggleButton) {
			instance._hideText = instance._toggleText.hide || Liferay.Language.get('hide');
			instance._showText = instance._toggleText.show || Liferay.Language.get('show');

			var toggleButton = jQuery('<a class="toggle-button" href="javascript:;"><span>' + instance._hideText + '</span></a>');
			var toggleSpan = toggleButton.find('span');
			var height = 0;

			toggleButton.toggle(
				function() {
					notice.slideUp();
					toggleSpan.text(instance._showText);
				},
				function() {
					notice.slideDown();
					toggleSpan.text(instance._hideText);
				}
			);

			notice.append(toggleButton);
		}
	}
});
Liferay.Navigation = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * hasPermission {boolean}: Whether the current user has permission to modify the navigation
	 * layoutIds {array}: The displayable layout ids.
	 * navBlock {string|object}: A jQuery selector or DOM element of the navigation.
	 */

	initialize: function(options) {
		var instance = this;

		instance.options = options;

		instance._navBlock = jQuery(instance.options.navBlock);

		instance._hasPermission = instance.options.hasPermission;
		instance._isModifiable = instance._navBlock.is('.modify-pages');
		instance._isSortable = instance._navBlock.is('.sort-pages') && instance._hasPermission;
		instance._isUseHandle = instance._navBlock.is('.use-handle');

		instance._updateURL = themeDisplay.getPathMain() + '/layout_management/update_page';

		var items = instance._navBlock.find('> ul > li');

		items.each(
			function(i) {
				this._LFR_layoutId = instance.options.layoutIds[i];
			}
		);

		instance._makeAddable();
		instance._makeDeletable();
		instance._makeSortable();
		instance._makeEditable();

		Liferay.bind('tree', instance._treeCallback, instance);
	},

	_addPage: function(event, obj) {
		var instance = this;

		var navItem = instance._navBlock;
		var addBlock = jQuery('<li>' + instance._enterPage + '</li>');

		var blockInput = addBlock.find('input');

		navItem.find('ul:first').append(addBlock);

		var savePage = addBlock.find('.save-page');
		var cancelPage = addBlock.find('.cancel-page');
		var currentInput = addBlock.find('.enter-page input');

		var pageParents = jQuery(document);

		var pageBlur = function(internalEvent) {
			var currentEl = jQuery(internalEvent.target);
			var liParent = currentEl.parents('ul:eq(0)');

			if ((liParent.length == 0) && !currentEl.is('li') && !currentEl.parents('#add-page').length) {
				cancelPage.trigger('click');
			}
		};

		pageParents.bind('click.liferay', pageBlur);

		cancelPage.click(
			function(event) {
				instance._cancelAddingPage(event, addBlock);
				pageParents.unbind('click.liferay', pageBlur);
			}
		);

		savePage.click(
			function(event) {
				instance._savePage(event, this);
				pageParents.unbind('click.liferay', pageBlur);
			}
		);

		currentInput.keyup(
			function(event) {
				if (event.keyCode == 13) {
					savePage.trigger('click');
				}
				else if (event.keyCode == 27) {
					cancelPage.trigger('click');
				}
				else {
					return;
				}

				pageParents.unbind('click.liferay', pageBlur);
			}
		);
	},

	_cancelAddingPage: function(event, obj) {
		var instance = this;
		obj.remove();
	},

	_cancelPage: function(event, obj, oldName) {
		var navItem = null;

		if (oldName) {
			navItem = jQuery(obj).parents('li');

			var enterPage = navItem.find('.enter-page');

			enterPage.prev().show();
			enterPage.remove();
		}
		else {
			navItem = jQuery(this).parents('li');

			navItem.remove();
		}
	},

	_deleteButton: function(obj) {
		var instance = this;

		obj.append('<span class="delete-tab">X</span>');

		var deleteTab = obj.find('.delete-tab');

		deleteTab.click(
			function(event) {
				instance._removePage(this);
			}
		);

		deleteTab.hide();

		obj.hover(
			function() {
				jQuery(this).find('.delete-tab').fadeIn('fast');
			},
			function() {
				jQuery(this).find('.delete-tab').fadeOut('fast');
			}
		);
	},

	_makeAddable: function() {
		var instance = this;

		if (instance._isModifiable) {
			var navList = instance._navBlock.find('ul:first');

			instance._enterPage =
				'<div class="enter-page">' +
				'<input class="lfr-auto-focus" type="text" name="new_page" value="" class="text" />' +
				'<a class="cancel-page" href="javascript: ;"></a>' +
				'<a class="save-page" href="javascript: ;">' + Liferay.Language.get('save') + '</a>' +
				'</div>';

			if (instance._hasPermission) {
				navList.after(
					'<div id="add-page">' +
					'<a href="javascript:;">' +
					'<span>' + Liferay.Language.get('add-page') + '</span>' +
					'</a>' +
					'</div>');

				var addPage = navList.parent().find('#add-page a');

				addPage.click(
					function(event) {
						instance._addPage(event, this);
					}
				);
			}
		}
	},

	_makeDeletable: function() {
		var instance = this;

		if (instance._isModifiable && instance._hasPermission) {
			var navItems = instance._navBlock.find('li').not('.selected');

			instance._deleteButton(navItems);
		}
	},

	_makeEditable: function() {
		var instance = this;

		if (instance._isModifiable) {
			var currentItem = instance._navBlock.find('li.selected');
			var currentLink = currentItem.find('a');
			var currentSpan = currentLink.find('span');

			currentLink.click(
				function(event) {
					if (event.shiftKey) {
						return false;
					}
				}
			);

			var resetCursor = function() {
				currentSpan.css('cursor', 'pointer');
			};

			currentLink.hover(
				function(event) {
					if (!themeDisplay.isStateMaximized() || event.shiftKey) {
						currentSpan.css('cursor', 'text');
					}
				},
				resetCursor
			);

			currentSpan.click(
				function(event) {
					if (themeDisplay.isStateMaximized() && !event.shiftKey) {
						return;
					}

					var span = jQuery(this);
					var text = span.text();

					span.parent().hide();
					span.parent().after(instance._enterPage);

					var enterPage = span.parent().next();

					var pageParents = enterPage.parents();

					var enterPageInput = enterPage.find('input');

					var pageBlur = function(event) {
						event.stopPropagation();

						if (!jQuery(this).is('li')) {
							cancelPage.trigger('click');
						}

						return false;
					};

					enterPageInput.val(text);

					enterPageInput.trigger('select');

					var savePage = enterPage.find('.save-page');

					savePage.click(
						function(event) {
							instance._savePage(event, this, text);
							pageParents.unbind('blur.liferay', pageBlur);
							pageParents.unbind('click.liferay', pageBlur);
						}
					);

					var cancelPage = enterPage.find('.cancel-page');

					cancelPage.hide();

					cancelPage.click(
						function(event) {
							instance._cancelPage(event, this, text);
							pageParents.unbind('blur.liferay', pageBlur);
							pageParents.unbind('click.liferay', pageBlur);
						}
					);

					enterPageInput.keyup(
						function(event) {
							if (event.keyCode == 13) {
								savePage.trigger('click');
								pageParents.unbind('blur.liferay', pageBlur);
								pageParents.unbind('click.liferay', pageBlur);
							}
							else if (event.keyCode == 27) {
								cancelPage.trigger('click');
								pageParents.unbind('blur.liferay', pageBlur);
								pageParents.unbind('click.liferay', pageBlur);
							}
						}
					);

					pageParents.bind('click.liferay', pageBlur);

					resetCursor();

					return false;
				}
			);
		}
	},

	_makeSortable: function() {
		var instance = this;

		var navBlock = instance._navBlock;
		var navList = navBlock.find('ul:first');

		if (instance._isSortable) {
			var items = navList.find('li');
			var anchors = items.find('a');

			if (instance._isUseHandle) {
				items.append('<span class="sort-handle">+</span>');
			}
			else {
				anchors.css('cursor', 'move');
				anchors.find('span').css('cursor', 'pointer');
			}

			items.addClass('sortable-item');

			instance.sortable = navList.sortable(
				{
					items: '.sortable-item',
					placeholder: 'navigation-sort-helper',
					handle: (instance._isUseHandle ? '.sort-handle' : 'a'),
					opacity: 0.8,
					revert:	false,
					tolerance: 'pointer',
					distance: 5,
					stop: function(event, ui) {
						instance._saveSortables(ui.item[0]);

						Liferay.trigger('navigation', 
							{
								item: ui.item[0],
								type: 'sort'
							}
						);
					}
				}
			);
		}
	},

	_removePage: function(obj) {
		var instance = this;

		var tab = jQuery(obj).parents('li');
		var tabText = tab.find('a span').html();

		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-page'))) {
			var data = {
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				cmd: 'delete',
				groupId: themeDisplay.getGroupId(),
				privateLayout: themeDisplay.isPrivateLayout(),
				layoutId: tab[0]._LFR_layoutId
			};

			jQuery.ajax(
				{
					data: data,
					success: function() {
						Liferay.trigger('navigation', 
							{
								item: tab,
								type: 'delete'
							}
						);

						tab.remove();
					},
					url: instance._updateURL
				}
			);
		}
	},

	_savePage: function(event, obj, oldName) {
		var instance = this;

		if ((event.type == 'keyup') && (event.keyCode !== 13)) {
			return;
		}

		var data = null;
		var onSuccess = null;

		var newNavItem = jQuery(obj).parents('li');
		var name = newNavItem.find('input').val();
		var enterPage = newNavItem.find('.enter-page');

		if (oldName) {

			// Updating an existing page

			if (name != oldName) {
				data = {
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					cmd: 'name',
					groupId: themeDisplay.getGroupId(),
					privateLayout: themeDisplay.isPrivateLayout(),
					layoutId: themeDisplay.getLayoutId(),
					name: name,
					languageId: themeDisplay.getLanguageId()
				};

				onSuccess = function(data) {
					var currentTab = enterPage.prev();
					var currentSpan = currentTab.find('span');

					currentSpan.text(name);
					currentTab.show();

					enterPage.remove();

					var oldTitle = jQuery(document).attr('title');

					var regex = new RegExp(oldName, 'g');

					newTitle = oldTitle.replace(regex, name);

					jQuery(document).attr('title', newTitle);
				}
			}
			else {

				// The new name is the same as the old one

				var currentTab = enterPage.prev();

				currentTab.show();
				enterPage.remove();

				return false;
			}
		}
		else {

			// Adding a new page

			data = {
				mainPath: themeDisplay.getPathMain(),
				doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
				cmd: 'add',
				groupId: themeDisplay.getGroupId(),
				privateLayout: themeDisplay.isPrivateLayout(),
				parentLayoutId: themeDisplay.getParentLayoutId(),
				name: name
			};

			onSuccess = function(data) {
				var newTab = jQuery('<a href="' + data.url + '"><span>' + name + '</span></a>');

				if (instance._isUseHandle) {
					enterPage.before('<span class="sort-handle">+</span>');
				}
				else {
					newTab.css('cursor', 'move');
				}

				newNavItem[0]._LFR_layoutId = data.layoutId;

				enterPage.before(newTab);
				enterPage.remove();

				newNavItem.addClass('sortable-item');

				instance.sortable.sortable('refresh');
				instance._deleteButton(newNavItem);

				Liferay.trigger('navigation',
					{
						item: newNavItem,
						type: 'add'
					}
				)
			}
		}

		jQuery.ajax(
			{
				data: data,
				dataType: 'json',
				success: onSuccess,
				url: instance._updateURL
			}
		);
	},

	_saveSortables: function(obj) {
		var instance = this;

		var tabs = jQuery('li', instance._navBlock);

		var data = {
			doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
			cmd: 'priority',
			groupId: themeDisplay.getGroupId(),
			privateLayout: themeDisplay.isPrivateLayout(),
			layoutId: obj._LFR_layoutId,
			priority: tabs.index(obj)
		};

		jQuery.ajax(
			{
				data: data,
				url: instance._updateURL
			}
		);
	},

	_treeCallback: function(event, data) {
		var instance = this;

		var navigation = instance._navBlock.find('> ul');
		var droppedItem = jQuery(data.droppedItem);
		var dropTarget = jQuery(data.dropTarget);

		if (instance._isSortable) {
			var liItems = navigation.find('> li');

			var tree = droppedItem.parent();
			var droppedName = droppedItem.find('span:first').text();
			var newParent = dropTarget.parents('li:first');

			var liChild = liItems.find('span').not('.delete-tab');

			liChild = liChild.filter(
				function() {
					var currentItem = jQuery(this);

					if (currentItem.text() == droppedName) {
						return true;
					}
					else {
						return false;
					}
				}
			);

			var treeItems = tree.find('> li');

			var newIndex = treeItems.index(droppedItem);

			if (liChild.length > 0) {
				var newSibling = liItems.eq(newIndex);
				var parentLi = liChild.parents('li:first');

				if (!newParent.is('.tree-item')) {
					newSibling.after(parentLi);

					if (parentLi.is(':hidden')) {
						parentLi.show();
					}
				}
				else {

					//TODO: add parsing to move child elements around by their layoutId

					parentLi.hide();
				}
			}
			else if (!newParent.is('.tree-item')) {
				var newTab = liItems.slice(0, 1).clone();

				newTab.removeClass('selected');
				newTab.find('.child-menu').remove();

				var newTabLink = newTab.find('a span');

				newTabLink.text(droppedName);
				newTabLink.css('cursor', 'pointer');

				liItems.parent().append(newTab);
			}
		}
	},

	_enterPage: '',
	_updateURL: ''
});
Liferay.Session = {
	autoExtend: false,
	sessionTimeout: 0,
	sessionTimeoutWarning: 0,
	redirectOnExpire: false,

	init: function(params) {
		var instance = this;

		params = params || {};
		instance.autoExtend = params.autoExtend || instance.autoExtend;

		instance._timeout = params.timeout || instance.sessionTimeout;
		instance._warning = params.timeoutWarning || instance.sessionTimeoutWarning;

		instance.sessionTimeout = instance._timeout * 60000;
		instance.sessionTimeoutWarning = instance._warning * 60000;
		instance._timeoutDiff = instance.sessionTimeout - instance.sessionTimeoutWarning;

		instance._currentTime = instance.sessionTimeoutWarning;

		instance.redirectOnExpire = params.redirectOnExpire || instance.redirectOnExpire;

		instance._cookieKey = 'LFR_SESSION_STATE_' + themeDisplay.getUserId();

		instance.banner = new jQuery;

		var urlBase = themeDisplay.getPathMain() + '/portal/';

		instance._sessionUrls = {
			expire: urlBase + 'expire_session',
			extend: urlBase + 'extend_session'
		};

		instance._stateCheck = setTimeout(
			function() {
				instance.checkState();
			},
			instance._timeoutDiff);

		var timeoutMinutes = instance._timeout;
		var timeLeft = instance._warning;

		instance._warningText = Liferay.Language.get('warning-your-session-will-expire', ['[$SPAN$]', timeoutMinutes]);
		instance._warningText = instance._warningText.replace(/\[\$SPAN\$\]/, '<span class="countdown-timer"></span>');

		instance._toggleText = {
			hide: Liferay.Language.get('hide'),
			show: Liferay.Language.get('show')
		};

		instance._expiredText = Liferay.Language.get('warning-your-session-has-expired');
		instance._extendText = Liferay.Language.get('extend');

		instance.setCookie();
	},

	checkState: function() {
		var instance = this;

		var currentTime = new Date().getTime();
		var sessionState = instance.getCookie();
		var newWaitTime = instance.sessionTimeoutWarning;
		var timeDiff = 0;

		clearTimeout(instance._stateCheck);
		if (sessionState == 'expired') {
			instance.expire();
		}
		else {
			timeDiff = currentTime - sessionState;

			if (!instance.autoExtend) {
				if ((timeDiff + 100) >= instance.sessionTimeoutWarning) {
						instance.warn();
				}
				else {
					newWaitTime = (instance.sessionTimeoutWarning - timeDiff) + 10000;
					instance._stateCheck = setTimeout(
						function() {
							instance.checkState();
						},
					newWaitTime);
				}
			}
			else {
				instance.extend();
			}
		}
	},

	getCookie: function() {
		var instance = this;

		return jQuery.cookie(instance._cookieKey) || 0;
	},

	expire: function() {
		var instance = this;

		document.title = instance._originalTitle;

		jQuery.ajax(
			{
				url: instance._sessionUrls.expire,
				success: function() {
					if (instance.redirectOnExpire) {
						location.href = themeDisplay.getURLHome();
					}
				}
			}
		);

		instance.setCookie('expired');
	},

	extend: function() {
		var instance = this;

		if (instance._countdownTimer) {
			clearInterval(instance._countdownTimer);
		}

		jQuery.ajax(
			{
				url: instance._sessionUrls.extend
			}
		);

		document.title = instance._originalTitle;

		instance._currentTime = instance.sessionTimeoutWarning;

		clearTimeout(instance._sessionExpired);

		if (instance._sessionWarning) {
			clearTimeout(instance._sessionWarning);
		}

		instance._sessionWarning = setTimeout(
			function() {
				if (!instance.autoExtend) {
					instance.warn();
				}
				else {
					instance.extend();
				}
			},
			instance._timeoutDiff
		);

		instance.setCookie();
	},

	setCookie: function(status) {
		var instance = this;

		var currentTime = new Date().getTime();
		jQuery.cookie(instance._cookieKey, status || currentTime);
	},

	warn: function() {
		var instance = this;

		instance.banner = new Liferay.Notice({
			content: instance._warningText,
			closeText: instance._extendText,
			onClose: function() {
				instance.extend();
			},
			toggleText: false
		});

		instance._counter();

		instance._sessionExpired = setTimeout(
			function() {
				instance.expire();
			},
			instance.sessionTimeoutWarning);
	},

	_counter: function() {
		var instance = this;

		var banner = instance.banner;
		if (banner.length) {
			instance._counterText = banner.find('.countdown-timer');
			instance._originalTitle = document.title;
			var interval = 1000;

			instance._counterText.text(instance._setTime());
			document.title = instance.banner.text();

			instance._countdownTimer = setInterval(
				function() {
					var time = instance._setTime();

					instance._currentTime = instance._currentTime - interval;

					if (instance._currentTime > 0) {
						instance._counterText.text(time);
						document.title = instance.banner.text();
					}
					else {
						instance.banner.html(instance._expiredText);
						instance.banner.toggleClass('popup-alert-notice').toggleClass('popup-alert-warning');

						if (instance._countdownTimer) {
							clearInterval(instance._countdownTimer);
						}
					}
				},
			interval
			);
		}
	},

	_formatNumber: function(num) {
		var instance = this;

		if (!Liferay.Util.isArray(num)) {
			if (num <= 9) {
				num = '0' + num;
			}
		}
		else {
			num = jQuery.map(num, instance._formatNumber);
		}
		return num;
	},

	_setTime: function() {
		var instance = this;

		var amount = instance._currentTime;

		if (amount <= 0) {

		}
		else {
			var days=0, hours=0, minutes=0, seconds=0, output='';

			// Remove the milliseconds
			amount = Math.floor(amount/1000);

			hours = Math.floor(amount/3600);
			amount = amount%3600;

			minutes = Math.floor(amount/60);
			amount = amount%60;

			seconds = Math.floor(amount);

			return instance._formatNumber([hours, minutes, seconds]).join(':');
		}
	},

	_banner: [],
	_currentTime: 0,
	_originalTitle: '',
	_sessionUrls: {},
	_timeout: 0,
	_timeoutDiff: 0,
	_warning: 0
};
Liferay.TagsSelector = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * instanceVar {string}: The instance variable for this class.
	 * hiddenInput {string}: The hidden input used to pass in the current tags.
	 * textInput {string}: The text input for users to add tags.
	 * summarySpan {string}: The summary span to show the current tags.
	 *
	 * Optional
	 * focus {boolean}: Whether the text input should be focused.
	 *
	 * Callbacks
	 * contentCallback {function}: Called to get suggested tags.
	 */

	initialize: function(options) {
		var instance = this;

		instance._curTags = [];

		instance.options = options;
		instance._ns = instance.options.instanceVar || '';
		instance._mainContainer = jQuery('<div class="lfr-tag-select-container"></div>');
		instance._container = jQuery('<div class="lfr-tag-container"></div>');

		var hiddenInput = jQuery('#' + options.hiddenInput);

		hiddenInput.attr('name', hiddenInput.attr('id'));

		var textInput = jQuery('#' + options.textInput);

		textInput.autocomplete(
			{
				source: instance._getTags,
				width: textInput.width() + 20,
				formatItem: function(row, i, max, term) {
					return row;
				},
				dataType: 'json',
				delay: 0,
				multiple: true,
				mutipleSeparator: ',',
				minChars: 1,
				hide: function(event, ui) {
					jQuery(this).removeClass('showing-list');
				},
				show: function(event, ui) {
					jQuery(this).addClass('showing-list');
					this._LFR_listShowing = true;
				},
				result: function(event, ui) {
					var caretPos = this.value.length;

					if (this.createTextRange) {
						var textRange = this.createTextRange();

						textRange.moveStart('character', caretPos);
						textRange.select();
					}
					else if (this.selectionStart) {
						this.selectionStart = caretPos;
						this.selectionEnd = caretPos;
					}
				}
			}
		);

		instance._popupVisible = false;

		instance._setupSelectTags();
		instance._setupSuggestions();

		var addTagButton = jQuery('#' + options.instanceVar + 'addTag');

		addTagButton.click(
			function() {
					var curTags = instance._curTags;
					var newTags = textInput.val().split(',');

					jQuery.each(
						newTags,
						function(i, n) {
							n = jQuery.trim(n);

							if (curTags.indexOf(n) == -1) {
								if (n != '') {
									curTags.push(n);

									if (instance._popupVisible) {
										jQuery('input[@type=checkbox][@value$=' + n + ']', instance.selectTagPopup).attr('checked', true);
									}
								}
							}
						}
					);

					curTags = curTags.sort();
					textInput.val('');

					instance._update();
				}
		);

		textInput.keypress(
			function(event) {
				if (event.keyCode == 13) {
					if (!this._LFR_listShowing) {
						addTagButton.trigger('click');
					}

					this._LFR_listShowing = null;

					return false;
				}
			}
		);

		if (options.focus) {
			textInput.focus();
		}

		if (options.curTags != '') {
			instance._curTags = options.curTags.split(',');

			instance._update();
		}

		Liferay.Util.actsAsAspect(window);

		window.before(
			'submitForm',
			function() {
				var val = jQuery.trim(textInput.val());

				if (val.length) {
					addTagButton.trigger('click');
				}
			}
		);
	},

	deleteTag: function(id) {
		var instance = this;

		var options = instance.options;
		var curTags = instance._curTags;

		jQuery('#' + instance._ns + 'CurTags' + id).remove();

		var value = curTags.splice(id, 1);

		if (instance._popupVisible) {
			jQuery('input[@type=checkbox][@value$=' + value + ']', instance.selectTagPopup).attr('checked', false);
		}

		instance._update();
	},

	_createPopup: function() {
		var instance = this;

		var ns = instance._ns;
		var container = instance._container;
		var mainContainer = instance._mainContainer;

		var saveBtn = jQuery('<input class="submit lfr-save-button" id="' + ns + 'saveButton" type="submit" value="' + Liferay.Language.get('save') + '" />');

		saveBtn.click(
			function() {
				instance._curTags = instance._curTags.length ? instance._curTags : [];

				container.find('input[@type=checkbox]').each(
					function() {
						var currentIndex = instance._curTags.indexOf(this.value);
						if (this.checked) {
							if (currentIndex == -1) {
								instance._curTags.push(this.value);
							}
						}
						else {
							if (currentIndex > -1) {
								instance._curTags.splice(currentIndex, 1);
							}
						}
					}
				);

				instance._update();
				Liferay.Popup.close(instance.selectTagPopup);
			}
		);

		mainContainer.append(container).append(saveBtn);

		if (!instance.selectTagPopup) {
			var popup = Liferay.Popup(
				{
					modal: false,
					position: 'center',
					width: 400,
					message: mainContainer[0],
					onClose: function() {
						instance._popupVisible = false;
						instance.selectTagPopup = null;
					}
				}
			);
			instance.selectTagPopup = popup;
		}
		instance._popupVisible = true;

		if (Liferay.Browser.is_ie) {
			jQuery('.lfr-label-text', popup).click(
				function() {
					var input = jQuery(this.previousSibling);
					var checkedState = !input.is(':checked');
					input.attr('checked', checkedState);
				}
			);
		}
	},

	_getTags: function(term) {
		var beginning = 0;
		var end = 20;

		var data = Liferay.Service.Tags.TagsEntry.searchAutocomplete(
			{
				groupId: themeDisplay.getGroupId(),
				name: "%" + term + "%",
				properties: "",
				begin: beginning,
				end: end
			}
		);

		return jQuery.map(
			data,
			function(row) {
				return {
					data: row.text,
					value: row.value,
					result: row.text
				}
			}
		);
	},

	_setupSelectTags: function() {
		var instance = this;

		var options = instance.options;
		var ns = instance._ns;

		var input = jQuery('#' + ns + 'selectTag');

		input.click(
			function() {
				instance._showSelectPopup();
			}
		);
	},

	_setupSuggestions: function() {
		var instance = this;

		var options = instance.options;
		var ns = instance._ns;

		var input = jQuery('#' + ns + 'suggestions');

		input.click(
			function() {
				instance._showSuggestionsPopup();
			}
		);
	},

	_showSelectPopup: function() {
		var instance = this;

		var options = instance.options;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;

		mainContainer.empty();
		container.empty();

		var categories = Liferay.Service.Tags.TagsProperty.getPropertyValues(
			{
				companyId: themeDisplay.getCompanyId(),
				key: "category"
			}
		);

		jQuery.each(
			categories,
			function(i, category) {
				var tags = Liferay.Service.Tags.TagsEntry.search(
					{
						companyId: themeDisplay.getCompanyId(),
						name: '%',
						properties: 'category:' + category.value
					}
				);

				var label = '';

				jQuery.each(
					tags,
					function(j, tag) {
						if (j == 0) {
							if (i > 0) {
								label += '</fieldset>';
							}
							label += '<fieldset><legend>' + category.value + '</legend>';
						}

						var checked = (instance._curTags.indexOf(tag.name) > -1) ? ' checked="checked"' : '';

						label +=
							'<label title="' + tag.name + '">' +
								'<input' + checked + ' type="checkbox" name="' + ns + 'input' + j + '" id="' + ns + 'input' + j + '" value="' + tag.name + '" />' +
								'<a class="lfr-label-text" href="javascript: ;">' + tag.name + '</a>' +
							'</label>';

					}
				);

				container.append(label);
			}
		);

		instance._createPopup();
	},

	_showSuggestionsPopup: function() {
		var instance = this;

		var options = instance.options;
		var ns = instance._ns;
		var mainContainer = instance._mainContainer;
		var container = instance._container;

		mainContainer.empty();
		container.empty();

		var context = '';

		if (options.contentCallback) {
			context = options.contentCallback();
		}

		var url =  "http://search.yahooapis.com/ContentAnalysisService/V1/termExtraction?appid=YahooDemo&output=json&context=" + escape(context);

		var label = '';

		jQuery.ajax(
			{
				url: themeDisplay.getPathMain() + "/portal/rest_proxy",
				data: {
					url: url
				},
				dataType: "json",
				success: function(obj) {
					label += '<fieldset><legend>' + Liferay.Language.get('suggestions') + '</legend>';

					jQuery.each(
						obj.ResultSet.Result,
						function(i, tag) {
							var checked = (instance._curTags.indexOf(tag) > -1) ? ' checked="checked"' : '';

							label +=
								'<label title="' + tag + '">' +
									'<input' + checked + ' type="checkbox" name="' + ns + 'input' + i + '" id="' + ns + 'input' + i + '" value="' + tag + '" />' +
									'<a class="lfr-label-text" href="javascript: ;">' + tag + '</a>' +
								'</label>';
						}
					)

					label += '</fieldset>';

					container.append(label);
				}
			}
		);

		instance._createPopup();
	},

	_update: function() {
		var instance = this;

		instance._updateHiddenInput();
		instance._updateSummarySpan();
	},

	_updateHiddenInput: function() {
		var instance = this;

		var options = instance.options;
		var curTags = instance._curTags;

		var hiddenInput = jQuery('#' + options.hiddenInput);

		hiddenInput.val(curTags.join(','));
	},

	_updateSummarySpan: function() {
		var instance = this;

		var options = instance.options;
		var curTags = instance._curTags;

		var html = '';

		jQuery(curTags).each(
			function(i, curTag) {
				html += '<span class="ui-tag" id="' + instance._ns + 'CurTags' + i + '">';
				html += curTag;
				html += '<a class="ui-tag-delete" href="javascript: ' + instance._ns + '.deleteTag(' + i + ');"><span>x</span></a>';
				html += '</span>';
			}
		);

		var tagsSummary = jQuery('#' + options.summarySpan);

		if (curTags.length) {
			tagsSummary.removeClass('empty');
		}
		else {
			tagsSummary.addClass('empty');
		}

		tagsSummary.html(html);
	}
});
Liferay.Upload = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * allowedFileTypes {string}: A comma-seperated list of allowable filetypes.
	 * container {string|object}: The container where the uploader will be placed.
	 * maxFileSize {number}: The maximum file size that can be uploaded.
	 * uploadFile {string}: The URL to where the file will be uploaded.
	 *
	 * Optional
	 * fallbackContainer {string|object}: A jQuery selector or DOM element of the container holding a fallback (in case flash is not supported).
	 * namespace {string}: A unique string so that the global callback methods don't collide.
	 * fileDescription {string}: A string describing what files can be uploaded.
	 *
	 * Callbacks
	 * onFileComplete {function}: Called whenever a file is completely uploaded.
	 * onUploadsComplete {function}: Called when all files are finished being uploaded, and is passed no arguments.
	 * onUploadProgress {function}: Called during upload, and is also passed in the number of bytes loaded as it's second argument.
	 * onUploadError {function}: Called when an error in the upload occurs. Gets passed the error number as it's only argument.
	 */

	initialize: function(options) {
		var instance = this;

		options = options || {};

		instance._container = jQuery(options.container);
		instance._fallbackContainer = jQuery(options.fallbackContainer || []);
		instance._namespaceId = options.namespace || '_liferay_pns_' + Liferay.Util.randomInt() + '_';
		instance._maxFileSize = options.maxFileSize || 0;
		instance._allowedFileTypes = options.allowedFileTypes;
		instance._uploadFile = options.uploadFile;

		instance._onFileComplete = options.onFileComplete;
		instance._onUploadsComplete = options.onUploadsComplete;
		instance._onUploadProgress = options.onUploadProgress;
		instance._onUploadError = options.onUploadError;

		instance._classicUploaderParam = 'uploader=classic';
		instance._newUploaderParam = 'uploader=new';

		instance._queueCancelled = false;

		instance._flashVersion = deconcept.SWFObjectUtil.getPlayerVersion().major;

		// Check for an override via the query string

		var loc = location.href;

		if (loc.indexOf(instance._classicUploaderParam) > -1 && instance._fallbackContainer.length) {
			instance._fallbackContainer.show();

			instance._setupIframe();

			return;
		}

		// Language keys

		instance._browseText = Liferay.Language.get('browse-you-can-select-multiple-files');
		instance._cancelUploadsText = Liferay.Language.get('cancel-all-uploads');
		instance._cancelFileText = Liferay.Language.get('cancel-upload');
		instance._clearRecentUploadsText = Liferay.Language.get('clear-recent-uploads');
		instance._fileListPendingText = Liferay.Language.get('x-files-ready-to-be-uploaded', '0');
		instance._fileListText = Liferay.Language.get('file-list');
		instance._fileTypesDescriptionText = options.fileDescription || instance._allowedFileTypes;
		instance._uploadsCompleteText = Liferay.Language.get('all-uploads-complete');
		instance._uploadStatusText = Liferay.Language.get('uploading-file-x-of-x', ['[$POS$]','[$TOTAL$]']);
		instance._uploadFilesText = Liferay.Language.get('upload-files');

		if (instance._fallbackContainer.length) {
			instance._useFallbackText = Liferay.Language.get('use-the-classic-uploader');
			instance._useNewUploaderText = Liferay.Language.get('use-the-new-uploader');
		}

		if (instance._flashVersion < 9 && instance._fallbackContainer.length) {
			instance._fallbackContainer.show();

			instance._setupIframe();

			return;
		}

		instance._setupCallbacks();
		instance._setupUploader();
	},

	cancelUploads: function() {
		var instance = this;

		var stats = instance._getStats();

		while (stats.files_queued > 0) {
			instance._uploader.cancelUpload();

			stats = instance._getStats();
		}

		if (stats.in_progress === 0) {
			instance._queueCancelled = false;
		}

		instance._uploadButton.hide();
		instance._cancelButton.hide();
	},

	fileAdded: function(file) {
		var instance = this;

		var listingFiles = instance._fileList;
		var listingUl = listingFiles.find('ul');

		if (!listingUl.length) {
			instance._listInfo.append('<h4>' + instance._fileListText + '</h4>');

			listingFiles.append('<ul class="lfr-component"></ul>');

			instance._uploadTarget.append(instance._clearUploadsButton);
			instance._clearUploadsButton.hide();

			instance._cancelButton.click(
				function() {
					instance.cancelUploads();
					instance._clearUploadsButton.hide();
				}
			);
		}

		instance._cancelButton.show();
		instance._uploadButton.show();

		listingFiles = listingFiles.find('ul');

		var fileId = instance._namespace(file.id);
		var fileName = file.name;

		var li = jQuery(
			'<li class="upload-file" id="' + fileId + '">' +
				'<span class="file-title">' + fileName + '</span>' +
				'<span class="progress-bar">' +
					'<span class="progress" id="' + fileId + 'progress"></span>' +
				'</span>' +
				'<a class="lfr-button cancel-button" href="javascript: ;" id="' + fileId+ 'cancelButton">' + instance._cancelFileText + '</a>' +
			'</li>');

		li.find('.cancel-button').click(
			function() {
				instance._uploader.cancelUpload(file.id);
			}
		);

		var uploadedFiles = listingFiles.find('.upload-complete');

		uploadedFiles = uploadedFiles.filter(':first');

		if (uploadedFiles.length) {
			uploadedFiles.before(li);
		}
		else {
			listingFiles.append(li);
		}

		var stats = instance._getStats();
		var listLength = stats.files_queued;

		instance._updateList(listLength);
	},

	fileCancelled: function(file, error_code, msg) {
		var instance = this;

		var stats = instance._getStats();

		var fileId = instance._namespace(file.id);
		var fileName = file.name;
		var li = jQuery('#' + fileId);

		instance._updateList(stats.files_queued);

		li.fadeOut('slow');
	},

	fileUploadComplete: function(file) {
		var instance = this;

		var fileId = instance._namespace(file.id);
		var li = jQuery('#' + fileId);

		li.removeClass('file-uploading').addClass('upload-complete');

		var uploader = instance._uploader;
		var stats = instance._getStats();

		if (stats.files_queued > 0 && !instance._queueCancelled) {

			// Automatically start the next upload if the queue wasn't cancelled

			uploader.startUpload();
		}
		else if (stats.files_queued === 0 && !instance._queueCancelled) {

			// Call Queue Complete if there are no more files queued and the queue wasn't cancelled

			instance.uploadsComplete(file);
		}
		else {

			// Don't do anything. Remove the queue cancelled flag (if the queue was cancelled it will be set again)

			instance._queueCancelled = false;
		}

		if (instance._onFileComplete) {
			instance._onFileComplete(file);
		}
	},

	flashLoaded: function() {
		var instance = this;

		instance._setupControls();
	},

	uploadError: function(file, error_code, msg) {
		var instance = this;

		/*
		Error codes:
			-10 HTTP error
			-20 No upload script specified
			-30 IOError
			-40 Security error
			-50 Filesize too big
		*/

		if (error_code == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED) {
			instance.fileCancelled(file, error_code, msg);
		}

		if (instance._onUploadError) {
			instance._onUploadError(arguments);
		}
	},

	uploadProgress: function(file, bytesLoaded) {
		var instance = this;
		var fileId = instance._namespace(file.id);
		var progress = document.getElementById(fileId + 'progress');
		var percent = Math.ceil((bytesLoaded / file.size) * 100);

		progress.style.width = percent + '%';

		if (instance._onUploadProgress) {
			instance._onUploadProgress(file, bytesLoaded);
		}
	},

	uploadsComplete: function(file) {
		var instance = this;

		instance._cancelButton.hide();
		instance._updateList(0, instance._uploadsCompleteText);
		instance._uploadButton.hide();

		if (instance._clearUploadsButton.is(':hidden')) {
			instance._clearUploadsButton.show();
		}

		if (instance._onUploadsComplete) {
			instance._onUploadsComplete();
		}

		var uploader = instance._uploader;

		uploader.setStats(
			{
				successful_uploads: 0
			}
		);
	},

	uploadStart: function(file) {
		var instance = this;

		var stats = instance._getStats();
		var listLength = (stats.successful_uploads + stats.upload_errors + stats.files_queued);
		var position = (stats.successful_uploads + stats.upload_errors + 1);

		var currentListText = instance._uploadStatusText.replace('[$POS$]', position).replace('[$TOTAL$]', listLength);
		var fileId = instance._namespace(file.id);

		instance._updateList(listLength, currentListText);

		var li = jQuery('#' + fileId);

		li.addClass('file-uploading');

		return true;
	},

	uploadSuccess: function(file, data) {
		var instance = this;

		instance.fileUploadComplete(file, data);
	},

	_clearUploads: function() {
		var instance = this;

		var completeUploads = instance._fileList.find('.upload-complete');

		completeUploads.fadeOut('slow',
			function() {
				jQuery(this).remove();
			}
		);

		instance._clearUploadsButton.hide();
	},

	_getStats: function() {
		var instance = this;

		return instance._uploader.getStats();
	},

	_namespace: function(txt) {
		var instance = this;

		txt = txt || '';

		return instance._namespaceId + txt;

	},

	_setupCallbacks: function() {
		var instance = this;

		// Global callback references

		instance._cancelUploads = instance._namespace('cancelUploads');
		instance._fileAdded = instance._namespace('fileAdded');
		instance._fileCancelled = instance._namespace('fileCancelled');
		instance._flashLoaded = instance._namespace('flashLoaded');
		instance._uploadStart = instance._namespace('uploadStart');
		instance._uploadProgress = instance._namespace('uploadProgress');
		instance._uploadError = instance._namespace('uploadError');
		instance._uploadSuccess = instance._namespace('uploadSuccess');
		instance._fileUploadComplete = instance._namespace('fileUploadComplete');
		instance._uploadsComplete = instance._namespace('uploadsComplete');
		instance._uploadsCancelled = instance._namespace('uploadsCancelled');

		// Global swfUpload var

		instance._swfUpload = instance._namespace('cancelUploads');

		window[instance._cancelUploads] = function() {
			instance.cancelUploads.apply(instance, arguments);
		};

		window[instance._fileAdded] = function() {
			instance.fileAdded.apply(instance, arguments);
		};

		window[instance._fileCancelled] = function() {
			instance.fileCancelled.apply(instance, arguments);
		};

		window[instance._uploadStart] = function() {
			instance.uploadStart.apply(instance, arguments);
		};

		window[instance._uploadProgress] = function() {
			instance.uploadProgress.apply(instance, arguments);
		};

		window[instance._uploadError] = function() {
			instance.uploadError.apply(instance, arguments);
		};

		window[instance._fileUploadComplete] = function() {
			instance.fileUploadComplete.apply(instance, arguments);
		};

		window[instance._uploadSuccess] = function() {
			instance.uploadSuccess.apply(instance, arguments);
		};

		window[instance._uploadsComplete] = function() {
			instance.uploadsComplete.apply(instance, arguments);
		};

		window[instance._flashLoaded] = function() {
			instance.flashLoaded.apply(instance, arguments);
		};

	},

	_setupControls: function() {
		var instance = this;

		instance._uploadTargetId = instance._namespace('uploadTarget');
		instance._listInfoId = instance._namespace('listInfo');
		instance._fileListId = instance._namespace('fileList');

		instance._uploadTarget = jQuery('<div id="' + instance._uploadTargetId + '" class="float-container upload-target"></div>');
		instance._listInfo = jQuery('<div id="' + instance._listInfoId + '" class="upload-list-info"></div>');
		instance._fileList = jQuery('<div id="' + instance._fileListId + '" class="upload-list"></div>');
		instance._cancelButton = jQuery('<a class="lfr-button cancel-uploads" href="javascript: ;">' + instance._cancelUploadsText + '</a>');
		instance._clearUploadsButton = jQuery('<a class="lfr-button clear-uploads" href="javascript: ;">' + instance._clearRecentUploadsText + '</a>');

		instance._browseButton = jQuery('<a class="lfr-button browse-button" href="javascript: ;">' + instance._browseText + '</a>');
		instance._uploadButton = jQuery('<a class="lfr-button upload-button" href="javascript: ;">' + instance._uploadFilesText + '</a>');

		instance._container.prepend([instance._uploadTarget[0], instance._listInfo[0], instance._fileList[0]]);
		instance._uploadTarget.append([instance._browseButton[0], instance._uploadButton[0], instance._cancelButton[0]]);

		instance._clearUploadsButton.click(
			function() {
				instance._clearUploads();
			}
		);

		instance._browseButton.click(
			function() {
				instance._uploader.selectFiles();
			}
		);

		instance._uploadButton.click(
			function() {
				instance._uploader.startUpload();
			}
		);

		instance._uploadButton.hide();
		instance._cancelButton.hide();

		if (instance._fallbackContainer.length) {
			instance._useFallbackButton = jQuery('<a class="use-fallback using-new-uploader" href="javascript: ;">' + instance._useFallbackText + '</a>');
			instance._fallbackContainer.after(instance._useFallbackButton);

			instance._useFallbackButton.click(
				function() {
					var fallback = jQuery(this);
					var newUploaderClass = 'using-new-uploader';
					var fallbackClass = 'using-classic-uploader';

					if (fallback.is('.' + newUploaderClass)) {
						instance._container.hide();
						instance._fallbackContainer.show();

						fallback.text(instance._useNewUploaderText);
						fallback.removeClass(newUploaderClass).addClass(fallbackClass);

						instance._setupIframe();

						var classicUploaderUrl = '';

						if (location.hash.length) {
							classicUploaderUrl = '&';
						}

						location.hash += classicUploaderUrl + instance._classicUploaderParam;
					}
					else {
						instance._container.show();
						instance._fallbackContainer.hide();
						fallback.text(instance._useFallbackText);
						fallback.removeClass(fallbackClass).addClass(newUploaderClass);

						location.hash = location.hash.replace(instance._classicUploaderParam, instance._newUploaderParam);
					}
				}
			);
		}
	},

	_setupIframe: function() {
		var instance = this;

		if (!instance._fallbackIframe) {
			instance._fallbackIframe = instance._fallbackContainer.find('iframe[@id$=-iframe]');

			var frameHeight = jQuery('#content-wrapper', instance._fallbackIframe[0].contentWindow).height() || 250;

			instance._fallbackIframe.height(frameHeight + 150);
		}
	},

	_setupUploader: function() {
		var instance = this;

		if (instance._allowedFileTypes.indexOf('*') == -1) {
			var fileTypes = instance._allowedFileTypes.split(',');

			fileTypes = jQuery.map(
				fileTypes,
				function(value, key) {
					var fileType = value;
					if (value.indexOf('*') == -1) {
						fileType = '*' + value;
					}
					return fileType;
				}
			);

			instance._allowedFileTypes = fileTypes.join(';');
		}

		instance._uploader = new SWFUpload({
			upload_url: instance._uploadFile,
			target: instance._uploadTargetId,
			flash_url: themeDisplay.getPathContext() + '/html/js/misc/swfupload/swfupload_f9.swf',
			file_size_limit: instance._maxFileSize,
			file_types: instance._allowedFileTypes,
			file_types_description: instance._fileTypesDescriptionText,
			browse_link_innerhtml: instance._browseText,
			upload_link_innerhtml: instance._uploadFilesText,
			browse_link_class: 'browse-button liferay-button',
			upload_link_class: 'upload-button liferay-button',
			swfupload_loaded_handler: window[instance._flashLoaded],
			file_queued_handler: window[instance._fileAdded],
			upload_start_handler: window[instance._uploadStart],
			upload_progress_handler: window[instance._uploadProgress],
			upload_complete_handler: window[instance._fileUploadComplete],
			upload_success_handler: window[instance._uploadSuccess],
			upload_file_cancel_callback: window[instance._fileCancelled],
			upload_queue_complete_callback: window[instance._uploadsComplete],
			upload_error_handler: window[instance._uploadError],
			upload_cancel_callback: window[instance._cancelUploads],
			auto_upload : false,
			file_post_name: 'file',
			create_ui: true,
			debug: false
		});

		window[instance._swfUpload] = instance._uploader;
	},

	_updateList: function(listLength, message) {
		var instance = this;

		var infoTitle = instance._listInfo.find('h4');
		var listText = '';

		if (!message) {
			listText = instance._fileListPendingText;
			listText = listText.replace(/\d+/g, listLength);
		}
		else {
			listText = message;
		}

		infoTitle.html(listText);
	}
});
