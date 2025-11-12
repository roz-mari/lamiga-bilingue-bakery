import { useLanguage } from '@/contexts/LanguageContext';
import { Facebook, Instagram, Twitter } from 'lucide-react';

const Footer = () => {
  const { t } = useLanguage();

  return (
    <footer className="bg-secondary mt-16">
      <div className="container mx-auto px-4 py-12">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div>
            <h3 className="font-playfair text-2xl font-bold text-primary mb-4">
              La Miga
            </h3>
            <p className="text-muted-foreground">
              {t(
                'Artisan bakery serving fresh bread and pastries daily.',
                'Panadería artesanal que ofrece pan fresco y bollería todos los días.'
              )}
            </p>
          </div>

          <div>
            <h4 className="font-semibold text-lg mb-4">
              {t('Contact', 'Contacto')}
            </h4>
            <p className="text-muted-foreground">
              123 Baker Street<br />
              {t('City', 'Ciudad')}, {t('State', 'Estado')} 12345<br />
              <a href="tel:+1234567890" className="hover:text-primary transition-colors">
                (123) 456-7890
              </a><br />
              <a href="mailto:info@lamiga.com" className="hover:text-primary transition-colors">
                info@lamiga.com
              </a>
            </p>
          </div>

          <div>
            <h4 className="font-semibold text-lg mb-4">
              {t('Follow Us', 'Síguenos')}
            </h4>
            <div className="flex gap-4">
              <a 
                href="https://facebook.com" 
                target="_blank" 
                rel="noopener noreferrer"
                className="text-muted-foreground hover:text-primary transition-colors"
                aria-label="Facebook"
              >
                <Facebook className="w-6 h-6" />
              </a>
              <a 
                href="https://instagram.com" 
                target="_blank" 
                rel="noopener noreferrer"
                className="text-muted-foreground hover:text-primary transition-colors"
                aria-label="Instagram"
              >
                <Instagram className="w-6 h-6" />
              </a>
              <a 
                href="https://twitter.com" 
                target="_blank" 
                rel="noopener noreferrer"
                className="text-muted-foreground hover:text-primary transition-colors"
                aria-label="Twitter"
              >
                <Twitter className="w-6 h-6" />
              </a>
            </div>
          </div>
        </div>

        <div className="border-t border-border mt-8 pt-8 text-center text-muted-foreground">
          <p>&copy; {new Date().getFullYear()} La Miga. {t('All rights reserved.', 'Todos los derechos reservados.')}</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;