db.photobook_meta.createIndex( {photobookId:1}, { unique: true } )

db.photobook_images.createIndex( {photobookId:1} )

db.photobook_images.createIndex( {imageId:1, photobookId:1}, { unique: true } )