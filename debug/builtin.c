void print(char* str_1){
	printf("%s",str_1);
}
void println(char* str_2){
	printf("%s\n",str_2);
}
void printInt(int n_1){
	printf("%d",n_1);
}
void printlnInt(int n_2){
	printf("%d\n",n_2);
}
char* getString(){//string大小最多不能超过100
	char* str = (char*)malloc(100);
    scanf("%s", str);
    return str;
}
int getInt(){
	int a;
	scanf("%d",&a);
	return a;
}
char* toString(int i_1){
	char* str = (char*)malloc(13);
    sprintf(str, "%d", i_1);
    return str;
}
char _bottom_str_eq(char* str1_1,char* str2_1){
	return strcmp(str1_1,str2_1)==0;
}
char _bottom_str_ne(char* str1_2,char* str2_2){
	return strcmp(str1_2,str2_2)!=0;
}
char _bottom_str_slt(char* str1_3,char* str2_3){
	return strcmp(str1_3,str2_3)<0;
}
char _bottom_str_sgt(char* str1_4,char* str2_4){
	return strcmp(str1_4,str2_4)>0;
}
char _bottom_str_sle(char* str1_5,char* str2_5){
	return strcmp(str1_5,str2_5)<=0;
}
char _bottom_str_sge(char* str1_6,char* str2_6){
	return strcmp(str1_6,str2_6)>=0;
}
int _str_length(char* str_3){
	int x=0;
	while(str_3[x]!='\0')x++;
	return x;
}
char* _bottom_str_cat(char* str1_7,char* str2_7){
	int len1=_string_length_1(str1_7),len2=_string_length_1(str2_7);
	char* str=malloc(len1+len2+1);
	int i;
	for(i=0;i<len1;i++)str[i]=str1_7[i];
	for(i=0;i<len2;i++)str[len1+i]=str2_7[i];
	str[len1+len2]='\0';
	return str;
}
char* _bottom_malloc(int n_3){
	return (char*)malloc(n_3);
}
char* _str_substring(char* str_4,int l_1,int r_1){
	char* str=malloc(r_1 - l_1 + 1);
	int i;
	for(i=l_1;i<r_1;i++)str[i-l_1]=str_4[i];
	return str;
}
int _str_parseInt(char* str_5){
	int ans=0,i=0;
	while(str_5[i]!='\0'){
		ans=ans*10+str_5[i]-'0';
		i++;
	}
	return ans;
}
int _str_ord(char* str_6,int ord_1){
	return (int)str_6[ord_1];
}