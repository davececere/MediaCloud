Ext.define('MCClient.store.Contents', {
    extend: 'Ext.data.Store',
    
    config: {
    	//give the store some fields
        //fields: ['id', 'title'],
    	model: 'MCClient.model.Content',
        //filter the data using the firstName field
        sorters: 'title',

        //autoload the data from the server
        autoLoad: true,

        //setup the grouping functionality to group by the first letter of the firstName field
        grouper: {
            groupFn: function(record) {
                return record.get('title')[0];
            }
        }
    }
});