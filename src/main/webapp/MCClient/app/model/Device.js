Ext.define('MCClient.model.Device', {

    extend: 'Ext.data.Model',

    config: {
        fields: [
                 { name: 'name', type: 'string' }
        ],
        proxy: {
            type: 'rest',
            url: '../devices' //'http://localhost:8080/springrest/devices'
        }
    }
});