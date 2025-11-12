import { useLanguage } from '@/contexts/LanguageContext';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { supabase } from '@/integrations/supabase/client';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Textarea } from '@/components/ui/textarea';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { toast } from 'sonner';

const formSchema = z.object({
  name: z.string().min(2, 'Name must be at least 2 characters').max(100),
  email: z.string().email('Invalid email address').max(255),
  message: z.string().min(10, 'Message must be at least 10 characters').max(1000),
});

type FormValues = z.infer<typeof formSchema>;

const Contact = () => {
  const { t } = useLanguage();
  const [isSubmitting, setIsSubmitting] = useState(false);

  const form = useForm<FormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: '',
      email: '',
      message: '',
    },
  });

  const onSubmit = async (values: FormValues) => {
    setIsSubmitting(true);
    try {
      const { error } = await supabase
        .from('contact_submissions')
        .insert([{
          name: values.name,
          email: values.email,
          message: values.message,
        }]);

      if (error) throw error;

      toast.success(
        t(
          'Message sent successfully! We\'ll get back to you soon.',
          '¡Mensaje enviado con éxito! Te responderemos pronto.'
        )
      );
      form.reset();
    } catch (error) {
      toast.error(
        t(
          'Failed to send message. Please try again.',
          'Error al enviar el mensaje. Por favor, inténtalo de nuevo.'
        )
      );
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <main className="py-16">
      <div className="container mx-auto px-4 max-w-4xl">
        <h1 className="font-playfair text-5xl font-bold text-center mb-4">
          {t('Contact Us', 'Contáctanos')}
        </h1>
        <p className="text-xl text-center text-muted-foreground mb-12">
          {t(
            'Have a question or want to place a special order? We\'d love to hear from you!',
            '¿Tienes una pregunta o quieres hacer un pedido especial? ¡Nos encantaría saber de ti!'
          )}
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
          <div>
            <h2 className="font-playfair text-3xl font-semibold mb-6">
              {t('Get In Touch', 'Ponte en Contacto')}
            </h2>
            
            <Form {...form}>
              <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
                <FormField
                  control={form.control}
                  name="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t('Name', 'Nombre')}</FormLabel>
                      <FormControl>
                        <Input placeholder={t('Your name', 'Tu nombre')} {...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name="email"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t('Email', 'Correo electrónico')}</FormLabel>
                      <FormControl>
                        <Input 
                          type="email" 
                          placeholder={t('your.email@example.com', 'tu.correo@ejemplo.com')} 
                          {...field} 
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name="message"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t('Message', 'Mensaje')}</FormLabel>
                      <FormControl>
                        <Textarea 
                          placeholder={t('Your message...', 'Tu mensaje...')}
                          className="min-h-[150px]"
                          {...field} 
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <Button type="submit" disabled={isSubmitting} className="w-full">
                  {isSubmitting 
                    ? t('Sending...', 'Enviando...') 
                    : t('Send Message', 'Enviar Mensaje')
                  }
                </Button>
              </form>
            </Form>
          </div>

          <div>
            <h2 className="font-playfair text-3xl font-semibold mb-6">
              {t('Visit Us', 'Visítanos')}
            </h2>
            
            <div className="space-y-6">
              <div>
                <h3 className="font-semibold text-lg mb-2">
                  {t('Address', 'Dirección')}
                </h3>
                <p className="text-muted-foreground">
                  123 Baker Street<br />
                  {t('City', 'Ciudad')}, {t('State', 'Estado')} 12345
                </p>
              </div>

              <div>
                <h3 className="font-semibold text-lg mb-2">
                  {t('Phone', 'Teléfono')}
                </h3>
                <a href="tel:+1234567890" className="text-muted-foreground hover:text-primary transition-colors">
                  (123) 456-7890
                </a>
              </div>

              <div>
                <h3 className="font-semibold text-lg mb-2">
                  {t('Email', 'Correo electrónico')}
                </h3>
                <a href="mailto:info@lamiga.com" className="text-muted-foreground hover:text-primary transition-colors">
                  info@lamiga.com
                </a>
              </div>

              <div>
                <h3 className="font-semibold text-lg mb-2">
                  {t('Hours', 'Horario')}
                </h3>
                <p className="text-muted-foreground">
                  {t('Monday - Friday', 'Lunes - Viernes')}: 7:00 AM - 6:00 PM<br />
                  {t('Saturday', 'Sábado')}: 8:00 AM - 5:00 PM<br />
                  {t('Sunday', 'Domingo')}: {t('Closed', 'Cerrado')}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Contact;