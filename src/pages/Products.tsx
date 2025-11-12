import { useLanguage } from '@/contexts/LanguageContext';
import { useQuery } from '@tanstack/react-query';
import { Card, CardContent, CardFooter } from '@/components/ui/card';
import { Skeleton } from '@/components/ui/skeleton';
import sourdoughImg from '@/assets/sourdough.jpg';
import croissantImg from '@/assets/croissant.jpg';
import baguetteImg from '@/assets/baguette.jpg';
import cookieImg from '@/assets/cookie.jpg';
import cinnamonRollImg from '@/assets/cinnamon-roll.jpg';
import { getProducts, type Product } from '@/lib/api';

const productImages: Record<string, string> = {
  'Sourdough Bread': sourdoughImg,
  'Croissant': croissantImg,
  'Baguette': baguetteImg,
  'Chocolate Chip Cookie': cookieImg,
  'Cinnamon Roll': cinnamonRollImg,
};

const Products = () => {
  const { language, t } = useLanguage();

  const { data: products, isLoading, error } = useQuery({
    queryKey: ['products'],
    queryFn: getProducts,
    retry: 2,
  });

  return (
    <main className="py-16">
      <div className="container mx-auto px-4">
        <h1 className="font-playfair text-5xl font-bold text-center mb-4">
          {t('Our Products', 'Nuestros Productos')}
        </h1>
        <p className="text-xl text-center text-muted-foreground mb-12 max-w-2xl mx-auto">
          {t(
            'Discover our selection of fresh-baked breads, pastries, and treats',
            'Descubre nuestra selección de panes, bollería y delicias recién horneadas'
          )}
        </p>

        {isLoading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {[...Array(6)].map((_, i) => (
              <Card key={i}>
                <Skeleton className="h-64 w-full" />
                <CardContent className="p-6">
                  <Skeleton className="h-6 w-3/4 mb-2" />
                  <Skeleton className="h-4 w-full mb-4" />
                  <Skeleton className="h-8 w-1/3" />
                </CardContent>
              </Card>
            ))}
          </div>
        ) : error ? (
          <div className="text-center">
            <div className="bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-lg p-6 max-w-2xl mx-auto">
              <h3 className="text-lg font-semibold text-red-800 dark:text-red-200 mb-2">
                {t('Error loading products', 'Error al cargar productos')}
              </h3>
              <p className="text-red-600 dark:text-red-400 text-sm mb-4">
                {error instanceof Error ? error.message : String(error)}
              </p>
              <p className="text-muted-foreground text-xs">
                {t(
                  'Please check your connection and try again later.',
                  'Por favor, verifica tu conexión e inténtalo más tarde.'
                )}
              </p>
            </div>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {products?.map((product: Product) => (
              <Card key={product.id} className="overflow-hidden hover:shadow-lg transition-shadow">
                <div className="aspect-square overflow-hidden">
                  <img
                    src={productImages[product.name_en] || '/placeholder.svg'}
                    alt={language === 'en' ? product.name_en : product.name_es}
                    className="w-full h-full object-cover hover:scale-105 transition-transform duration-300"
                    onError={(e) => {
                      // Fallback на placeholder если изображение не загрузилось
                      const target = e.target as HTMLImageElement;
                      if (target.src && !target.src.includes('placeholder')) {
                        target.src = '/placeholder.svg';
                      }
                    }}
                  />
                </div>
                <CardContent className="p-6">
                  <h3 className="font-playfair text-2xl font-semibold mb-2">
                    {language === 'en' ? product.name_en : product.name_es}
                  </h3>
                  <p className="text-muted-foreground mb-4">
                    {language === 'en' ? product.description_en : product.description_es}
                  </p>
                </CardContent>
                <CardFooter className="px-6 pb-6">
                  <p className="text-2xl font-semibold text-primary">
                    €{product.price.toFixed(2)}
                  </p>
                </CardFooter>
              </Card>
            ))}
          </div>
        )}
      </div>
    </main>
  );
};

export default Products;