Ext.define('MCClient.model.Content', {

    extend: 'Ext.data.Model',

    config: {
        fields: [
                 { name: 'id', type: 'string' },
                 { name: 'title', type: 'string' }
        ],
        proxy: {
            type: 'rest',
            url: '../contents' //'http://localhost:8080/springrest/contents'
        }
    }
});