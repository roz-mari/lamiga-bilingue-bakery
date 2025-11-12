import { useLanguage } from '@/contexts/LanguageContext';

const About = () => {
  const { t } = useLanguage();

  return (
    <main className="py-16">
      <div className="container mx-auto px-4 max-w-4xl">
        <h1 className="font-playfair text-5xl font-bold text-center mb-8">
          {t('About Us', 'Sobre Nosotros')}
        </h1>
        
        <div className="prose prose-lg max-w-none">
          <p className="text-xl text-muted-foreground mb-6">
            {t(
              'La Miga is more than just a bakery – it\'s a celebration of artisan baking traditions passed down through generations.',
              'La Miga es más que una panadería: es una celebración de las tradiciones de panadería artesanal transmitidas a través de generaciones.'
            )}
          </p>

          <h2 className="font-playfair text-3xl font-semibold mt-12 mb-4">
            {t('Our Story', 'Nuestra Historia')}
          </h2>
          <p className="text-muted-foreground mb-6">
            {t(
              'Founded in 2010, La Miga began with a simple mission: to bring authentic, handcrafted bread to our community. Our founders, inspired by traditional European baking techniques, opened our doors with a commitment to quality and craftsmanship that continues to this day.',
              'Fundada en 2010, La Miga comenzó con una misión simple: traer pan auténtico y artesanal a nuestra comunidad. Nuestros fundadores, inspirados por las técnicas tradicionales de panadería europea, abrieron nuestras puertas con un compromiso de calidad y artesanía que continúa hasta el día de hoy.'
            )}
          </p>

          <h2 className="font-playfair text-3xl font-semibold mt-12 mb-4">
            {t('Our Philosophy', 'Nuestra Filosofía')}
          </h2>
          <p className="text-muted-foreground mb-6">
            {t(
              'We believe in the power of simple, quality ingredients transformed through time-honored baking methods. Every loaf, every pastry is made with care and attention to detail. We source our ingredients locally whenever possible and never compromise on quality.',
              'Creemos en el poder de ingredientes simples y de calidad transformados a través de métodos de horneado tradicionales. Cada barra, cada pastelería se hace con cuidado y atención al detalle. Obtenemos nuestros ingredientes localmente siempre que sea posible y nunca comprometemos la calidad.'
            )}
          </p>

          <h2 className="font-playfair text-3xl font-semibold mt-12 mb-4">
            {t('Our Team', 'Nuestro Equipo')}
          </h2>
          <p className="text-muted-foreground mb-6">
            {t(
              'Our skilled bakers arrive each morning before dawn to begin the careful process of mixing, kneading, and baking. With years of experience and a passion for their craft, they ensure that every product leaving our ovens meets our high standards.',
              'Nuestros hábiles panaderos llegan cada mañana antes del amanecer para comenzar el cuidadoso proceso de mezclar, amasar y hornear. Con años de experiencia y pasión por su oficio, se aseguran de que cada producto que sale de nuestros hornos cumpla con nuestros altos estándares.'
            )}
          </p>
        </div>
      </div>
    </main>
  );
};

export default About;