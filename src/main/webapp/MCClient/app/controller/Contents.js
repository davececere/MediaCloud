Ext.define('MCClient.controller.Contents', {
    extend: 'Ext.app.Controller',
    config: {
        refs: {
            contentList: '#contentsList'
        },
        control: {
        	contentList: {
        		itemtaphold: function (list, idx, target, record, evt) {
                    Ext.Msg.alert('itemtaphold', record.data.title);
                }, 
        		itemswipe: function (list, idx, target, record, evt) {
                    Ext.Msg.alert('itemswipe', record.data.id);
                }
        	}
        }
    }
});