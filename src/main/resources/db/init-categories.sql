INSERT INTO public.categories(name)
SELECT T.name
FROM (VALUES ('Almacén'),
             ('Frescos'),
             ('Congelados'),
             ('Limpieza'),
             ('Equipamineto'),
             ('Bazar'),
             ('Descartables'),
             ('Bebidas'))
         AS T(name)
WHERE NOT EXISTS (SELECT 1
                  FROM public.categories C
                  WHERE C.name = T.name);


INSERT INTO public.sub_category_level1 (name, category_id)
SELECT T.sub_category_level1_name, C.id
FROM (VALUES
-- {Subcategoría N1, Categoría}																									
('Golosinas', 'Almacén'),
('Panadería', 'Almacén'),
('Snacks', 'Almacén'),
('Cereales', 'Almacén'),
('Endulzantes', 'Almacén'),
('Aderezos Y Salsas', 'Almacén'),
('Infusiones', 'Almacén'),
('Conservas', 'Almacén'),
('Harinas', 'Almacén'),
('Encurtidos', 'Almacén'),
('Mermeladas Y Dulces', 'Almacén'),
('Salsas Y Puré De Tomate', 'Almacén'),
('Aceites Y Condimentos', 'Almacén'),
('Arroz Y Legumbres', 'Almacén'),
('Especias', 'Almacén'),
('Pasta Seca, Lista Y Rellenas', 'Almacén'),
('Polvo Para Postres Y Repostería', 'Almacén'),
('Sopas, Caldos, Puré Y Saborizantes', 'Almacén'),
('Rebozador Y Pan Rallado', 'Almacén'),
('Leche En Polvo', 'Almacén'),
('Productos Orgánicos', 'Almacén'),
('Bebidas Sin Alcohol', 'Bebidas'),
('Bebidas Con Alcohol', 'Bebidas'),
('Lácteos', 'Frescos'),
('Fiambres', 'Frescos'),
('Quesos', 'Frescos'),
('Carnicería', 'Frescos'),
('Aves', 'Frescos'),
('Huevos', 'Frescos'),
('Pastas Frescas Y Tapas', 'Frescos'),
('Comidas Elaboradas', 'Frescos'),
('Frutas Y Verduras', 'Frescos'),
('Pescadería Fresca', 'Frescos'),
('Pescadería Congelada', 'Congelados'),
('Nuggets, Patitas Y Bocaditos', 'Congelados'),
('Hamburguesas Y Milanesas', 'Congelados'),
('Papas Congeladas / Fritas', 'Congelados'),
('Helados Y Postres', 'Congelados'),
('Comidas Congeladas', 'Congelados'),
('Vegetales Congelados', 'Congelados'),
('Frutas Congeladas', 'Congelados'),
('Lavado', 'Limpieza'),
('Papeles', 'Limpieza'),
('Insecticidas', 'Limpieza'),
('Calzado', 'Limpieza'),
('Accesorios De Limpieza', 'Limpieza'),
('Desodorantes De Ambiente', 'Limpieza'),
('Limpieza De Baño', 'Limpieza'),
('Limpieza De Cocina', 'Limpieza'),
('Limpieza De Pisos Y Superficies', 'Limpieza'),
('Lavandinas', 'Limpieza'),
('Linea Frio', 'Equipamineto'),
('Linea Calor', 'Equipamineto'),
('Carnicos', 'Equipamineto'),
('Panificados', 'Equipamineto'),
('Vajilla', 'Bazar'),
('Cubiertos Cuchilleria', 'Bazar'),
('Servilletas', 'Descartables')) AS T(sub_category_level1_name, category_name)
         INNER JOIN public.categories C ON C.name = T.category_name
WHERE NOT EXISTS (SELECT 1
                  FROM public.sub_category_level1 S1
                           INNER JOIN public.categories C_E ON S1.category_id = C_E.id
                  WHERE S1.name = T.sub_category_level1_name
                    AND C_E.name = T.category_name);



INSERT INTO public.sub_category_level2 (name, sub_category_level1_id)
SELECT T.sub_category_level2_name, S1.id
FROM (VALUES
-- {Subcategoría N2, Subcategoría N1}		
('Servilletas', 'Servilletas'),
('Alfajores', 'Golosinas'),
('Chocolates', 'Golosinas'),
('Caramelos y Chupetines', 'Golosinas'),
('Chicles Y Pastillas', 'Golosinas'),
('Galletitas', 'Panadería'),
('Panadería Propia', 'Panadería'),
('Panificados', 'Panadería'),
('Galletas, Tostadas Y Grisines', 'Panadería'),
('Papas Fritas', 'Snacks'),
('Bastones De Maíz', 'Snacks'),
('Maní', 'Snacks'),
('Palitos', 'Snacks'),
('Cereales Con Azúcar', 'Cereales'),
('Cereales Infantiles', 'Cereales'),
('Break Saludables', 'Cereales'),
('Barras De Arroz', 'Cereales'),
('Azúcar', 'Endulzantes'),
('Edulcorante', 'Endulzantes'),
('Mayonesas', 'Aderezos Y Salsas'),
('Mostazas', 'Aderezos Y Salsas'),
('Ketchup', 'Aderezos Y Salsas'),
('Salsas Golf', 'Aderezos Y Salsas'),
('Café', 'Infusiones'),
('Mate', 'Infusiones'),
('Té', 'Infusiones'),
('Cacao En Polvo', 'Infusiones'),
('Conservas De Carne', 'Conservas'),
('Conservas De Frutas', 'Conservas'),
('Conservas De Pescado', 'Conservas'),
('Conservas Vegetal', 'Conservas'),
('Harina De Arroz', 'Harinas'),
('Harina De Trigo', 'Harinas'),
('Harina De Maíz', 'Harinas'),
('Premezcla', 'Harinas'),
('Aceitunas', 'Encurtidos'),
('Ají', 'Encurtidos'),
('Alcaparras', 'Encurtidos'),
('Alcaucil', 'Encurtidos'),
('Mermeladas', 'Mermeladas Y Dulces'),
('Dulce', 'Mermeladas Y Dulces'),
('Miel', 'Mermeladas Y Dulces'),
('Jalea', 'Mermeladas Y Dulces'),
('Puré De Tomate', 'Salsas Y Puré De Tomate'),
('Pulpa De Tomate', 'Salsas Y Puré De Tomate'),
('Extracto Tomates', 'Salsas Y Puré De Tomate'),
('Salsas Preparadas', 'Salsas Y Puré De Tomate'),
('Aceites', 'Aceites Y Condimentos'),
('Condimentos', 'Aceites Y Condimentos'),
('Vinagres', 'Aceites Y Condimentos'),
('Arroz', 'Arroz Y Legumbres'),
('Legumbres', 'Arroz Y Legumbres'),
('Especias', 'Especias'),
('Semillas', 'Especias'),
('Pasta Lista Y Rellena', 'Pasta Seca, Lista Y Rellenas'),
('Pastas Secas', 'Pasta Seca, Lista Y Rellenas'),
('Premezcla Postres', 'Polvo Para Postres Y Repostería'),
('Repostería', 'Polvo Para Postres Y Repostería'),
('Mezcla Lista', 'Sopas, Caldos, Puré Y Saborizantes'),
('Sopas Y Saborizantes', 'Sopas, Caldos, Puré Y Saborizantes'),
('Pan Rallado', 'Rebozador Y Pan Rallado'),
('Rebozador', 'Rebozador Y Pan Rallado'),
('Leche En Polvo', 'Leche En Polvo'),
('Productos Orgánicos', 'Productos Orgánicos'),
('Aguas', 'Bebidas Sin Alcohol'),
('Gaseosas', 'Bebidas Sin Alcohol'),
('Jugos', 'Bebidas Sin Alcohol'),
('Amargos', 'Bebidas Sin Alcohol'),
('Aperitivos', 'Bebidas Con Alcohol'),
('Cerveza', 'Bebidas Con Alcohol'),
('Champagne Y Espumantes', 'Bebidas Con Alcohol'),
('Vinos', 'Bebidas Con Alcohol'),
('Cremas', 'Lácteos'),
('Leches', 'Lácteos'),
('Yogures', 'Lácteos'),
('Dulce De Leche', 'Lácteos'),
('Fiambres', 'Fiambres'),
('Salchichas Y Pates', 'Fiambres'),
('Quesos Veganos', 'Quesos'),
('Quesos Especiales', 'Quesos'),
('Queso Rallado', 'Quesos'),
('Queso Crema Y Untables', 'Quesos'),
('Carnes', 'Carnicería'),
('Carnes Elaboradas', 'Carnicería'),
('Embutidos', 'Carnicería'),
('Granja', 'Carnicería'),
('Pollo Entero Fresco', 'Aves'),
('Pollo Trozado Fresco', 'Aves'),
('Pollo Entero Congelado', 'Aves'),
('Pollo Trozado Congelado', 'Aves'),
('Huevos Blanco', 'Huevos'),
('Huevos Color', 'Huevos'),
('Fideos Y Ñoquis', 'Pastas Frescas Y Tapas'),
('Pastas Rellenas', 'Pastas Frescas Y Tapas'),
('Tapas Para Pascualina Y Empanadas', 'Pastas Frescas Y Tapas'),
('Masas Preparadas', 'Pastas Frescas Y Tapas'),
('Pizzas, Tartas Y Empanadas', 'Comidas Elaboradas'),
('Platos Y Guarniciones', 'Comidas Elaboradas'),
('Listos Para Congelar', 'Comidas Elaboradas'),
('X Unidad - Porción', 'Comidas Elaboradas'),
('Frutas', 'Frutas Y Verduras'),
('Verduras', 'Frutas Y Verduras'),
('Frutas Secas Y Desecadas', 'Frutas Y Verduras'),
('Entero Eviscerado', 'Pescadería'),
('Filete', 'Pescadería'),
('Semiconserva', 'Pescadería'),
('Mariscos', 'Pescadería'),
('Pescado De Mar', 'Pescadería'),
('Rebozados', 'Pescadería'),
('Salmón Y Trucha', 'Pescadería'),
('Nuggets', 'Nuggets, Patitas Y Bocaditos'),
('Patitas', 'Nuggets, Patitas Y Bocaditos'),
('Bocaditos Y Formitas', 'Nuggets, Patitas Y Bocaditos'),
('Bastones De Mozzarella', 'Nuggets, Patitas Y Bocaditos'),
('Hamburguesas Y Medallones', 'Hamburguesas Y Milanesas'),
('Milanesas Y Supremas', 'Hamburguesas Y Milanesas'),
('Papas Bastón', 'Papas Congeladas / Fritas'),
('Papas Noisettes', 'Papas Congeladas / Fritas'),
('Papas Con Formas', 'Papas Congeladas / Fritas'),
('Papas Estilo Casero', 'Papas Congeladas / Fritas'),
('Postres Helados', 'Helados Y Postres'),
('Helados En Pote', 'Helados Y Postres'),
('Helados En Palito', 'Helados Y Postres'),
('Yogur Helado', 'Helados Y Postres'),
('Pizzas Y Pizzetas', 'Comidas Congeladas'),
('Pastas Congeladas', 'Comidas Congeladas'),
('Panificados Congelados', 'Comidas Congeladas'),
('Empanadas Y Tartas', 'Comidas Congeladas'),
('Acelga', 'Vegetales Congelados'),
('Cebollas', 'Vegetales Congelados'),
('Vegetales Para Saltear', 'Vegetales Congelados')) AS T(sub_category_level2_name, sub_category_level1_name)
         INNER JOIN public.sub_category_level1 S1 ON S1.name = T.sub_category_level1_name
