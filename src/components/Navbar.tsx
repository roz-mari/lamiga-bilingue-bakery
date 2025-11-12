import { Link } from 'react-router-dom';
import { useLanguage } from '@/contexts/LanguageContext';
import { Button } from '@/components/ui/button';
import { Globe } from 'lucide-react';

const Navbar = () => {
  const { language, setLanguage, t } = useLanguage();

  return (
    <nav className="sticky top-0 z-50 bg-background/95 backdrop-blur border-b border-border">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <Link to="/" className="font-playfair text-2xl font-bold text-primary">
            La Miga
          </Link>
          
          <div className="hidden md:flex items-center gap-8">
            <Link to="/" className="text-foreground hover:text-primary transition-colors">
              {t('Home', 'Inicio')}
            </Link>
            <Link to="/about" className="text-foreground hover:text-primary transition-colors">
              {t('About', 'Sobre Nosotros')}
            </Link>
            <Link to="/products" className="text-foreground hover:text-primary transition-colors">
              {t('Products', 'Productos')}
            </Link>
            <Link to="/contact" className="text-foreground hover:text-primary transition-colors">
              {t('Contact', 'Contacto')}
            </Link>
          </div>

          <Button
            variant="outline"
            size="sm"
            onClick={() => {
              const newLang = language === 'en' ? 'es' : 'en';
              setLanguage(newLang);
            }}
            className="flex items-center gap-2"
            title={language === 'en' ? 'Switch to Spanish' : 'Cambiar a Inglés'}
          >
            <Globe className="w-4 h-4" />
            {language === 'en' ? 'ES' : 'EN'}
          </Button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;