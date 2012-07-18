Ext.define('MCClient.controller.Renderers', {
    extend: 'Ext.app.Controller',
    requires: [
               'MCClient.store.Renderers'
           ],
    config: {
        refs: {
            renderersCarousel: '#renderersCarousel'
        },
        control: {
        	renderersCarousel: {
        		initialize: 'initCarousel'
        	}
        }
    },

    initCarousel : function(renderersCarousel){
    	 Ext.getStore('Renderers').load(function(renderers) {
    	    var items = [];
    	    
    	    Ext.each(renderers, function(renderer) {
    	        items.push({
    	        	xtype: 'component', html: renderer.get('name')
    	        });
    	    });
    	    
    	    renderersCarousel.setItems(items);
    	    renderersCarousel.setActiveItem(0);
    	});
    }
});