WHERE NOT EXISTS (SELECT 1
                  FROM public.sub_category_level2 S2
                           INNER JOIN public.sub_category_level1 S1_E ON S2.sub_category_level1_id = S1_E.id
                  WHERE S2.name = T.sub_category_level2_name
                    AND S1_E.name = T.sub_category_level1_name);


INSERT INTO public.product(sub_category_level2_id, name)
SELECT SCL2.id,
       T.product_name
FROM (VALUES ('Servilletas', 'Servilletas'),
             ('Alfajor', 'Alfajores'),
             ('Chocolate Cobertura', 'Chocolates'),
             ('Chocolate Negro', 'Chocolates'),
             ('Chocolate Blanco', 'Chocolates'),
             ('Caramelos Masticables', 'Caramelos y Chupetines'),
             ('Chupetines', 'Caramelos y Chupetines'),
             ('Chicles', 'Chicles Y Pastillas'),
             ('Pastillas', 'Chicles Y Pastillas'),
             ('Galletitas', 'Galletitas'),
             ('Pan Francés', 'Panadería Propia'),
             ('Pan de Molde Integral', 'Panadería Propia'),
             ('Facturas de Crema', 'Panadería Propia'),
             ('Medialunas de Grasa', 'Panadería Propia'),
             ('Pan Lactal Clásico', 'Panadería Propia'),
             ('Baguettes', 'Panadería Propia'),
             ('Pan rallado fresco', 'Panadería Propia'),
             ('Chipás', 'Panadería Propia'),
             ('Grisines caseros', 'Panadería Propia'),
             ('Tortitas', 'Panadería Propia'),
             ('Pan de Miga', 'Panificados'),
             ('Pan de Hamburguesa', 'Panificados'),
             ('Pan de Pancho', 'Panificados'),
             ('Tostadas de Campo', 'Panificados'),
             ('Croissant envasado', 'Panificados'),
             ('Figacitas', 'Panificados'),
             ('Rapiditas', 'Panificados'),
             ('Pan Árabe', 'Panificados'),
             ('Pan Integral', 'Panificados'),
             ('Tortillas de Harina', 'Panificados'),
             ('Galletas de Agua', 'Galletas, Tostadas Y Grisines'),
             ('Galletas de Salvado', 'Galletas, Tostadas Y Grisines'),
             ('Tostadas Integrales', 'Galletas, Tostadas Y Grisines'),
             ('Grisines Comunes', 'Galletas, Tostadas Y Grisines'),
             ('Vainillas', 'Galletas, Tostadas Y Grisines'),
             ('Bizcochos de Grasa', 'Galletas, Tostadas Y Grisines'),
             ('Crackers', 'Galletas, Tostadas Y Grisines'),
             ('Galletas Marineras', 'Galletas, Tostadas Y Grisines'),
             ('Rebozador', 'Galletas, Tostadas Y Grisines'),
             ('Tostadas Clásicas', 'Galletas, Tostadas Y Grisines'),
             ('Papas Fritas Con Sal', 'Papas Fritas'),
             ('Papas Americanas', 'Papas Fritas'),
             ('Chips de Batata', 'Papas Fritas'),
             ('Papas de Corte Grueso', 'Papas Fritas'),
             ('Papas con Cebolla', 'Papas Fritas'),
             ('Papas Fritas Sin Sal', 'Papas Fritas'),
             ('Palitos Salados', 'Palitos'),
             ('Snacks Queso', 'Bastones De Maíz'),
             ('Conitos', 'Bastones De Maíz'),
             ('Bastones de Arroz inflado', 'Bastones De Maíz'),
             ('Crunchy', 'Bastones De Maíz'),
             ('Palitos de Maíz Saborizados', 'Bastones De Maíz'),
             ('Maní Salado', 'Maní'),
             ('Maní Tostado', 'Maní'),
             ('Maní con Chocolate', 'Maní'),
             ('Maní Pelado', 'Maní'),
             ('Maní Crocante', 'Maní'),
             ('Maní Japonés', 'Maní'),
             ('Maní Saborizado', 'Maní'),
             ('Mix de Frutos Secos', 'Maní'),
             ('Almendras', 'Maní'),
             ('Castañas de Cajú', 'Maní'),
             ('Palitos de Queso', 'Palitos'),
             ('Palitos de Zanahoria Deshidratada', 'Palitos'),
             ('Palitos de Salvado', 'Palitos'),
             ('Palitos Integrales', 'Palitos'),
             ('Palitos de Semillas', 'Palitos'),
             ('Palitos de Arroz', 'Palitos'),
             ('Palitos de Ajo', 'Palitos'),
             ('Palitos Sin Sal', 'Palitos'),
             ('Palitos de Orégano', 'Palitos'),
             ('Zucaritas', 'Cereales Con Azúcar'),
             ('Choco Krispis', 'Cereales Con Azúcar'),
             ('Nesquik Cereal', 'Cereales Con Azúcar'),
             ('Trix', 'Cereales Con Azúcar'),
             ('Froot Loops', 'Cereales Con Azúcar'),
             ('Copos de Azúcar', 'Cereales Con Azúcar'),
             ('Aros de Frutas', 'Cereales Con Azúcar'),
             ('Cereal de Miel', 'Cereales Con Azúcar'),
             ('K-Time', 'Cereales Con Azúcar'),
             ('Cereal de Chocolate', 'Cereales Con Azúcar'),
             ('Choco K', 'Cereales Infantiles'),
             ('Cereal de Estrellitas', 'Cereales Infantiles'),
             ('Cereal de Dinosaurios', 'Cereales Infantiles'),
             ('Cereal con Marshmallows', 'Cereales Infantiles'),
             ('Cereal de Frutilla', 'Cereales Infantiles'),
             ('Cereal de Vainilla', 'Cereales Infantiles'),
             ('Cereal de Letras', 'Cereales Infantiles'),
             ('Mini Aros', 'Cereales Infantiles'),
             ('Cereal de Bolitas', 'Cereales Infantiles'),
             ('K-Aros', 'Cereales Infantiles'),
             ('Special K', 'Break Saludables'),
             ('All-Bran', 'Break Saludables'),
             ('Avena Instantánea', 'Break Saludables'),
             ('Granola Clásica', 'Break Saludables'),
             ('Muesli', 'Break Saludables'),
             ('Fibra Max', 'Break Saludables'),
             ('Cereal de Salvado', 'Break Saludables'),
             ('Copos sin Azúcar', 'Break Saludables'),
             ('Cereal de Quinoa', 'Break Saludables'),
             ('Cereal con Frutos Secos', 'Break Saludables'),
             ('Barra de Arroz con Chocolate', 'Barras De Arroz'),
             ('Barra de Cereal con Frutas', 'Barras De Arroz'),
             ('Barra de Semillas', 'Barras De Arroz'),
             ('Barra de Arroz de Miel', 'Barras De Arroz'),
             ('Barra de Arroz Light', 'Barras De Arroz'),
             ('Barra de Cereal Sin TACC', 'Barras De Arroz'),
             ('Barra Proteica', 'Barras De Arroz'),
             ('Barra de Arroz con Dulce de Leche', 'Barras De Arroz'),
             ('Barra de Sésamo', 'Barras De Arroz'),
             ('Barra de Arroz de Coco', 'Barras De Arroz'),
             ('Azúcar Blanca Común', 'Azúcar'),
             ('Azúcar Rubia', 'Azúcar'),
             ('Azúcar Impalpable', 'Azúcar'),
             ('Azúcar Integral Orgánica', 'Azúcar'),
             ('Azúcar Mascabo', 'Azúcar'),
             ('Azúcar Ledesma', 'Azúcar'),
             ('Azúcar Chango', 'Azúcar'),
             ('Azúcar Light', 'Azúcar'),
             ('Azúcar de Coco', 'Azúcar'),
             ('Azúcar Fructosa', 'Azúcar'),
             ('Hileret Zucra', 'Edulcorante'),
             ('Stevia Líquida', 'Edulcorante'),
             ('Edulcorante en Pastillas', 'Edulcorante'),
             ('Hileret Clásico', 'Edulcorante'),
             ('Sucralosa', 'Edulcorante'),
             ('Sacarina', 'Edulcorante'),
             ('Aspartamo', 'Edulcorante'),
             ('Edulcorante de Agave', 'Edulcorante'),
             ('Miel de Caña', 'Edulcorante'),
             ('Jarabe de Arce', 'Edulcorante'),
             ('Hellmanns Clásica', 'Mayonesas'),
             ('Fanacoa Light', 'Mayonesas'),
             ('Mayoliva', 'Mayonesas'),
             ('Mayonesa Casera', 'Mayonesas'),
             ('Mayonesa de Oliva', 'Mayonesas'),
             ('Mayonesa Saborizada', 'Mayonesas'),
             ('Mayonesa Ahumada', 'Mayonesas'),
             ('Mayonesa Vegana', 'Mayonesas'),
             ('Mayonesa en Sachet', 'Mayonesas'),
             ('Mayonesa de Ajo', 'Mayonesas'),
             ('Mostaza Savora', 'Mostazas'),
             ('Mostaza Dijon', 'Mostazas'),
             ('Mostaza a la Antigua', 'Mostazas'),
             ('Mostaza con Miel', 'Mostazas'),
             ('Mostaza Inglesa', 'Mostazas'),
             ('Mostaza de Cerveza', 'Mostazas'),
             ('Mostaza Americana', 'Mostazas'),
             ('Mostaza Picante', 'Mostazas'),
             ('Mostaza en Polvo', 'Mostazas'),
             ('Mostaza Francesa', 'Mostazas'),
             ('Ketchup Hellmanns', 'Ketchup'),
             ('Ketchup Heinz', 'Ketchup'),
             ('Ketchup Light', 'Ketchup'),
             ('Ketchup Picante', 'Ketchup'),
             ('Salsa Barbacoa', 'Ketchup'),
             ('Salsa de Tomate Ketchup', 'Ketchup'),
             ('Ketchup Orgánico', 'Ketchup'),
             ('Ketchup Artesanal', 'Ketchup'),
             ('Ketchup con Especias', 'Ketchup'),
             ('Ketchup Sin Sal', 'Ketchup'),
             ('Salsa Golf Clásica', 'Salsas Golf'),
             ('Salsa Golf Light', 'Salsas Golf'),
             ('Aderezo Mil Islas', 'Salsas Golf'),
             ('Aderezo Ranch', 'Salsas Golf'),
             ('Aderezo César', 'Salsas Golf'),
             ('Salsa Tártara', 'Salsas Golf'),
             ('Salsa Rosada', 'Salsas Golf'),
             ('Aderezo de Palta', 'Salsas Golf'),
             ('Salsa Alioli', 'Salsas Golf'),
             ('Aderezo de Queso Azul', 'Salsas Golf'),
             ('Café Molido Clásico', 'Café'),
             ('Café Instantáneo', 'Café'),
             ('Cápsulas de Espresso', 'Café'),
             ('Café de Grano Tostado', 'Café'),
             ('Café Descafeinado', 'Café'),
             ('Café Saborizado', 'Café'),
             ('Café con Leche en Polvo', 'Café'),
             ('Café Filtrado', 'Café'),
             ('Café Torrado', 'Café'),
             ('Café Soluble', 'Café'),
             ('Yerba Mate con Palo', 'Mate'),
             ('Yerba Mate Despalada', 'Mate'),
             ('Yerba Mate Compuesta', 'Mate'),
             ('Yerba Mate Saborizada (Naranja/Menta)', 'Mate'),
             ('Mate Cocido en Saquitos', 'Mate'),
             ('Yerba Orgánica', 'Mate'),
             ('Mate Listo', 'Mate'),
             ('Tereré', 'Mate'),
             ('Yerba sin TACC', 'Mate'),
             ('Yerba Suave', 'Mate'),
             ('Té Negro Clásico', 'Té'),
             ('Té Verde', 'Té'),
             ('Té de Manzanilla', 'Té'),
             ('Té de Frutas Rojas', 'Té'),
             ('Té de Menta', 'Té'),
             ('Té Earl Grey', 'Té'),
             ('Té de Ceylán', 'Té'),
             ('Té Rojo', 'Té'),
             ('Té de Hierbas Digestivas', 'Té'),
             ('Té Blanco', 'Té'),
             ('Cacao en Polvo Nesquik', 'Cacao En Polvo'),
             ('Cacao en Polvo Toddy', 'Cacao En Polvo'),
             ('Cacao Amargo', 'Cacao En Polvo'),
             ('Cacao Instantáneo con Vitaminas', 'Cacao En Polvo'),
             ('Chocolate en Taza', 'Cacao En Polvo'),
             ('Cacao Semi Amargo', 'Cacao En Polvo'),
             ('Cacao para Repostería', 'Cacao En Polvo'),
             ('Cacao Orgánico', 'Cacao En Polvo'),
             ('Cacao Bajo en Grasa', 'Cacao En Polvo'),
             ('Cacao Dietético', 'Cacao En Polvo'),
             ('Pescado en Aceite (Atún)', 'Conservas De Carne'),
             ('Caballa en Tomate', 'Conservas De Carne'),
             ('Corned Beef', 'Conservas De Carne'),
             ('Salmón en Lata', 'Conservas De Carne'),
             ('Sardinas en Aceite', 'Conservas De Carne'),
             ('Anchoas en Aceite', 'Conservas De Carne'),
             ('Pescado en Agua', 'Conservas De Carne'),
             ('Paté de Ave', 'Conservas De Carne'),
             ('Carnes Curadas en Lata', 'Conservas De Carne'),
             ('Duraznos en Almíbar', 'Conservas De Frutas'),
             ('Coctel de Frutas', 'Conservas De Frutas'),
             ('Ananá en Rodajas', 'Conservas De Frutas'),
             ('Higos en Almíbar', 'Conservas De Frutas'),
             ('Cerezas al Marraschino', 'Conservas De Frutas'),
             ('Frutillas en Jarabe', 'Conservas De Frutas'),
             ('Peras en Almíbar', 'Conservas De Frutas'),
             ('Membrillo en Lata', 'Conservas De Frutas'),
             ('Frutas al Natural', 'Conservas De Frutas'),
             ('Compota de Manzana', 'Conservas De Frutas'),
             ('Atún al Natural', 'Conservas De Pescado'),
             ('Sardinas en Aceite', 'Conservas De Pescado'),
             ('Caballa en Escabeche', 'Conservas De Pescado'),
             ('Anchoas en Aceite', 'Conservas De Pescado'),
             ('Salmón en Lata', 'Conservas De Pescado'),
             ('Pescado a la Mostaza', 'Conservas De Pescado'),
             ('Filetes de Caballa', 'Conservas De Pescado'),
             ('Atún en Aceite', 'Conservas De Pescado'),
             ('Paté de Atún', 'Conservas De Pescado'),
             ('Huevas de Pescado', 'Conservas De Pescado'),
             ('Choclo en Grano', 'Conservas Vegetal'),
             ('Arvejas en Lata', 'Conservas Vegetal'),
             ('Lentejas en Lata', 'Conservas Vegetal'),
             ('Garbanzos en Lata', 'Conservas Vegetal'),
             ('Porotos de Manteca', 'Conservas Vegetal'),
             ('Pickles', 'Conservas Vegetal'),
             ('Espárragos en Frasco', 'Conservas Vegetal'),
             ('Remolacha en Rodajas', 'Conservas Vegetal'),
             ('Zanahoria en Cubos', 'Conservas Vegetal'),
             ('Hongos en Conserva', 'Conservas Vegetal'),
             ('Harina de Arroz Común', 'Harina De Arroz'),
             ('Harina de Arroz Integral', 'Harina De Arroz'),
             ('Premezcla Sin TACC', 'Harina De Arroz'),
             ('Harina de Tapioca', 'Harina De Arroz'),
             ('Almidón de Maíz', 'Harina De Arroz'),
             ('Harina de Mandioca', 'Harina De Arroz'),
             ('Harina de Sarraceno', 'Harina De Arroz'),
             ('Harina de Goma Xantana', 'Harina De Arroz'),
             ('Harina de Garbanzo', 'Harina De Arroz'),
             ('Harina de Sorgo', 'Harina De Arroz'),
             ('Harina 000', 'Harina De Trigo'),
             ('Harina 0000', 'Harina De Trigo'),
             ('Harina Integral', 'Harina De Trigo'),
             ('Harina Leudante', 'Harina De Trigo'),
             ('Harina de Fuerza', 'Harina De Trigo'),
             ('Harina de Centeno', 'Harina De Trigo'),
             ('Harina para Pizza', 'Harina De Trigo'),
             ('Harina de Trigo Candeal', 'Harina De Trigo'),
             ('Harina de Trigo Sarraceno', 'Harina De Trigo'),
             ('Harina para Pastas', 'Harina De Trigo'),
             ('Polenta Clásica', 'Harina De Maíz'),
             ('Harina para Arepas', 'Harina De Maíz'),
             ('Maicena', 'Harina De Maíz'),
             ('Harina de Maíz Precocida', 'Harina De Maíz'),
             ('Flocos de Maíz', 'Harina De Maíz'),
             ('Sémola de Maíz', 'Harina De Maíz'),
             ('Harina de Maíz para Tamales', 'Harina De Maíz'),
             ('Harina de Maíz Morado', 'Harina De Maíz'),
             ('Harina de Maíz Frita', 'Harina De Maíz'),
             ('Harina de Maíz Tostado', 'Harina De Maíz'),
             ('Premezcla para Bizcochuelo', 'Premezcla'),
             ('Premezcla para Pan Sin TACC', 'Premezcla'),
             ('Premezcla para Pizza', 'Premezcla'),
             ('Premezcla para Brownies', 'Premezcla'),
             ('Premezcla para Muffins', 'Premezcla'),
             ('Premezcla para Panqueques', 'Premezcla'),
             ('Premezcla para Waffles', 'Premezcla'),
             ('Premezcla para Galletas', 'Premezcla'),
             ('Premezcla para Chipá', 'Premezcla'),
             ('Premezcla para Budín', 'Premezcla'),
             ('Aceitunas Verdes', 'Aceitunas'),
             ('Aceitunas Negras', 'Aceitunas'),
             ('Aceitunas Rellenas', 'Aceitunas'),
             ('Aceitunas Descarozadas', 'Aceitunas'),
             ('Pasta de Aceitunas', 'Aceitunas'),
             ('Aceitunas Griegas', 'Aceitunas'),
             ('Aceitunas Sevillanas', 'Aceitunas'),
             ('Aceitunas con Ají', 'Aceitunas'),
             ('Aceitunas para Picada', 'Aceitunas'),
             ('Aceitunas de Mesa', 'Aceitunas'),
             ('Ají Molido', 'Ají'),
             ('Pimentón Picante', 'Ají'),
             ('Merquén', 'Ají'),
             ('Ají de la Mala Palabra', 'Ají'),
             ('Pimiento Rojo en Conserva', 'Ají'),
             ('Ají Amarillo', 'Ají'),
             ('Ají Cayena', 'Ají'),
             ('Guindillas en Vinagre', 'Ají'),
             ('Ají Jalapeño', 'Ají'),
             ('Ají Dulce', 'Ají'),
             ('Alcaparras en Vinagre', 'Alcaparras'),
             ('Alcaparrones', 'Alcaparras'),
             ('Alcaparras Saladas', 'Alcaparras'),
             ('Alcaparras Deshidratadas', 'Alcaparras'),
             ('Alcaparras Grandes', 'Alcaparras'),
             ('Alcaparras Pequeñas', 'Alcaparras'),
             ('Alcaparras en Aceite', 'Alcaparras'),
             ('Pasta de Alcaparras', 'Alcaparras'),
             ('Alcaparras con Finas Hierbas', 'Alcaparras'),
             ('Alcaparras Orgánicas', 'Alcaparras'),
             ('Corazones de Alcaucil', 'Alcaucil'),
             ('Alcauciles en Aceite', 'Alcaucil'),
             ('Fondos de Alcaucil', 'Alcaucil'),
             ('Alcauciles Marinados', 'Alcaucil'),
             ('Pasta de Alcaucil', 'Alcaucil'),
             ('Alcauciles en Agua', 'Alcaucil'),
             ('Alcauciles con Especias', 'Alcaucil'),
             ('Alcauciles Grillados', 'Alcaucil'),
             ('Alcauciles en Rodajas', 'Alcaucil'),
             ('Alcauciles Congelados', 'Alcaucil'),
             ('Mermelada de Durazno', 'Mermeladas'),
             ('Mermelada de Frutilla', 'Mermeladas'),
             ('Mermelada de Ciruela', 'Mermeladas'),
             ('Mermelada de Naranja', 'Mermeladas'),
             ('Mermelada de Damasco', 'Mermeladas'),
             ('Mermelada Light', 'Mermeladas'),
             ('Mermelada de Higo', 'Mermeladas'),
             ('Mermelada de Arándanos', 'Mermeladas'),
             ('Mermelada de Zapallo', 'Mermeladas'),
             ('Mermelada de Pera', 'Mermeladas'),
             ('Dulce de Batata', 'Dulce'),
             ('Dulce de Membrillo', 'Dulce'),
             ('Pasta de Maní', 'Dulce'),
             ('Pasta de Avellanas', 'Dulce'),
             ('Crema de Avellanas y Cacao', 'Dulce'),
             ('Miel', 'Miel'),
             ('Jarabe de Maíz', 'Miel'),
             ('Jalea de Membrillo', 'Jalea'),
             ('Puré de Tomate Clásico', 'Puré De Tomate'),
             ('Puré de Tomate Triturado', 'Puré De Tomate'),
             ('Puré de Tomate Condimentado', 'Puré De Tomate'),
             ('Puré de Tomate Orgánico', 'Puré De Tomate'),
             ('Puré de Tomate en Botella', 'Puré De Tomate'),
             ('Tomates Pelados Enteros', 'Puré De Tomate'),
             ('Tomate en Cubos', 'Puré De Tomate'),
             ('Puré de Tomate Light', 'Puré De Tomate'),
             ('Puré de Tomate con Albahaca', 'Puré De Tomate'),
             ('Puré de Tomate Sin Sal', 'Puré De Tomate'),
             ('Pulpa de Tomate Clásica', 'Pulpa De Tomate'),
             ('Pulpa de Tomate Densa', 'Pulpa De Tomate'),
             ('Pulpa de Tomate con Semillas', 'Pulpa De Tomate'),
             ('Pulpa de Tomate Para Pizza', 'Pulpa De Tomate'),
             ('Pulpa de Tomate Saborizada', 'Pulpa De Tomate'),
             ('Pulpa de Tomate en Tetra', 'Pulpa De Tomate'),
             ('Pulpa de Tomate en Lata', 'Pulpa De Tomate'),
             ('Tomates Secos', 'Pulpa De Tomate'),
             ('Tomates Cherry en Frasco', 'Pulpa De Tomate'),
             ('Tomate Perita', 'Pulpa De Tomate'),
             ('Extracto de Tomate Concentrado', 'Extracto Tomates'),
             ('Pasta de Tomate', 'Extracto Tomates'),
             ('Doble Extracto de Tomate', 'Extracto Tomates'),
             ('Extracto de Tomate en Tubo', 'Extracto Tomates'),
             ('Concentrado de Tomate', 'Extracto Tomates'),
             ('Extracto de Pimientos', 'Extracto Tomates'),
             ('Pasta de Ají', 'Extracto Tomates'),
             ('Pasta de Tomate con Especias', 'Extracto Tomates'),
             ('Pasta de Tomate Bajo en Sodio', 'Extracto Tomates'),
             ('Extracto de Tomate Orgánico', 'Extracto Tomates'),
             ('Salsa Fileto', 'Salsas Preparadas'),
             ('Salsa Bolognesa', 'Salsas Preparadas'),
             ('Salsa Blanca', 'Salsas Preparadas'),
             ('Salsa de Soja', 'Salsas Preparadas'),
             ('Salsa de Hongos', 'Salsas Preparadas'),
             ('Salsa Pesto', 'Salsas Preparadas'),
             ('Salsa Agrodulce', 'Salsas Preparadas'),
             ('Salsa Bechamel', 'Salsas Preparadas'),
             ('Salsa de Queso', 'Salsas Preparadas'),
             ('Salsa Napolitana', 'Salsas Preparadas'),
             ('Aceite de Girasol', 'Aceites'),
             ('Aceite de Oliva Extra Virgen', 'Aceites'),
             ('Aceite de Maíz', 'Aceites'),
             ('Aceite de Soja', 'Aceites'),
             ('Aceite de Uva', 'Aceites'),
             ('Aceite de Coco', 'Aceites'),
             ('Aceite de Sésamo', 'Aceites'),
             ('Aceite de Palta', 'Aceites'),
             ('Aceite de Canola', 'Aceites'),
             ('Aceite Aromatizado (Ajo/Romero)', 'Aceites'),
             ('Sal Fina', 'Condimentos'),
             ('Sal Gruesa', 'Condimentos'),
             ('Pimienta Negra Molida', 'Condimentos'),
             ('Ají Molido', 'Condimentos'),
             ('Orégano', 'Condimentos'),
             ('Pimentón Dulce', 'Condimentos'),
             ('Comino', 'Condimentos'),
             ('Laurel', 'Condimentos'),
             ('Nuez Moscada', 'Condimentos'),
             ('Canela', 'Condimentos'),
             ('Vinagre de Alcohol', 'Vinagres'),
             ('Vinagre de Vino Tinto', 'Vinagres'),
             ('Vinagre de Manzana', 'Vinagres'),
             ('Vinagre Balsámico', 'Vinagres'),
             ('Vinagre de Jerez', 'Vinagres'),
             ('Aceto Balsámico', 'Vinagres'),
             ('Vinagre de Arroz', 'Vinagres'),
             ('Reducción de Aceto', 'Vinagres'),
             ('Vinagre Blanco', 'Vinagres'),
             ('Vinagre de Miel', 'Vinagres'),
             ('Arroz Blanco', 'Arroz'),
             ('Arroz Integral', 'Arroz'),
             ('Arroz Parboiled', 'Arroz'),
             ('Arroz Doble Carolina', 'Arroz'),
             ('Arroz Yamaní', 'Arroz'),
             ('Arroz para Sushi', 'Arroz'),
             ('Arroz Basmati', 'Arroz'),
             ('Arroz Arborio (Rissotto)', 'Arroz'),
             ('Arroz con Vegetales', 'Arroz'),
             ('Arroz Saborizado', 'Arroz'),
             ('Lentejas', 'Legumbres'),
             ('Garbanzos', 'Legumbres'),
             ('Porotos Negros', 'Legumbres'),
             ('Porotos Alubia', 'Legumbres'),
             ('Soja', 'Legumbres'),
             ('Arvejas Secas', 'Legumbres'),
             ('Porotos de Pallares', 'Legumbres'),
             ('Lentejas Rojas', 'Legumbres'),
             ('Arvejas Partidas', 'Legumbres'),
             ('Mix de Legumbres', 'Legumbres'),
             ('Pimienta Blanca', 'Especias'),
             ('Provenzal', 'Especias'),
             ('Pimentón Ahumado', 'Especias'),
             ('Condimento para pizza', 'Especias'),
             ('Chimichurri', 'Especias'),
             ('Orgéno', 'Especias'),
             ('Tomillo', 'Especias'),
             ('Curry', 'Especias'),
             ('Azafrán', 'Especias'),
             ('Jengibre en Polvo', 'Especias'),
             ('Clavo de Olor', 'Especias'),
             ('Pimentón Dulce', 'Especias'),
             ('Nuez Moscada', 'Especias'),
             ('Vainilla', 'Especias'),
             ('Canela', 'Especias'),
             ('Semillas de Sésamo', 'Semillas'),
             ('Semillas de Chía', 'Semillas'),
             ('Semillas de Lino', 'Semillas'),
             ('Semillas de Girasol', 'Semillas'),
             ('Sorrentinos', 'Pasta Lista Y Rellena'),
             ('Ravioles', 'Pasta Lista Y Rellena'),
             ('Ñoquis Frescos', 'Pasta Lista Y Rellena'),
             ('Lasagna', 'Pasta Lista Y Rellena'),
             ('Canelones', 'Pasta Lista Y Rellenas'),
             ('Tortellinis', 'Pasta Lista Y Rellenas'),
             ('Gnocchi Rellenos', 'Pasta Lista Y Rellenas'),
             ('Pasta Fresca al Huevo', 'Pasta Lista Y Rellenas'),
             ('Tapas de Empanadas', 'Pasta Lista Y Rellenas'),
             ('Masa para Tarta', 'Pasta Lista Y Rellenas'),
             ('Fideos Spaghetti', 'Pastas Secas'),
             ('Fideos Tirabuzón', 'Pastas Secas'),
             ('Fideos Penne Rigate', 'Pastas Secas'),
             ('Fideos Tallarines', 'Pastas Secas'),
             ('Fideos Mostacholes', 'Pastas Secas'),
             ('Fideos Codo', 'Pastas Secas'),
             ('Fideos de Arroz', 'Pastas Secas'),
             ('Fideos Integrales', 'Pastas Secas'),
             ('Fideos para Sopa', 'Pastas Secas'),
             ('Fideos Semolados', 'Pastas Secas'),
             ('Flan', 'Premezcla Postres'),
             ('Gelatina', 'Premezcla Postres'),
             ('Mousse', 'Premezcla Postres'),
             ('Postre de Vainilla', 'Premezcla Postres'),
             ('Postre de Chocolate', 'Premezcla Postres'),
             ('Postre con Caramelo', 'Premezcla Postres'),
             ('Postre Light', 'Premezcla Postres'),
             ('Postre de Café', 'Premezcla Postres'),
             ('Postre de Frutilla', 'Premezcla Postres'),
             ('Crema Chantilly en Polvo', 'Premezcla Postres'),
             ('Polvo de Hornear', 'Repostería'),
             ('Bicarbonato de Sodio', 'Repostería'),
             ('Levadura en Polvo', 'Repostería'),
             ('Esencia de Vainilla', 'Repostería'),
             ('Cacao Amargo', 'Repostería'),
             ('Colorantes Comestibles', 'Repostería'),
             ('Granas de Colores', 'Repostería'),
             ('Azúcar Impalpable', 'Repostería'),
             ('Glaseado Listo', 'Repostería'),
             ('Decoraciones Comestibles', 'Repostería'),
             ('Sopa Instantánea', 'Mezcla Lista'),
             ('Caldo en Cubos', 'Mezcla Lista'),
             ('Puré de Papa Instantáneo', 'Mezcla Lista'),
             ('Sopa Deshidratada', 'Mezcla Lista'),
             ('Saborizante para Caldos', 'Mezcla Lista'),
             ('Crema de Choclo', 'Mezcla Lista'),
             ('Sopa Crema de Calabaza', 'Mezcla Lista'),
             ('Sopa de Verduras', 'Mezcla Lista'),
             ('Sopa de Tomate', 'Mezcla Lista'),
             ('Base para Salsas', 'Mezcla Lista'),
             ('Caldo de Verduras', 'Sopas Y Saborizantes'),
             ('Caldo de Gallina', 'Sopas Y Saborizantes'),
             ('Caldo de Carne', 'Sopas Y Saborizantes'),
             ('Sopa en Sobres', 'Sopas Y Saborizantes'),
             ('Sopa de Letras', 'Sopas Y Saborizantes'),
             ('Fideos para Sopa', 'Sopas Y Saborizantes'),
             ('Saborizante en Polvo (Parmesano/Panceta)', 'Sopas Y Saborizantes'),
             ('Concentrado de Tomate', 'Sopas Y Saborizantes'),
             ('Extracto de Carne', 'Sopas Y Saborizantes'),
             ('Cubitos de Sabor', 'Sopas Y Saborizantes'),
             ('Pan Rallado Clásico', 'Pan Rallado'),
             ('Pan Rallado Integral', 'Pan Rallado'),
             ('Pan Rallado con Perejil y Ajo', 'Pan Rallado'),
             ('Rebozador Crocante', 'Pan Rallado'),
             ('Pan Rallado Sin TACC', 'Pan Rallado'),
             ('Harina para Rebozar', 'Pan Rallado'),
             ('Pan Rallado de Semillas', 'Pan Rallado'),
             ('Rebozador de Maíz', 'Pan Rallado'),
             ('Panko', 'Pan Rallado'),
             ('Rebozador Especial', 'Pan Rallado'),
             ('Rebozador Clásico', 'Rebozador'),
             ('Rebozador Saborizado (Pimentón)', 'Rebozador'),
             ('Rebozador para Pescado', 'Rebozador'),
             ('Rebozador para Milanesas', 'Rebozador'),
             ('Rebozador de Pan Viejo', 'Rebozador'),
             ('Rebozador de Salvado', 'Rebozador'),
             ('Rebozador de Avena', 'Rebozador'),
             ('Rebozador de Queso', 'Rebozador'),
             ('Rebozador de Hierbas', 'Rebozador'),
             ('Rebozador para Nuggets', 'Rebozador'),
             ('Leche en Polvo Entera', 'Leche En Polvo'),
             ('Leche en Polvo Descremada', 'Leche En Polvo'),
             ('Café Orgánico', 'Productos Orgánicos'),
             ('Aceite de Oliva Orgánico', 'Productos Orgánicos'),
             ('Miel Orgánica', 'Productos Orgánicos'),
             ('Yerba Mate Orgánica', 'Productos Orgánicos'),
             ('Harina Integral Orgánica', 'Productos Orgánicos'),
             ('Legumbres Orgánicas', 'Productos Orgánicos'),
             ('Azúcar Mascabo Orgánica', 'Productos Orgánicos'),
             ('Té Verde Orgánico', 'Productos Orgánicos'),
             ('Huevos de Campo Orgánicos', 'Productos Orgánicos'),
             ('Chocolate Orgánico', 'Productos Orgánicos'),
             ('Agua Mineral con Gas', 'Aguas'),
             ('Agua Mineral sin Gas', 'Aguas'),
             ('Agua Saborizada Limón', 'Aguas'),
             ('Agua con Gas Light', 'Aguas'),
             ('Agua Destilada', 'Aguas'),
             ('Agua de Coco', 'Aguas'),
             ('Agua Saborizada Naranja', 'Aguas'),
             ('Agua Potable', 'Aguas'),
             ('Agua con Electrolitos', 'Aguas'),
             ('Agua de Vertiente', 'Aguas'),
             ('Coca-Cola Clásica', 'Gaseosas'),
             ('Pepsi Clásica', 'Gaseosas'),
             ('Fanta', 'Gaseosas'),
             ('Sprite', 'Gaseosas'),
             ('Pepsi Light', 'Gaseosas'),
             ('7up', 'Gaseosas'),
             ('Gaseosa Lima-Limón', 'Gaseosas'),
             ('Gaseosa Pomelo', 'Gaseosas'),
             ('Tónica', 'Gaseosas'),
             ('Gaseosa Cola Zero', 'Gaseosas'),
             ('Ginger Ale', 'Gaseosas'),
             ('Jugo de Naranja Concentrado', 'Jugos'),
             ('Jugo de Manzana Tetra', 'Jugos'),
             ('Jugo de Durazno Light', 'Jugos'),
             ('Jugo de Tomate', 'Jugos'),
             ('Jugo Multifruta', 'Jugos'),
             ('Jugo de Limón Exprimido', 'Jugos'),
             ('Jugo de Ananá', 'Jugos'),
             ('Jugo de Uva', 'Jugos'),
             ('Jugo en Polvo', 'Jugos'),
             ('Jugo Detox', 'Jugos'),
             ('Amargo Obrero', 'Amargos'),
             ('Cinzano', 'Amargos'),
             ('Gancia', 'Amargos'),
             ('Aperitivo Fernet', 'Amargos'),
             ('Bitter', 'Amargos'),
             ('Hesperidina', 'Amargos'),
             ('Martini', 'Amargos'),
             ('Campari', 'Amargos'),
             ('Aperitivo a Base de Hierbas', 'Amargos'),
             ('Vermouth', 'Amargos'),
             ('Fernet con Coca', 'Aperitivos'),
             ('Cynar', 'Aperitivos'),
             ('Aperol Spritz', 'Aperitivos'),
             ('Gancia Batido', 'Aperitivos'),
             ('Vermouth Rojo', 'Aperitivos'),
             ('Vermouth Blanco', 'Aperitivos'),
             ('Bitter Rojo', 'Aperitivos'),
             ('Campari Orange', 'Aperitivos'),
             ('Licor de Huevo', 'Aperitivos'),
             ('Oporto', 'Aperitivos'),
             ('Cerveza Rubia Clásica', 'Cerveza'),
             ('Cerveza Negra', 'Cerveza'),
             ('Cerveza Artesanal IPA', 'Cerveza'),
             ('Cerveza Lager', 'Cerveza'),
             ('Cerveza Stout', 'Cerveza'),
             ('Cerveza Sin Alcohol', 'Cerveza'),
             ('Cerveza Roja', 'Cerveza'),
             ('Cerveza Porter', 'Cerveza'),
             ('Cerveza en Lata', 'Cerveza'),
             ('Cerveza en Porrón', 'Cerveza'),
             ('Champagne Brut Nature', 'Champagne Y Espumantes'),
             ('Vino Espumante Extra Brut', 'Champagne Y Espumantes'),
             ('Prosecco', 'Champagne Y Espumantes'),
             ('Espumante Rosé', 'Champagne Y Espumantes'),
             ('Cava', 'Champagne Y Espumantes'),
             ('Asti Spumante', 'Champagne Y Espumantes'),
             ('Champagne Dulce', 'Champagne Y Espumantes'),
             ('Sidra de Manzana', 'Champagne Y Espumantes'),
             ('Champagne Demi Sec', 'Champagne Y Espumantes'),
             ('Vino Gasificado', 'Champagne Y Espumantes'),
             ('Vino Tinto Malbec', 'Vinos'),
             ('Vino Blanco Chardonnay', 'Vinos'),
             ('Vino Rosado', 'Vinos'),
             ('Vino Torrontés', 'Vinos'),
             ('Vino Cabernet Sauvignon', 'Vinos'),
             ('Vino Syrah', 'Vinos'),
             ('Vino Merlot', 'Vinos'),
             ('Vino Dulce Cosecha Tardía', 'Vinos'),
             ('Vino en Caja', 'Vinos'),
             ('Vino Tinto Joven', 'Vinos'),
             ('Crema de Leche Clásica', 'Cremas'),
             ('Crema de Leche Light', 'Cremas'),
             ('Crema para Batir', 'Cremas'),
             ('Crema Doble', 'Cremas'),
             ('Crema Agria', 'Cremas'),
             ('Crema Vegetal', 'Cremas'),
             ('Crema de Café', 'Cremas'),
             ('Crema de Leche en Aerosol', 'Cremas'),
             ('Media Crema', 'Cremas'),
             ('Crema Fresca', 'Cremas'),
             ('Leche Entera', 'Leches'),
             ('Leche Descremada', 'Leches'),
             ('Leche Semidescremada', 'Leches'),
             ('Leche Sin Lactosa', 'Leches'),
             ('Leche Fortificada', 'Leches'),
             ('Leche Chocolatada', 'Leches'),
             ('Leche de Almendras', 'Leches'),
             ('Leche de Soja', 'Leches'),
             ('Leche de Avena', 'Leches'),
             ('Leche de Arroz', 'Leches'),
             ('Yogur Entero', 'Yogures'),
             ('Yogur Descremado', 'Yogures'),
             ('Yogur con Cereales', 'Yogures'),
             ('Yogur Bebible', 'Yogures'),
             ('Yogur Griego', 'Yogures'),
             ('Yogur con Frutas', 'Yogures'),
             ('Yogur Natural', 'Yogures'),
             ('Yogur con Probióticos', 'Yogures'),
             ('Postre Lácteo', 'Yogures'),
             ('Yogur con Dulce de Leche', 'Yogures'),
             ('Dulce de Leche Clásico', 'Dulce De Leche'),
             ('Dulce de Leche Repostero', 'Dulce De Leche'),
             ('Dulce de Leche Light', 'Dulce De Leche'),
             ('Dulce de Leche con Chocolate', 'Dulce De Leche'),
             ('Dulce de Leche de Cabra', 'Dulce De Leche'),
             ('Dulce de Leche Artesanal', 'Dulce De Leche'),
             ('Leche Condensada', 'Dulce De Leche'),
             ('Manjar', 'Dulce De Leche'),
             ('Mermelada de Leche', 'Dulce De Leche'),
             ('Dulce de Leche en Pote', 'Dulce De Leche'),
             ('Jamón Cocido', 'Fiambres'),
             ('Paleta', 'Fiambres'),
             ('Jamón Crudo', 'Fiambres'),
             ('Salame', 'Fiambres'),
             ('Mortadela', 'Fiambres'),
             ('Bondiola', 'Fiambres'),
             ('Pastrón', 'Fiambres'),
             ('Lomito Ahumado', 'Fiambres'),
             ('Salchichón Primavera', 'Fiambres'),
             ('Pavita', 'Fiambres'),
             ('Salchichas de Viena', 'Salchichas Y Pates'),
             ('Salchichas Alemanas', 'Salchichas Y Pates'),
             ('Paté de Foie', 'Salchichas Y Pates'),
             ('Paté de Campaña', 'Salchichas Y Pates'),
             ('Salchichas Parrilleras', 'Salchichas Y Pates'),
             ('Salchichas de Pollo', 'Salchichas Y Pates'),
             ('Salchichas de Cerdo', 'Salchichas Y Pates'),
             ('Salchichas Vegetarianas', 'Salchichas Y Pates'),
             ('Paté de Atún', 'Salchichas Y Pates'),
             ('Paté de Pollo', 'Salchichas Y Pates'),
             ('Queso Vegano', 'Quesos Veganos'),
             ('Queso Roquefort', 'Quesos Especiales'),
             ('Queso Brie', 'Quesos Especiales'),
             ('Queso Camembert', 'Quesos Especiales'),
             ('Queso Parmesano', 'Quesos Especiales'),
             ('Queso Provolone', 'Quesos Especiales'),
             ('Provoletas', 'Quesos Especiales'),
             ('Queso Gouda', 'Quesos Especiales'),
             ('Queso Cheddar', 'Quesos Especiales'),
             ('Queso Mozzarella', 'Quesos Especiales'),
             ('Queso Azul', 'Quesos Especiales'),
             ('Queso Fontina', 'Quesos Especiales'),
             ('Queso Sardo', 'Quesos Especiales'),
             ('Queso Rallado Sardo', 'Queso Rallado'),
             ('Queso Rallado Parmesano', 'Queso Rallado'),
             ('Queso Rallado Reggianito', 'Queso Rallado'),
             ('Queso Rallado en Sobre', 'Queso Rallado'),
             ('Queso Rallado en Frasco', 'Queso Rallado'),
             ('Queso Rallado Bajo en Sodio', 'Queso Rallado'),
             ('Queso Rallado Light', 'Queso Rallado'),
             ('Queso Rallado para Gratinar', 'Queso Rallado'),
             ('Queso Rallado Orgánico', 'Queso Rallado'),
             ('Mix de Quesos Rallados', 'Queso Rallado'),
             ('Queso Crema Clásico', 'Queso Crema Y Untables'),
             ('Queso Crema Light', 'Queso Crema Y Untables'),
             ('Queso Untable de Finas Hierbas', 'Queso Crema Y Untables'),
             ('Queso Crema con Ciboulette', 'Queso Crema Y Untables'),
             ('Ricotta', 'Queso Crema Y Untables'),
             ('Mascarpone', 'Queso Crema Y Untables'),
             ('Requesón', 'Queso Crema Y Untables'),
             ('Queso Untable con Jamón', 'Queso Crema Y Untables'),
             ('Queso Untable con Tomate y Albahaca', 'Queso Crema Y Untables'),
             ('Queso Cottage', 'Queso Crema Y Untables'),
             ('Bife de Chorizo', 'Carnes'),
             ('Lomo', 'Carnes'),
             ('Falda', 'Carnes'),
             ('Costeleta', 'Carnes'),
             ('Cima de ternera', 'Carnes'),
             ('Arañita', 'Carnes'),
             ('Osobuco', 'Carnes'),
             ('Chuleton', 'Carnes'),
             ('Matambrito', 'Carnes'),
             ('Lengua', 'Carnes'),
             ('Solomillo', 'Carnes'),
             ('Carre agridulce', 'Carnes'),
             ('Lechon', 'Carnes'),
             ('Chivito', 'Carnes'),
             ('Palomita', 'Carnes'),
             ('Vacío', 'Carnes'),
             ('Asado', 'Carnes'),
             ('Entraña', 'Carnes'),
             ('Colita de Cuadril', 'Carnes'),
             ('Milanesa de Nalga', 'Carnes'),
             ('Cuadril', 'Carnes'),
             ('Peceto', 'Carnes'),
             ('Matambre', 'Carnes'),
             ('Hamburguesas Caseras', 'Carnes Elaboradas'),
             ('Chorizos Frescos', 'Carnes Elaboradas'),
             ('Morcillas', 'Carnes Elaboradas'),
             ('Salchicha Parrillera', 'Carnes Elaboradas'),
             ('Pinchos de Carne', 'Carnes Elaboradas'),
             ('Brochettes', 'Carnes Elaboradas'),
             ('Albóndigas Frescas', 'Carnes Elaboradas'),
             ('Churrasco Marinado', 'Carnes Elaboradas'),
             ('Carne para Estofado', 'Carnes Elaboradas'),
             ('Carne Picada Especial', 'Carnes Elaboradas'),
             ('Salchichas de Viena', 'Embutidos'),
             ('Mortadela', 'Embutidos'),
             ('Salame', 'Embutidos'),
             ('Chorizo Seco', 'Embutidos'),
             ('Longaniza', 'Embutidos'),
             ('Fuet', 'Embutidos'),
             ('Jamón Ahumado', 'Embutidos'),
             ('Panceta Salada', 'Embutidos'),
             ('Queso de Chancho', 'Embutidos'),
             ('Paté de Campaña', 'Embutidos'),
             ('Cordero', 'Granja'),
             ('Cabrito', 'Granja'),
             ('Conejo', 'Granja'),
             ('Pato', 'Granja'),
             ('Codorniz', 'Granja'),
             ('Gallina para Caldo', 'Granja'),
             ('Mollejas', 'Granja'),
             ('Chinchulines', 'Granja'),
             ('Riñones', 'Granja'),
             ('Sesos', 'Granja'),
             ('Pollo de Campo', 'Pollo Entero Fresco'),
             ('Pollo Parrillero', 'Pollo Entero Fresco'),
             ('Gallina', 'Pollo Entero Fresco'),
             ('Pata Muslo', 'Pollo Trozado Fresco'),
             ('Pechuga', 'Pollo Trozado Fresco'),
             ('Alitas', 'Pollo Trozado Fresco'),
             ('Muslo', 'Pollo Trozado Fresco'),
             ('Supremas', 'Pollo Trozado Fresco'),
             ('Pollo Trocitos', 'Pollo Trozado Fresco'),
             ('Pollo en Cubos', 'Pollo Trozado Fresco'),
             ('Huesos de Pollo', 'Pollo Trozado Fresco'),
             ('Menudos', 'Pollo Trozado Fresco'),
             ('Pollo Deshuesado', 'Pollo Trozado Fresco'),
             ('Pollo Congelado', 'Pollo Entero Congelado'),
             ('Pavita Congelada', 'Pollo Entero Congelado'),
             ('Pechuga Congelada', 'Pollo Trozado Congelado'),
             ('Pata Muslo Congelada', 'Pollo Trozado Congelado'),
             ('Nuggets de Pollo', 'Pollo Trozado Congelado'),
             ('Medallones de Pollo', 'Pollo Trozado Congelado'),
             ('Alitas Congeladas', 'Pollo Trozado Congelado'),
             ('Milanesas de Pollo Congeladas', 'Pollo Trozado Congelado'),
             ('Pollo para Wok Congelado', 'Pollo Trozado Congelado'),
             ('Tiritas de Pollo Congeladas', 'Pollo Trozado Congelado'),
             ('Supremas Congeladas', 'Pollo Trozado Congelado'),
             ('Pollo Entero Sin Menudos', 'Pollo Trozado Congelado'),
             ('Huevo Blanco Grande', 'Huevos Blanco'),
             ('Huevo Blanco Mediano', 'Huevos Blanco'),
             ('Huevo Blanco Pequeño', 'Huevos Blanco'),
             ('Docena de Huevo Blanco', 'Huevos Blanco'),
             ('Huevo Color Grande', 'Huevos Color'),
             ('Huevo Color Mediano', 'Huevos Color'),
             ('Huevo de Campo', 'Huevos Color'),
             ('Huevo Orgánico', 'Huevos Color'),
             ('Huevo de Codorniz', 'Huevos Color'),
             ('Huevo de Pata', 'Huevos Color'),
             ('Media Docena de Huevo Color', 'Huevos Color'),
             ('Huevo Fortificado', 'Huevos Color'),
             ('Huevo de Gallinas Libres', 'Huevos Color'),
             ('Huevo XL', 'Huevos Color'),
             ('Fideos al Huevo', 'Fideos Y Ñoquis'),
             ('Fideos de Espinaca', 'Fideos Y Ñoquis'),
             ('Fideos de Sémola', 'Fideos Y Ñoquis'),
             ('Ñoquis de Papa', 'Fideos Y Ñoquis'),
             ('Ñoquis de Calabaza', 'Fideos Y Ñoquis'),
             ('Gnocchi de Ricotta', 'Fideos Y Ñoquis'),
             ('Fideos Frescos Largos', 'Fideos Y Ñoquis'),
             ('Fideos Frescos Cortos', 'Fideos Y Ñoquis'),
             ('Fideos de Remolacha', 'Fideos Y Ñoquis'),
             ('Fideos de Harina Integral', 'Fideos Y Ñoquis'),
             ('Ravioles de Ricotta y Jamón', 'Pastas Rellenas'),
             ('Sorrentinos de Calabaza', 'Pastas Rellenas'),
             ('Agnolottis de Verdura', 'Pastas Rellenas'),
             ('Canelones de Carne', 'Pastas Rellenas'),
             ('Lasagna Fresca', 'Pastas Rellenas'),
             ('Tortellinis', 'Pastas Rellenas'),
             ('Panzottis', 'Pastas Rellenas'),
             ('Cappellettis', 'Pastas Rellenas'),
             ('Ravioles de Pollo', 'Pastas Rellenas'),
             ('Lasagna de Verdura', 'Pastas Rellenas'),
             ('Tapas de Empanadas Hojaldre', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas de Pascualina Criolla', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas de Empanadas Criollas', 'Tapas Para Pascualina Y Empanadas'),
             ('Masa para Tarta Salada', 'Tapas Para Pascualina Y Empanadas'),
             ('Masa para Tarta Dulce', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas para Copetín', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas de Masa Integral', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas de Empanadas Sin TACC', 'Tapas Para Pascualina Y Empanadas'),
             ('Discos para Freír', 'Tapas Para Pascualina Y Empanadas'),
             ('Tapas para Pasteles', 'Tapas Para Pascualina Y Empanadas'),
             ('Masa para Pizza Fresca', 'Masas Preparadas'),
             ('Masa de Hojaldre', 'Masas Preparadas'),
             ('Masa para Panqueques', 'Masas Preparadas'),
             ('Masa para Waffles', 'Masas Preparadas'),
             ('Masa para Pan Casero', 'Masas Preparadas'),
             ('Masa para Scones', 'Masas Preparadas'),
             ('Masa de Focaccia', 'Masas Preparadas'),
             ('Masa de Pan de Molde', 'Masas Preparadas'),
             ('Masa de Chapatis', 'Masas Preparadas'),
             ('Masa de Croissants', 'Masas Preparadas'),
             ('Pizza Muzzarella', 'Pizzas, Tartas Y Empanadas'),
             ('Tarta de Jamón y Queso', 'Pizzas, Tartas Y Empanadas'),
             ('Empanadas de Carne', 'Pizzas, Tartas Y Empanadas'),
             ('Pizza Napolitana', 'Pizzas, Tartas Y Empanadas'),
             ('Tarta de Calabaza', 'Pizzas, Tartas Y Empanadas'),
             ('Empanadas de Pollo', 'Pizzas, Tartas Y Empanadas'),
             ('Pizza Fugazzeta', 'Pizzas, Tartas Y Empanadas'),
             ('Tarta de Puerro', 'Pizzas, Tartas Y Empanadas'),
             ('Empanadas de Verdura', 'Pizzas, Tartas Y Empanadas'),
             ('Pizza Cuatro Quesos', 'Pizzas, Tartas Y Empanadas'),
             ('Milanesa Lista para Calentar', 'Platos Y Guarniciones'),
             ('Pollo al Spiedo', 'Platos Y Guarniciones'),
             ('Lasagna lista', 'Platos Y Guarniciones'),
             ('Canelones listos', 'Platos Y Guarniciones'),
             ('Ensalada de Pasta', 'Platos Y Guarniciones'),
             ('Puré de Papas listo', 'Platos Y Guarniciones'),
             ('Arroz con Vegetales', 'Platos Y Guarniciones'),
             ('Platos del Día', 'Platos Y Guarniciones'),
             ('Guarnición de Papas', 'Platos Y Guarniciones'),
             ('Ensalada Rusa', 'Platos Y Guarniciones'),
             ('Pizzetas para Freezar', 'Listos Para Congelar'),
             ('Empanadas crudas', 'Listos Para Congelar'),
             ('Milanesas empanadas', 'Listos Para Congelar'),
             ('Pastas Rellenas crudas', 'Listos Para Congelar'),
             ('Platos al Vacío para Freezar', 'Listos Para Congelar'),
             ('Tartas crudas', 'Listos Para Congelar'),
             ('Masa de Pizza para Freezar', 'Listos Para Congelar'),
             ('Albóndigas crudas', 'Listos Para Congelar'),
             ('Churros listos', 'Listos Para Congelar'),
             ('Bocaditos de Espinaca', 'Listos Para Congelar'),
             ('Porción de Lasagna', 'X Unidad - Porción'),
             ('Porción de Tiramisú', 'X Unidad - Porción'),
             ('Porción de Tarta', 'X Unidad - Porción'),
             ('Porción de Ensalada', 'X Unidad - Porción'),
             ('Postre Individual', 'X Unidad - Porción'),
             ('Sandwich de Miga', 'X Unidad - Porción'),
             ('Medialuna Rellena', 'X Unidad - Porción'),
             ('Porción de Pollo Relleno', 'X Unidad - Porción'),
             ('Quiche Individual', 'X Unidad - Porción'),
             ('Wrap', 'X Unidad - Porción'),
             ('Manzana', 'Frutas'),
             ('Banana', 'Frutas'),
             ('Naranja', 'Frutas'),
             ('Pera', 'Frutas'),
             ('Kiwi', 'Frutas'),
             ('Uva', 'Frutas'),
             ('Durazno', 'Frutas'),
             ('Frutilla', 'Frutas'),
             ('Mandarina', 'Frutas'),
             ('Melón', 'Frutas'),
             ('Uva Rubia', 'Frutas'),
             ('Papa', 'Verduras'),
             ('Menta', 'Verduras'),
             ('Cilantro', 'Verduras'),
             ('Zucchini', 'Verduras'),
             ('Cebolla', 'Verduras'),
             ('Acelga', 'Verduras'),
             ('Champignones', 'Verduras'),
             ('Repollitos de Bruselas', 'Verduras'),
             ('Rabanitos', 'Verduras'),
             ('Hinojo', 'Verduras'),
             ('Portobello', 'Verduras'),
             ('Radicheta', 'Verduras'),
             ('Brotes de Alfalfa', 'Verduras'),
             ('Chaucha', 'Verduras'),

             ('Pepino', 'Verduras'),
             ('Chauchas', 'Verduras'),
             ('Rucula', 'Verduras'),
             ('Zanahoria', 'Verduras'),
             ('Lechuga', 'Verduras'),
             ('Tomate', 'Verduras'),
             ('Calabaza', 'Verduras'),
             ('Brócoli', 'Verduras'),
             ('Choclo', 'Verduras'),
             ('Pimiento Rojo', 'Verduras'),
             ('Palta', 'Verduras'),
             ('Champiñones', 'Verduras'),
             ('Albahaca', 'Verduras'),
             ('Nueces', 'Frutas Secas Y Desecadas'),
             ('Almendras', 'Frutas Secas Y Desecadas'),
             ('Pasas de Uva', 'Frutas Secas Y Desecadas'),
             ('Dátiles', 'Frutas Secas Y Desecadas'),
             ('Higo Seco', 'Frutas Secas Y Desecadas'),
             ('Ciruelas Secas', 'Frutas Secas Y Desecadas'),
             ('Castañas de Cajú', 'Frutas Secas Y Desecadas'),
             ('Pistachos', 'Frutas Secas Y Desecadas'),
             ('Maní Tostado', 'Frutas Secas Y Desecadas'),
             ('Mix Frutos Secos', 'Frutas Secas Y Desecadas'),
             ('Merluza Entera', 'Entero Eviscerado'),
             ('Trucha Entera', 'Entero Eviscerado'),
             ('Besugo', 'Entero Eviscerado'),
             ('Pejerrey', 'Entero Eviscerado'),
             ('Corvina', 'Entero Eviscerado'),
             ('Pescado de Río', 'Entero Eviscerado'),
             ('Pescado Blanco', 'Entero Eviscerado'),
             ('Salmon', 'Entero Eviscerado'),
             ('Filete de Merluza', 'Filete'),
             ('Filete de Salmón Fresco', 'Filete'),
             ('Filete de Lenguado', 'Filete'),
             ('Filete de Abadejo', 'Filete'),
             ('Filete de Corvina', 'Filete'),
             ('Filete de Tilapia', 'Filete'),
             ('Filete de Pez Espada', 'Filete'),
             ('Filete de Atún Fresco', 'Filete'),
             ('Filete de Pescado Blanco', 'Filete'),
             ('Filete de Pescadilla', 'Filete'),
             ('Anchoas en Aceite', 'Semiconserva'),
             ('Boquerones en Vinagre', 'Semiconserva'),
             ('Sardinas en Aceite', 'Semiconserva'),
             ('Paté de Salmón', 'Semiconserva'),
             ('Pescado Ahumado', 'Semiconserva'),
             ('Caviar de Pescado', 'Semiconserva'),
             ('Huevas de Pescado', 'Semiconserva'),
             ('Moluscos en Escabeche', 'Semiconserva'),
             ('Camarones', 'Mariscos'),
             ('Langostinos', 'Mariscos'),
             ('Calamares Anillos', 'Mariscos'),
             ('Mejillones', 'Mariscos'),
             ('Vieira', 'Mariscos'),
             ('Pulpo', 'Mariscos'),
             ('Almejas', 'Mariscos'),
             ('Mix de Mariscos', 'Mariscos'),
             ('Pinzas de Cangrejo', 'Mariscos'),
             ('Filet de Merluza Congelado', 'Pescado De Mar'),
             ('Salmón Congelado en Porciones', 'Pescado De Mar'),
             ('Lenguado Congelado', 'Pescado De Mar'),
             ('Filet de Atún Congelado', 'Pescado De Mar'),
             ('Filet de Tilapia Congelado', 'Pescado De Mar'),
             ('Corvina Congelada', 'Pescado De Mar'),
             ('Abadejo Congelado', 'Pescado De Mar'),
             ('Merluza en Tiras Congeladas', 'Pescado De Mar'),
             ('Bacalao', 'Pescado De Mar'),
             ('Pescado de Roca', 'Pescado De Mar'),
             ('Filet de Merluza Rebozado', 'Rebozados'),
             ('Aros de Calamar Rebozados', 'Rebozados'),
             ('Bocaditos de Pescado', 'Rebozados'),
             ('Bastones de Merluza', 'Rebozados'),
             ('Milanesa de Merluza', 'Rebozados'),
             ('Fish Sticks', 'Rebozados'),
             ('Rabas Congeladas', 'Rebozados'),
             ('Nuggets de Pescado', 'Rebozados'),
             ('Salmón en Postas', 'Salmón Y Trucha'),
             ('Salmón Ahumado Congelado', 'Salmón Y Trucha'),
             ('Trucha en Filete', 'Salmón Y Trucha'),
             ('Salmón por Porción', 'Salmón Y Trucha'),
             ('Lomos de Trucha', 'Salmón Y Trucha'),
             ('Salmón Deshuesado', 'Salmón Y Trucha'),
             ('Trucha Ahumada', 'Salmón Y Trucha'),
             ('Salmón Rosado', 'Salmón Y Trucha'),
             ('Nuggets de Pollo Clásicos', 'Nuggets'),
             ('Nuggets de Pollo con Queso', 'Nuggets'),
             ('Patitas de Pollo', 'Nuggets'),
             ('Tiritas de Pollo Rebozadas', 'Nuggets'),
             ('Bocaditos de Jamón y Queso', 'Nuggets'),
             ('Bocaditos de Espinaca', 'Nuggets'),
             ('Bocaditos de Atún', 'Nuggets'),
             ('Nuggets de Pescado', 'Nuggets'),
             ('Patitas de Pavo', 'Nuggets'),
             ('Patitas de Pollo con Sésamo', 'Patitas'),
             ('Patitas de Pollo Rebozadas', 'Patitas'),
             ('Patitas de Pollo Granjeras', 'Patitas'),
             ('Patitas de Pollo a la Naranja', 'Patitas'),
             ('Bocaditos de Calabaza', 'Bocaditos Y Formitas'),
             ('Bocaditos de Queso', 'Bocaditos Y Formitas'),
             ('Formitas de Pollo Dinosaurios', 'Bocaditos Y Formitas'),
             ('Formitas de Pescado', 'Bocaditos Y Formitas'),
             ('Bocaditos de Brócoli', 'Bocaditos Y Formitas'),
             ('Bocaditos de Arroz', 'Bocaditos Y Formitas'),
             ('Bolitas de Queso', 'Bocaditos Y Formitas'),
             ('Formitas de Verdura', 'Bocaditos Y Formitas'),
             ('Bastones de Mozzarella Rebozados', 'Bastones De Mozzarella'),
             ('Dedos de Queso', 'Bastones De Mozzarella'),
             ('Bastones de Queso Cheddar', 'Bastones De Mozzarella'),
             ('Sticks de Mozzarella', 'Bastones De Mozzarella'),
             ('Bastones de Provolone', 'Bastones De Mozzarella'),
             ('Hamburguesa de Carne', 'Hamburguesas Y Medallones'),
             ('Medallón de Pollo', 'Hamburguesas Y Medallones'),
             ('Hamburguesa de Lentejas', 'Hamburguesas Y Medallones'),
             ('Hamburguesa Vegetal', 'Hamburguesas Y Medallones'),
             ('Hamburguesa con Queso', 'Hamburguesas Y Medallones'),
             ('Hamburguesa de Pescado', 'Hamburguesas Y Medallones'),
             ('Medallón de Calabaza', 'Hamburguesas Y Medallones'),
             ('Hamburguesa XL', 'Hamburguesas Y Medallones'),
             ('Hamburguesa de Cerdo', 'Hamburguesas Y Medallones'),
             ('Milanesas de Carne Congeladas', 'Milanesas Y Supremas'),
             ('Supremas de Pollo Congeladas', 'Milanesas Y Supremas'),
             ('Milanesa de Soja', 'Milanesas Y Supremas'),
             ('Milanesa de Berenjena', 'Milanesas Y Supremas'),
             ('Milanesa de Pollo Rellena', 'Milanesas Y Supremas'),
             ('Milanesa de Merluza', 'Milanesas Y Supremas'),
             ('Milanesa de Pescado', 'Milanesas Y Supremas'),
             ('Milanesa de Cerdo', 'Milanesas Y Supremas'),
             ('Milanesa de Muzzarella', 'Milanesas Y Supremas'),
             ('Papas Fritas Clásicas', 'Papas Bastón'),
             ('Papas Prefritas', 'Papas Bastón'),
             ('Papas Fritas Corte Americano', 'Papas Bastón'),
             ('Papas en Bastón para Freír', 'Papas Bastón'),
             ('Papas Fritas Largas', 'Papas Bastón'),
             ('Papas Fritas Sin TACC', 'Papas Bastón'),
             ('Papas Noisettes', 'Papas Noisettes'),
             ('Croquetas de Papa', 'Papas Noisettes'),
             ('Bolitas de Papa', 'Papas Noisettes'),
             ('Papas Smile', 'Papas Con Formas'),
             ('Papas Caritas', 'Papas Con Formas'),
             ('Papas Zig-Zag', 'Papas Con Formas'),
             ('Papas Hash Brown', 'Papas Con Formas'),
             ('Papas Duquesa', 'Papas Con Formas'),
             ('Papas en Gajo', 'Papas Con Formas'),
             ('Papas a la Española', 'Papas Estilo Casero'),
             ('Papas al Horno', 'Papas Estilo Casero'),
             ('Papas Rústicas', 'Papas Estilo Casero'),
             ('Papas Congeladas Caseras', 'Papas Estilo Casero'),
             ('Papas en Rodajas', 'Papas Estilo Casero'),
             ('Tiramisú Helado', 'Postres Helados'),
             ('Selva Negra Helada', 'Postres Helados'),
             ('Tronco Helado', 'Postres Helados'),
             ('Postre Bombón Suizo', 'Postres Helados'),
             ('Almendrado', 'Postres Helados'),
             ('Postre Balcarce', 'Postres Helados'),
             ('Postre de Chocolate Helado', 'Postres Helados'),
             ('Postre de Crema y Durazno', 'Postres Helados'),
             ('Pote de Helado Vainilla', 'Helados En Pote'),
             ('Pote de Helado Chocolate', 'Helados En Pote'),
             ('Pote de Helado Frutilla', 'Helados En Pote'),
             ('Helado en Pote Artesanal', 'Helados En Pote'),
             ('Helado de Dulce de Leche', 'Helados En Pote'),
             ('Helado Crema Americana', 'Helados En Pote'),
             ('Helado de Limón', 'Helados En Pote'),
             ('Helado Cremosísimo', 'Helados En Pote'),
             ('Pote de Helado Light', 'Helados En Pote'),
             ('Palito de Agua', 'Helados En Palito'),
             ('Palito de Crema', 'Helados En Palito'),
             ('Bombón Helado', 'Helados En Palito'),
             ('Palito de Frutas', 'Helados En Palito'),
             ('Palito de Jugo', 'Helados En Palito'),
             ('Palito de Vainilla', 'Helados En Palito'),
             ('Palito de Chocolate', 'Helados En Palito'),
             ('Palito de Crema y Chocolate', 'Helados En Palito'),
             ('Palito Doble Sabor', 'Helados En Palito'),
             ('Yogur Helado Frutilla', 'Yogur Helado'),
             ('Yogur Helado Natural', 'Yogur Helado'),
             ('Yogur Helado Durazno', 'Yogur Helado'),
             ('Yogur Helado con Cereales', 'Yogur Helado'),
             ('Yogur Helado Cremosísimo', 'Yogur Helado'),
             ('Pizzas Y Pizzetas', 'Comidas Congeladas'),
             ('Pastas Congeladas', 'Comidas Congeladas'),
             ('Panificados', 'Comidas Congeladas'),
             ('Empanadas Y Tartas', 'Comidas Congeladas'),
             ('Platos Preparados', 'Comidas Congeladas'),
             ('Acelga', 'Vegetales Congelados'),
             ('Cebollas', 'Vegetales Congelados'),
             ('Vegetales Para Saltear', 'Vegetales Congelados')) AS T(product_name, sub_category_level2_name)
         JOIN public.sub_category_level2 SCL2 ON SCL2.name = T.sub_category_level2_name
WHERE NOT EXISTS (SELECT 1
                  FROM public.product P
                  WHERE P.name = T.product_name);
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									
																									