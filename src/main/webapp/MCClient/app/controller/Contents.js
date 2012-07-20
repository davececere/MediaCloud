Ext.define('MCClient.controller.Contents', {
    extend: 'Ext.app.Controller',
    config: {
        refs: {
            contentList: '#contentsList',
            renderersCarousel: '#renderersCarousel'
        },
        control: {
        	contentList: {
        		itemtaphold: function (list, idx, target, record, evt) {
                    Ext.Msg.alert('itemtaphold', record.data.id);
                }, 
        		itemswipe: function (list, idx, target, record, evt) {
                    var carousel = this.getRenderersCarousel();
                    // use carousel.getStore() instead?
                    var renderer = Ext.getStore('Renderers').getAt(carousel.getActiveIndex());
                    renderer.set('nowPlayingContentId',record.data.id);
                    renderer.save();
                }
        	}
        }
    }
});