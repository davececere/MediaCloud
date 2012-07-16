Ext.define('MCClient.controller.Devices', {
    extend: 'Ext.app.Controller',
    requires: [
               'MCClient.store.Devices'
           ],
    config: {
        refs: {
            devicesCarousel: '#devicesCarousel'
        },
        control: {
        	devicesCarousel: {
        		initialize: 'initCarousel'
        	}
        }
    },

    initCarousel : function(devicesCarousel){
    	 Ext.getStore('Devices').load(function(devices) {
    	    var items = [];
    	    
    	    Ext.each(devices, function(device) {
    	        items.push({
    	        	xtype: 'component', html: device.get('name')
    	        });
    	    });
    	    
    	    devicesCarousel.setItems(items);
    	    devicesCarousel.setActiveItem(0);
    	});
    }
});