Ext.define('MCClient.store.Renderers', {
    extend: 'Ext.data.Store',
    
    config: {
    	model: 'MCClient.model.Renderer',
        //autoload the data from the server
        autoLoad: true
    }
});