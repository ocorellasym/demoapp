package corella.oscar.check24.di

import corella.oscar.check24.data.repository.ProductsRepository
import corella.oscar.check24.data.repository.ProductsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository

}