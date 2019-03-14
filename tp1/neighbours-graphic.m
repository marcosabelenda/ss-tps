% load data
file_data=importdata('ss-dynamic.txt',',');
fileID = fopen('ss-output.txt');
id = 0; % si no encuentra en la particula en la pos xp, yp entonces carga la particula 0
xp = round(0.15,2);
yp = round(14.92,2);

% asigno x, y de las particulas
x = file_data(:, 1);
y = file_data(:, 2);
n = size(x);

% cargo los vecinos
separator = ',';
allParams = textscan(fileID,'%s');
fclose(fileID);
values = allParams{1};

allParams_size = size(values);
vector = cell(allParams_size(1),1);
for i = 1:allParams_size(1)

   vec = values{i};
   splitted = strsplit(vec, separator);
   splitted_size = size(splitted);
   aux = zeros(1, splitted_size(2));
   for j=1:splitted_size(2)
       aux(j) = str2num(splitted{j});
   end
   vector{i} = aux;

end

x1 = vector;
n1 = size(x1);
n1 = n1(1);

% selecciono la particula segun el xp, yp
for i = 1:n 
    if(round(x(i),2) == xp || round(y(i),2) == yp)
        id = i - 1;
        break;
    end
end
% grafico
h = scatter(x,y,'filled');
set(gcf,'position',[0,0,1000,1000])
c = h.CData;
% cambio el color de la particula seleccionada y de sus vecinos
c = repmat(c, [n 1]);

for i=1:n1
    if(id == x1{i}(1))
        aux = id+1;
        c(aux,:) = [1 0 0];
        x2 = x1{i}(:);
        x2 = x2'
        x2 = (x2(~isnan(x2)));
        n2 = size(x2);
        n2 = n2(2);
        
        for j=2:n2
            aux = x2(j)+1;
            c(aux,:) = [0 1 0];
        end
        
    end
    
end
h.CData = c;
