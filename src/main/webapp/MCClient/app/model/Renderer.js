Ext.define('MCClient.model.Renderer', {

    extend: 'Ext.data.Model',

    config: {
        fields: [
                 { name: 'id', type: 'string' },
                 { name: 'name', type: 'string' },
                 { name: 'nowPlayingContentId', type: 'string' }
        ],
        proxy: {
            type: 'rest',
            appendId: true, //default
            headers: {                
                'Accept' : 'application/json'                 
            },
            url: '../renderers' //'http://localhost:8080/springrest/renderers'
        }
    }
});