-- Create products table
CREATE TABLE public.products (
  id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  name_en TEXT NOT NULL,
  name_es TEXT NOT NULL,
  description_en TEXT,
  description_es TEXT,
  price DECIMAL(10,2) NOT NULL,
  image_url TEXT,
  category TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Enable Row Level Security
ALTER TABLE public.products ENABLE ROW LEVEL SECURITY;

-- Create policy for public read access (no authentication needed)
CREATE POLICY "Anyone can view products" 
ON public.products 
FOR SELECT 
USING (true);

-- Create contact_submissions table
CREATE TABLE public.contact_submissions (
  id UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  name TEXT NOT NULL,
  email TEXT NOT NULL,
  message TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

-- Enable Row Level Security
ALTER TABLE public.contact_submissions ENABLE ROW LEVEL SECURITY;

-- Create policy for public insert (anyone can submit contact form)
CREATE POLICY "Anyone can submit contact form" 
ON public.contact_submissions 
FOR INSERT 
WITH CHECK (true);

-- Insert sample products
INSERT INTO public.products (name_en, name_es, description_en, description_es, price, category) VALUES
('Sourdough Bread', 'Pan de Masa Madre', 'Traditional sourdough with crispy crust', 'Pan de masa madre tradicional con corteza crujiente', 6.50, 'bread'),
('Croissant', 'Croissant', 'Buttery flaky pastry', 'Hojaldre mantecoso y hojaldrado', 3.25, 'pastry'),
('Baguette', 'Baguette', 'Classic French bread', 'Pan francés clásico', 4.00, 'bread'),
('Chocolate Chip Cookie', 'Galleta con Chips de Chocolate', 'Fresh baked cookie with chocolate chips', 'Galleta recién horneada con chips de chocolate', 2.50, 'cookies'),
('Cinnamon Roll', 'Rollo de Canela', 'Sweet roll with cinnamon and icing', 'Rollo dulce con canela y glaseado', 4.75, 'pastry'),
('Whole Wheat Bread', 'Pan Integral', 'Healthy whole grain bread', 'Pan integral saludable', 5.50, 'bread');