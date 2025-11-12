import { useLanguage } from '@/contexts/LanguageContext';
import { Button } from '@/components/ui/button';
import { Link } from 'react-router-dom';
import heroImage from '@/assets/hero-bakery.jpg';

const Home = () => {
  const { t } = useLanguage();

  return (
    <main>
      {/* Hero Section */}
      <section className="relative h-[600px] flex items-center justify-center overflow-hidden">
        <img 
          src={heroImage}
          alt="La Miga Bakery Interior"
          className="absolute inset-0 w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-r from-background/90 to-background/60" />
        
        <div className="relative z-10 container mx-auto px-4 text-center md:text-left">
          <h1 className="font-playfair text-5xl md:text-7xl font-bold text-foreground mb-6">
            {t('Fresh Baked Daily', 'Horneado Fresco Diario')}
          </h1>
          <p className="text-xl md:text-2xl text-muted-foreground mb-8 max-w-2xl">
            {t(
              'Artisan breads and pastries made with love and traditional techniques',
              'Panes artesanales y bollería hechos con amor y técnicas tradicionales'
            )}
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center md:justify-start">
            <Button asChild size="lg" className="bg-primary hover:bg-primary/90">
              <Link to="/products">
                {t('View Products', 'Ver Productos')}
              </Link>
            </Button>
            <Button asChild variant="outline" size="lg">
              <Link to="/about">
                {t('Our Story', 'Nuestra Historia')}
              </Link>
            </Button>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20 bg-secondary">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">🥖</span>
              </div>
              <h3 className="font-playfair text-2xl font-semibold mb-2">
                {t('Fresh Daily', 'Fresco Diario')}
              </h3>
              <p className="text-muted-foreground">
                {t(
                  'Baked fresh every morning using traditional methods',
                  'Horneado fresco cada mañana usando métodos tradicionales'
                )}
              </p>
            </div>

            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">🌾</span>
              </div>
              <h3 className="font-playfair text-2xl font-semibold mb-2">
                {t('Quality Ingredients', 'Ingredientes de Calidad')}
              </h3>
              <p className="text-muted-foreground">
                {t(
                  'Only the finest locally-sourced ingredients',
                  'Solo los mejores ingredientes de origen local'
                )}
              </p>
            </div>

            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-3xl">👨‍🍳</span>
              </div>
              <h3 className="font-playfair text-2xl font-semibold mb-2">
                {t('Artisan Craft', 'Artesanía')}
              </h3>
              <p className="text-muted-foreground">
                {t(
                  'Handcrafted by experienced bakers with passion',
                  'Elaborado a mano por panaderos experimentados con pasión'
                )}
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20">
        <div className="container mx-auto px-4 text-center">
          <h2 className="font-playfair text-4xl md:text-5xl font-bold mb-6">
            {t('Visit Us Today', 'Visítanos Hoy')}
          </h2>
          <p className="text-xl text-muted-foreground mb-8 max-w-2xl mx-auto">
            {t(
              'Experience the aroma and taste of authentic artisan baking',
              'Experimenta el aroma y sabor de la auténtica panadería artesanal'
            )}
          </p>
          <Button asChild size="lg" className="bg-accent hover:bg-accent/90">
            <Link to="/contact">
              {t('Get In Touch', 'Contáctanos')}
            </Link>
          </Button>
        </div>
      </section>
    </main>
  );
};

export default Home;