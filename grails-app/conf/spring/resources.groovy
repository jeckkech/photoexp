import com.mongodb.DB
import com.mongodb.MongoClient
import photoexp.ImageService
import photoexp.banks.DepositphotosService
import photoexp.banks.status.ShutterstockStatusService
import photoexp.file.FileService

// Place your Spring DSL code here
beans = {
    imageService(ImageService)
    dpService(DepositphotosService)
    fileService(FileService)
    shutterstockStatusService(ShutterstockStatusService)
}
