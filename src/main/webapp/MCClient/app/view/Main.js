Ext.define("MCClient.view.Main", {
    extend: 'Ext.tab.Panel',
    requires: [
        'Ext.TitleBar',
        'Ext.Video',        
        'Ext.data.Store',
        'Ext.List',
        'Ext.plugin.PullRefresh',
        'Ext.carousel.Carousel',
        'MCClient.store.Contents',
        'MCClient.store.Devices',
        'MCClient.store.Renderers'
    ],
    config: {
        tabBarPosition: 'bottom',

        items: [
            {
                title: 'Welcome',
                iconCls: 'home',

                styleHtmlContent: true,
                scrollable: true,

                items: {
                    docked: 'top',
                    xtype: 'titlebar',
                    title: 'Welcome to Sencha Touch 2'
                },

                html: [
                    "You've just generated a new Sencha Touch 2 project. What you're looking at right now is the ",
                    "contents of <a target='_blank' href=\"app/view/Main.js\">app/view/Main.js</a> - edit that file ",
                    "and refresh to change what's rendered here."
                ].join("")
            },
            {
                title: 'Devices',
                iconCls: 'action',
                layout: 'fit',
                items: [
                    {
                        docked: 'top',
                        xtype: 'titlebar',
                        title: 'Devices'
                    },
                    {
                        xtype: 'carousel',
                        id: 'devicesCarousel',

                        //the direction is horizontal
                        direction: 'horizontal',

                        //we turn on direction lock so you cannot scroll diagonally
                        directionLock: true,

                        //and give it the items array
                        //items: [{html:"test1"},{html:"test2"}]
                        store: 'Devices'
                    }
                ]
            },
            {
                title: 'Renderers',
                iconCls: 'action',
                layout: 'fit',
                items: [
                    {
                        docked: 'top',
                        xtype: 'titlebar',
                        title: 'Renderers'
                    },
                    {
                        xtype: 'carousel',
                        id: 'renderersCarousel',

                        //the direction is horizontal
                        direction: 'vertical',

                        //we turn on direction lock so you cannot scroll diagonally
                        directionLock: true,

                        //and give it the items array
                        //items: [{html:"test1"},{html:"test2"}]
                        store: 'Renderers'
                    }
                ]
            },
            {
            	title: 'Contents',
            	iconCls: 'action',
                layout: 'fit',
            	
            	items: [
                        {
                            docked: 'top',
                            xtype: 'titlebar',
                            title: 'Content List'
                        },
            	        {
                        		id: 'contentsList',
            	                //give it an xtype of list for the list component
            	                xtype: 'list',

            	                //set the itemtpl to show the fields for the store
            	                itemTpl: '<div class="contact2"><strong>{title}</strong> {id}</div>',

            	                //group the list
            	                grouped: true,

            	                //enable the indexBar
            	                indexBar: true,

            	                //bind the store to this list
            	                store: 'Contents'
            	        }
            	]
            
            }
        ]
    }
});
