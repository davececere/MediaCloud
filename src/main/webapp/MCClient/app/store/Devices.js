Ext.define('MCClient.store.Devices', {
    extend: 'Ext.data.Store',
    
    config: {
    	//give the store some fields
        //fields: ['id', 'title'],
    	id: 'devicesStore',
    	model: 'MCClient.model.Device',
        //filter the data using the firstName field
        sorters: 'name',

        //autoload the data from the server
        autoLoad: true,

        //setup the grouping functionality to group by the first letter of the firstName field
        grouper: {
            groupFn: function(record) {
                return record.get('name')[0];
            }
        }
    }
});